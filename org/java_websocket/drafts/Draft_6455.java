package org.java_websocket.drafts;

import java.nio.*;
import org.java_websocket.protocols.*;
import org.slf4j.*;
import org.java_websocket.extensions.*;
import org.java_websocket.handshake.*;
import java.math.*;
import org.java_websocket.exceptions.*;
import org.java_websocket.util.*;
import java.text.*;
import java.util.*;
import java.security.*;
import org.java_websocket.*;
import org.java_websocket.framing.*;
import org.java_websocket.enums.*;

public class Draft_6455 extends Draft
{
    private static final String SEC_WEB_SOCKET_KEY = "Sec-WebSocket-Key";
    private static final String SEC_WEB_SOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
    private static final String SEC_WEB_SOCKET_EXTENSIONS = "Sec-WebSocket-Extensions";
    private static final String SEC_WEB_SOCKET_ACCEPT = "Sec-WebSocket-Accept";
    private static final String UPGRADE = "Upgrade";
    private static final String CONNECTION = "Connection";
    private final Logger log;
    private IExtension extension;
    private List<IExtension> knownExtensions;
    private IProtocol protocol;
    private List<IProtocol> knownProtocols;
    private Framedata currentContinuousFrame;
    private final List<ByteBuffer> byteBufferList;
    private ByteBuffer incompleteframe;
    private final SecureRandom reuseableRandom;
    private int maxFrameSize;
    
    public Draft_6455() {
        this(Collections.emptyList());
    }
    
    public Draft_6455(final IExtension inputExtension) {
        this(Collections.singletonList(inputExtension));
    }
    
    public Draft_6455(final List<IExtension> inputExtensions) {
        this(inputExtensions, (List<IProtocol>)Collections.singletonList(new Protocol("")));
    }
    
    public Draft_6455(final List<IExtension> inputExtensions, final List<IProtocol> inputProtocols) {
        this(inputExtensions, inputProtocols, Integer.MAX_VALUE);
    }
    
    public Draft_6455(final List<IExtension> inputExtensions, final int inputMaxFrameSize) {
        this(inputExtensions, (List<IProtocol>)Collections.singletonList(new Protocol("")), inputMaxFrameSize);
    }
    
    public Draft_6455(final List<IExtension> inputExtensions, final List<IProtocol> inputProtocols, final int inputMaxFrameSize) {
        this.log = LoggerFactory.getLogger(Draft_6455.class);
        this.extension = new DefaultExtension();
        this.reuseableRandom = new SecureRandom();
        if (inputExtensions == null || inputProtocols == null || inputMaxFrameSize < 1) {
            throw new IllegalArgumentException();
        }
        this.knownExtensions = new ArrayList<IExtension>(inputExtensions.size());
        this.knownProtocols = new ArrayList<IProtocol>(inputProtocols.size());
        boolean hasDefault = false;
        this.byteBufferList = new ArrayList<ByteBuffer>();
        for (final IExtension inputExtension : inputExtensions) {
            if (inputExtension.getClass().equals(DefaultExtension.class)) {
                hasDefault = true;
            }
        }
        this.knownExtensions.addAll(inputExtensions);
        if (!hasDefault) {
            this.knownExtensions.add(this.knownExtensions.size(), this.extension);
        }
        this.knownProtocols.addAll(inputProtocols);
        this.maxFrameSize = inputMaxFrameSize;
    }
    
    @Override
    public HandshakeState acceptHandshakeAsServer(final ClientHandshake handshakedata) throws InvalidHandshakeException {
        final int v = this.readVersion(handshakedata);
        if (v != 13) {
            this.log.trace("acceptHandshakeAsServer - Wrong websocket version.");
            return HandshakeState.NOT_MATCHED;
        }
        HandshakeState extensionState = HandshakeState.NOT_MATCHED;
        final String requestedExtension = handshakedata.getFieldValue("Sec-WebSocket-Extensions");
        for (final IExtension knownExtension : this.knownExtensions) {
            if (knownExtension.acceptProvidedExtensionAsServer(requestedExtension)) {
                this.extension = knownExtension;
                extensionState = HandshakeState.MATCHED;
                this.log.trace("acceptHandshakeAsServer - Matching extension found: {}", this.extension);
                break;
            }
        }
        final HandshakeState protocolState = this.containsRequestedProtocol(handshakedata.getFieldValue("Sec-WebSocket-Protocol"));
        if (protocolState == HandshakeState.MATCHED && extensionState == HandshakeState.MATCHED) {
            return HandshakeState.MATCHED;
        }
        this.log.trace("acceptHandshakeAsServer - No matching extension or protocol found.");
        return HandshakeState.NOT_MATCHED;
    }
    
    private HandshakeState containsRequestedProtocol(final String requestedProtocol) {
        for (final IProtocol knownProtocol : this.knownProtocols) {
            if (knownProtocol.acceptProvidedProtocol(requestedProtocol)) {
                this.protocol = knownProtocol;
                this.log.trace("acceptHandshake - Matching protocol found: {}", this.protocol);
                return HandshakeState.MATCHED;
            }
        }
        return HandshakeState.NOT_MATCHED;
    }
    
    @Override
    public HandshakeState acceptHandshakeAsClient(final ClientHandshake request, final ServerHandshake response) throws InvalidHandshakeException {
        if (!this.basicAccept(response)) {
            this.log.trace("acceptHandshakeAsClient - Missing/wrong upgrade or connection in handshake.");
            return HandshakeState.NOT_MATCHED;
        }
        if (!request.hasFieldValue("Sec-WebSocket-Key") || !response.hasFieldValue("Sec-WebSocket-Accept")) {
            this.log.trace("acceptHandshakeAsClient - Missing Sec-WebSocket-Key or Sec-WebSocket-Accept");
            return HandshakeState.NOT_MATCHED;
        }
        final String seckeyAnswer = response.getFieldValue("Sec-WebSocket-Accept");
        String seckeyChallenge = request.getFieldValue("Sec-WebSocket-Key");
        seckeyChallenge = this.generateFinalKey(seckeyChallenge);
        if (!seckeyChallenge.equals(seckeyAnswer)) {
            this.log.trace("acceptHandshakeAsClient - Wrong key for Sec-WebSocket-Key.");
            return HandshakeState.NOT_MATCHED;
        }
        HandshakeState extensionState = HandshakeState.NOT_MATCHED;
        final String requestedExtension = response.getFieldValue("Sec-WebSocket-Extensions");
        for (final IExtension knownExtension : this.knownExtensions) {
            if (knownExtension.acceptProvidedExtensionAsClient(requestedExtension)) {
                this.extension = knownExtension;
                extensionState = HandshakeState.MATCHED;
                this.log.trace("acceptHandshakeAsClient - Matching extension found: {}", this.extension);
                break;
            }
        }
        final HandshakeState protocolState = this.containsRequestedProtocol(response.getFieldValue("Sec-WebSocket-Protocol"));
        if (protocolState == HandshakeState.MATCHED && extensionState == HandshakeState.MATCHED) {
            return HandshakeState.MATCHED;
        }
        this.log.trace("acceptHandshakeAsClient - No matching extension or protocol found.");
        return HandshakeState.NOT_MATCHED;
    }
    
    public IExtension getExtension() {
        return this.extension;
    }
    
    public List<IExtension> getKnownExtensions() {
        return this.knownExtensions;
    }
    
    public IProtocol getProtocol() {
        return this.protocol;
    }
    
    public int getMaxFrameSize() {
        return this.maxFrameSize;
    }
    
    public List<IProtocol> getKnownProtocols() {
        return this.knownProtocols;
    }
    
    @Override
    public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(final ClientHandshakeBuilder request) {
        request.put("Upgrade", "websocket");
        request.put("Connection", "Upgrade");
        final byte[] random = new byte[16];
        this.reuseableRandom.nextBytes(random);
        request.put("Sec-WebSocket-Key", Base64.encodeBytes(random));
        request.put("Sec-WebSocket-Version", "13");
        final StringBuilder requestedExtensions = new StringBuilder();
        for (final IExtension knownExtension : this.knownExtensions) {
            if (knownExtension.getProvidedExtensionAsClient() != null && knownExtension.getProvidedExtensionAsClient().length() != 0) {
                if (requestedExtensions.length() > 0) {
                    requestedExtensions.append(", ");
                }
                requestedExtensions.append(knownExtension.getProvidedExtensionAsClient());
            }
        }
        if (requestedExtensions.length() != 0) {
            request.put("Sec-WebSocket-Extensions", requestedExtensions.toString());
        }
        final StringBuilder requestedProtocols = new StringBuilder();
        for (final IProtocol knownProtocol : this.knownProtocols) {
            if (knownProtocol.getProvidedProtocol().length() != 0) {
                if (requestedProtocols.length() > 0) {
                    requestedProtocols.append(", ");
                }
                requestedProtocols.append(knownProtocol.getProvidedProtocol());
            }
        }
        if (requestedProtocols.length() != 0) {
            request.put("Sec-WebSocket-Protocol", requestedProtocols.toString());
        }
        return request;
    }
    
    @Override
    public HandshakeBuilder postProcessHandshakeResponseAsServer(final ClientHandshake request, final ServerHandshakeBuilder response) throws InvalidHandshakeException {
        response.put("Upgrade", "websocket");
        response.put("Connection", request.getFieldValue("Connection"));
        final String seckey = request.getFieldValue("Sec-WebSocket-Key");
        if (seckey == null || "".equals(seckey)) {
            throw new InvalidHandshakeException("missing Sec-WebSocket-Key");
        }
        response.put("Sec-WebSocket-Accept", this.generateFinalKey(seckey));
        if (this.getExtension().getProvidedExtensionAsServer().length() != 0) {
            response.put("Sec-WebSocket-Extensions", this.getExtension().getProvidedExtensionAsServer());
        }
        if (this.getProtocol() != null && this.getProtocol().getProvidedProtocol().length() != 0) {
            response.put("Sec-WebSocket-Protocol", this.getProtocol().getProvidedProtocol());
        }
        response.setHttpStatusMessage("Web Socket Protocol Handshake");
        response.put("Server", "TooTallNate Java-WebSocket");
        response.put("Date", this.getServerTime());
        return response;
    }
    
    @Override
    public Draft copyInstance() {
        final ArrayList<IExtension> newExtensions = new ArrayList<IExtension>();
        for (final IExtension knownExtension : this.getKnownExtensions()) {
            newExtensions.add(knownExtension.copyInstance());
        }
        final ArrayList<IProtocol> newProtocols = new ArrayList<IProtocol>();
        for (final IProtocol knownProtocol : this.getKnownProtocols()) {
            newProtocols.add(knownProtocol.copyInstance());
        }
        return new Draft_6455(newExtensions, newProtocols, this.maxFrameSize);
    }
    
    @Override
    public ByteBuffer createBinaryFrame(final Framedata framedata) {
        this.getExtension().encodeFrame(framedata);
        if (this.log.isTraceEnabled()) {
            this.log.trace("afterEnconding({}): {}", (Object)framedata.getPayloadData().remaining(), (framedata.getPayloadData().remaining() > 1000) ? "too big to display" : new String(framedata.getPayloadData().array()));
        }
        return this.createByteBufferFromFramedata(framedata);
    }
    
    private ByteBuffer createByteBufferFromFramedata(final Framedata framedata) {
        final ByteBuffer mes = framedata.getPayloadData();
        final boolean mask = this.role == Role.CLIENT;
        final int sizebytes = this.getSizeBytes(mes);
        final ByteBuffer buf = ByteBuffer.allocate(1 + ((sizebytes > 1) ? (sizebytes + 1) : sizebytes) + (mask ? 4 : 0) + mes.remaining());
        final byte optcode = this.fromOpcode(framedata.getOpcode());
        byte one = (byte)(framedata.isFin() ? -128 : 0);
        one |= optcode;
        if (framedata.isRSV1()) {
            one |= this.getRSVByte(1);
        }
        if (framedata.isRSV2()) {
            one |= this.getRSVByte(2);
        }
        if (framedata.isRSV3()) {
            one |= this.getRSVByte(3);
        }
        buf.put(one);
        final byte[] payloadlengthbytes = this.toByteArray(mes.remaining(), sizebytes);
        assert payloadlengthbytes.length == sizebytes;
        if (sizebytes == 1) {
            buf.put((byte)(payloadlengthbytes[0] | this.getMaskByte(mask)));
        }
        else if (sizebytes == 2) {
            buf.put((byte)(0x7E | this.getMaskByte(mask)));
            buf.put(payloadlengthbytes);
        }
        else {
            if (sizebytes != 8) {
                throw new IllegalStateException("Size representation not supported/specified");
            }
            buf.put((byte)(0x7F | this.getMaskByte(mask)));
            buf.put(payloadlengthbytes);
        }
        if (mask) {
            final ByteBuffer maskkey = ByteBuffer.allocate(4);
            maskkey.putInt(this.reuseableRandom.nextInt());
            buf.put(maskkey.array());
            int i = 0;
            while (mes.hasRemaining()) {
                buf.put((byte)(mes.get() ^ maskkey.get(i % 4)));
                ++i;
            }
        }
        else {
            buf.put(mes);
            mes.flip();
        }
        assert buf.remaining() == 0 : buf.remaining();
        buf.flip();
        return buf;
    }
    
    private Framedata translateSingleFrame(final ByteBuffer buffer) throws IncompleteException, InvalidDataException {
        if (buffer == null) {
            throw new IllegalArgumentException();
        }
        final int maxpacketsize = buffer.remaining();
        int realpacketsize = 2;
        this.translateSingleFrameCheckPacketSize(maxpacketsize, realpacketsize);
        final byte b1 = buffer.get();
        final boolean fin = b1 >> 8 != 0;
        final boolean rsv1 = (b1 & 0x40) != 0x0;
        final boolean rsv2 = (b1 & 0x20) != 0x0;
        final boolean rsv3 = (b1 & 0x10) != 0x0;
        final byte b2 = buffer.get();
        final boolean mask = (b2 & 0xFFFFFF80) != 0x0;
        int payloadlength = (byte)(b2 & 0x7F);
        final Opcode optcode = this.toOpcode((byte)(b1 & 0xF));
        if (payloadlength < 0 || payloadlength > 125) {
            final TranslatedPayloadMetaData payloadData = this.translateSingleFramePayloadLength(buffer, optcode, payloadlength, maxpacketsize, realpacketsize);
            payloadlength = payloadData.getPayloadLength();
            realpacketsize = payloadData.getRealPackageSize();
        }
        this.translateSingleFrameCheckLengthLimit(payloadlength);
        realpacketsize += (mask ? 4 : 0);
        realpacketsize += payloadlength;
        this.translateSingleFrameCheckPacketSize(maxpacketsize, realpacketsize);
        final ByteBuffer payload = ByteBuffer.allocate(this.checkAlloc(payloadlength));
        if (mask) {
            final byte[] maskskey = new byte[4];
            buffer.get(maskskey);
            for (int i = 0; i < payloadlength; ++i) {
                payload.put((byte)(buffer.get() ^ maskskey[i % 4]));
            }
        }
        else {
            payload.put(buffer.array(), buffer.position(), payload.limit());
            buffer.position(buffer.position() + payload.limit());
        }
        final FramedataImpl1 frame = FramedataImpl1.get(optcode);
        frame.setFin(fin);
        frame.setRSV1(rsv1);
        frame.setRSV2(rsv2);
        frame.setRSV3(rsv3);
        payload.flip();
        frame.setPayload(payload);
        this.getExtension().isFrameValid(frame);
        this.getExtension().decodeFrame(frame);
        if (this.log.isTraceEnabled()) {
            this.log.trace("afterDecoding({}): {}", (Object)frame.getPayloadData().remaining(), (frame.getPayloadData().remaining() > 1000) ? "too big to display" : new String(frame.getPayloadData().array()));
        }
        frame.isValid();
        return frame;
    }
    
    private TranslatedPayloadMetaData translateSingleFramePayloadLength(final ByteBuffer buffer, final Opcode optcode, final int oldPayloadlength, final int maxpacketsize, final int oldRealpacketsize) throws InvalidFrameException, IncompleteException, LimitExceededException {
        int payloadlength = oldPayloadlength;
        int realpacketsize = oldRealpacketsize;
        if (optcode == Opcode.PING || optcode == Opcode.PONG || optcode == Opcode.CLOSING) {
            this.log.trace("Invalid frame: more than 125 octets");
            throw new InvalidFrameException("more than 125 octets");
        }
        if (payloadlength == 126) {
            realpacketsize += 2;
            this.translateSingleFrameCheckPacketSize(maxpacketsize, realpacketsize);
            final byte[] sizebytes = { 0, buffer.get(), buffer.get() };
            payloadlength = new BigInteger(sizebytes).intValue();
        }
        else {
            realpacketsize += 8;
            this.translateSingleFrameCheckPacketSize(maxpacketsize, realpacketsize);
            final byte[] bytes = new byte[8];
            for (int i = 0; i < 8; ++i) {
                bytes[i] = buffer.get();
            }
            final long length = new BigInteger(bytes).longValue();
            this.translateSingleFrameCheckLengthLimit(length);
            payloadlength = (int)length;
        }
        return new TranslatedPayloadMetaData(payloadlength, realpacketsize);
    }
    
    private void translateSingleFrameCheckLengthLimit(final long length) throws LimitExceededException {
        if (length > 2147483647L) {
            this.log.trace("Limit exedeed: Payloadsize is to big...");
            throw new LimitExceededException("Payloadsize is to big...");
        }
        if (length > this.maxFrameSize) {
            this.log.trace("Payload limit reached. Allowed: {} Current: {}", (Object)this.maxFrameSize, length);
            throw new LimitExceededException("Payload limit reached.", this.maxFrameSize);
        }
        if (length < 0L) {
            this.log.trace("Limit underflow: Payloadsize is to little...");
            throw new LimitExceededException("Payloadsize is to little...");
        }
    }
    
    private void translateSingleFrameCheckPacketSize(final int maxpacketsize, final int realpacketsize) throws IncompleteException {
        if (maxpacketsize < realpacketsize) {
            this.log.trace("Incomplete frame: maxpacketsize < realpacketsize");
            throw new IncompleteException(realpacketsize);
        }
    }
    
    private byte getRSVByte(final int rsv) {
        switch (rsv) {
            case 1: {
                return 64;
            }
            case 2: {
                return 32;
            }
            case 3: {
                return 16;
            }
            default: {
                return 0;
            }
        }
    }
    
    private byte getMaskByte(final boolean mask) {
        return (byte)(mask ? -128 : 0);
    }
    
    private int getSizeBytes(final ByteBuffer mes) {
        if (mes.remaining() <= 125) {
            return 1;
        }
        if (mes.remaining() <= 65535) {
            return 2;
        }
        return 8;
    }
    
    @Override
    public List<Framedata> translateFrame(final ByteBuffer buffer) throws InvalidDataException {
        List<Framedata> frames;
        while (true) {
            frames = new LinkedList<Framedata>();
            if (this.incompleteframe != null) {
                try {
                    buffer.mark();
                    final int availableNextByteCount = buffer.remaining();
                    final int expectedNextByteCount = this.incompleteframe.remaining();
                    if (expectedNextByteCount > availableNextByteCount) {
                        this.incompleteframe.put(buffer.array(), buffer.position(), availableNextByteCount);
                        buffer.position(buffer.position() + availableNextByteCount);
                        return Collections.emptyList();
                    }
                    this.incompleteframe.put(buffer.array(), buffer.position(), expectedNextByteCount);
                    buffer.position(buffer.position() + expectedNextByteCount);
                    final Framedata cur = this.translateSingleFrame((ByteBuffer)this.incompleteframe.duplicate().position(0));
                    frames.add(cur);
                    this.incompleteframe = null;
                }
                catch (IncompleteException e) {
                    final ByteBuffer extendedframe = ByteBuffer.allocate(this.checkAlloc(e.getPreferredSize()));
                    assert extendedframe.limit() > this.incompleteframe.limit();
                    this.incompleteframe.rewind();
                    extendedframe.put(this.incompleteframe);
                    this.incompleteframe = extendedframe;
                    continue;
                }
                break;
            }
            break;
        }
        while (buffer.hasRemaining()) {
            buffer.mark();
            try {
                final Framedata cur = this.translateSingleFrame(buffer);
                frames.add(cur);
                continue;
            }
            catch (IncompleteException e) {
                buffer.reset();
                final int pref = e.getPreferredSize();
                (this.incompleteframe = ByteBuffer.allocate(this.checkAlloc(pref))).put(buffer);
            }
            break;
        }
        return frames;
    }
    
    @Override
    public List<Framedata> createFrames(final ByteBuffer binary, final boolean mask) {
        final BinaryFrame curframe = new BinaryFrame();
        curframe.setPayload(binary);
        curframe.setTransferemasked(mask);
        try {
            curframe.isValid();
        }
        catch (InvalidDataException e) {
            throw new NotSendableException(e);
        }
        return (List<Framedata>)Collections.singletonList(curframe);
    }
    
    @Override
    public List<Framedata> createFrames(final String text, final boolean mask) {
        final TextFrame curframe = new TextFrame();
        curframe.setPayload(ByteBuffer.wrap(Charsetfunctions.utf8Bytes(text)));
        curframe.setTransferemasked(mask);
        try {
            curframe.isValid();
        }
        catch (InvalidDataException e) {
            throw new NotSendableException(e);
        }
        return (List<Framedata>)Collections.singletonList(curframe);
    }
    
    @Override
    public void reset() {
        this.incompleteframe = null;
        if (this.extension != null) {
            this.extension.reset();
        }
        this.extension = new DefaultExtension();
        this.protocol = null;
    }
    
    private String getServerTime() {
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }
    
    private String generateFinalKey(final String in) {
        final String seckey = in.trim();
        final String acc = seckey + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        MessageDigest sh1;
        try {
            sh1 = MessageDigest.getInstance("SHA1");
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
        return Base64.encodeBytes(sh1.digest(acc.getBytes()));
    }
    
    private byte[] toByteArray(final long val, final int bytecount) {
        final byte[] buffer = new byte[bytecount];
        final int highest = 8 * bytecount - 8;
        for (int i = 0; i < bytecount; ++i) {
            buffer[i] = (byte)(val >>> highest - 8 * i);
        }
        return buffer;
    }
    
    private byte fromOpcode(final Opcode opcode) {
        if (opcode == Opcode.CONTINUOUS) {
            return 0;
        }
        if (opcode == Opcode.TEXT) {
            return 1;
        }
        if (opcode == Opcode.BINARY) {
            return 2;
        }
        if (opcode == Opcode.CLOSING) {
            return 8;
        }
        if (opcode == Opcode.PING) {
            return 9;
        }
        if (opcode == Opcode.PONG) {
            return 10;
        }
        throw new IllegalArgumentException("Don't know how to handle " + opcode.toString());
    }
    
    private Opcode toOpcode(final byte opcode) throws InvalidFrameException {
        switch (opcode) {
            case 0: {
                return Opcode.CONTINUOUS;
            }
            case 1: {
                return Opcode.TEXT;
            }
            case 2: {
                return Opcode.BINARY;
            }
            case 8: {
                return Opcode.CLOSING;
            }
            case 9: {
                return Opcode.PING;
            }
            case 10: {
                return Opcode.PONG;
            }
            default: {
                throw new InvalidFrameException("Unknown opcode " + opcode);
            }
        }
    }
    
    @Override
    public void processFrame(final WebSocketImpl webSocketImpl, final Framedata frame) throws InvalidDataException {
        final Opcode curop = frame.getOpcode();
        if (curop == Opcode.CLOSING) {
            this.processFrameClosing(webSocketImpl, frame);
        }
        else if (curop == Opcode.PING) {
            webSocketImpl.getWebSocketListener().onWebsocketPing(webSocketImpl, frame);
        }
        else if (curop == Opcode.PONG) {
            webSocketImpl.updateLastPong();
            webSocketImpl.getWebSocketListener().onWebsocketPong(webSocketImpl, frame);
        }
        else if (!frame.isFin() || curop == Opcode.CONTINUOUS) {
            this.processFrameContinuousAndNonFin(webSocketImpl, frame, curop);
        }
        else {
            if (this.currentContinuousFrame != null) {
                this.log.error("Protocol error: Continuous frame sequence not completed.");
                throw new InvalidDataException(1002, "Continuous frame sequence not completed.");
            }
            if (curop == Opcode.TEXT) {
                this.processFrameText(webSocketImpl, frame);
            }
            else {
                if (curop != Opcode.BINARY) {
                    this.log.error("non control or continious frame expected");
                    throw new InvalidDataException(1002, "non control or continious frame expected");
                }
                this.processFrameBinary(webSocketImpl, frame);
            }
        }
    }
    
    private void processFrameContinuousAndNonFin(final WebSocketImpl webSocketImpl, final Framedata frame, final Opcode curop) throws InvalidDataException {
        if (curop != Opcode.CONTINUOUS) {
            this.processFrameIsNotFin(frame);
        }
        else if (frame.isFin()) {
            this.processFrameIsFin(webSocketImpl, frame);
        }
        else if (this.currentContinuousFrame == null) {
            this.log.error("Protocol error: Continuous frame sequence was not started.");
            throw new InvalidDataException(1002, "Continuous frame sequence was not started.");
        }
        if (curop == Opcode.TEXT && !Charsetfunctions.isValidUTF8(frame.getPayloadData())) {
            this.log.error("Protocol error: Payload is not UTF8");
            throw new InvalidDataException(1007);
        }
        if (curop == Opcode.CONTINUOUS && this.currentContinuousFrame != null) {
            this.addToBufferList(frame.getPayloadData());
        }
    }
    
    private void processFrameBinary(final WebSocketImpl webSocketImpl, final Framedata frame) {
        try {
            webSocketImpl.getWebSocketListener().onWebsocketMessage(webSocketImpl, frame.getPayloadData());
        }
        catch (RuntimeException e) {
            this.logRuntimeException(webSocketImpl, e);
        }
    }
    
    private void logRuntimeException(final WebSocketImpl webSocketImpl, final RuntimeException e) {
        this.log.error("Runtime exception during onWebsocketMessage", e);
        webSocketImpl.getWebSocketListener().onWebsocketError(webSocketImpl, e);
    }
    
    private void processFrameText(final WebSocketImpl webSocketImpl, final Framedata frame) throws InvalidDataException {
        try {
            webSocketImpl.getWebSocketListener().onWebsocketMessage(webSocketImpl, Charsetfunctions.stringUtf8(frame.getPayloadData()));
        }
        catch (RuntimeException e) {
            this.logRuntimeException(webSocketImpl, e);
        }
    }
    
    private void processFrameIsFin(final WebSocketImpl webSocketImpl, final Framedata frame) throws InvalidDataException {
        if (this.currentContinuousFrame == null) {
            this.log.trace("Protocol error: Previous continuous frame sequence not completed.");
            throw new InvalidDataException(1002, "Continuous frame sequence was not started.");
        }
        this.addToBufferList(frame.getPayloadData());
        this.checkBufferLimit();
        if (this.currentContinuousFrame.getOpcode() == Opcode.TEXT) {
            ((FramedataImpl1)this.currentContinuousFrame).setPayload(this.getPayloadFromByteBufferList());
            ((FramedataImpl1)this.currentContinuousFrame).isValid();
            try {
                webSocketImpl.getWebSocketListener().onWebsocketMessage(webSocketImpl, Charsetfunctions.stringUtf8(this.currentContinuousFrame.getPayloadData()));
            }
            catch (RuntimeException e) {
                this.logRuntimeException(webSocketImpl, e);
            }
        }
        else if (this.currentContinuousFrame.getOpcode() == Opcode.BINARY) {
            ((FramedataImpl1)this.currentContinuousFrame).setPayload(this.getPayloadFromByteBufferList());
            ((FramedataImpl1)this.currentContinuousFrame).isValid();
            try {
                webSocketImpl.getWebSocketListener().onWebsocketMessage(webSocketImpl, this.currentContinuousFrame.getPayloadData());
            }
            catch (RuntimeException e) {
                this.logRuntimeException(webSocketImpl, e);
            }
        }
        this.currentContinuousFrame = null;
        this.clearBufferList();
    }
    
    private void processFrameIsNotFin(final Framedata frame) throws InvalidDataException {
        if (this.currentContinuousFrame != null) {
            this.log.trace("Protocol error: Previous continuous frame sequence not completed.");
            throw new InvalidDataException(1002, "Previous continuous frame sequence not completed.");
        }
        this.currentContinuousFrame = frame;
        this.addToBufferList(frame.getPayloadData());
        this.checkBufferLimit();
    }
    
    private void processFrameClosing(final WebSocketImpl webSocketImpl, final Framedata frame) {
        int code = 1005;
        String reason = "";
        if (frame instanceof CloseFrame) {
            final CloseFrame cf = (CloseFrame)frame;
            code = cf.getCloseCode();
            reason = cf.getMessage();
        }
        if (webSocketImpl.getReadyState() == ReadyState.CLOSING) {
            webSocketImpl.closeConnection(code, reason, true);
        }
        else if (this.getCloseHandshakeType() == CloseHandshakeType.TWOWAY) {
            webSocketImpl.close(code, reason, true);
        }
        else {
            webSocketImpl.flushAndClose(code, reason, false);
        }
    }
    
    private void clearBufferList() {
        synchronized (this.byteBufferList) {
            this.byteBufferList.clear();
        }
    }
    
    private void addToBufferList(final ByteBuffer payloadData) {
        synchronized (this.byteBufferList) {
            this.byteBufferList.add(payloadData);
        }
    }
    
    private void checkBufferLimit() throws LimitExceededException {
        final long totalSize = this.getByteBufferListSize();
        if (totalSize > this.maxFrameSize) {
            this.clearBufferList();
            this.log.trace("Payload limit reached. Allowed: {} Current: {}", (Object)this.maxFrameSize, totalSize);
            throw new LimitExceededException(this.maxFrameSize);
        }
    }
    
    @Override
    public CloseHandshakeType getCloseHandshakeType() {
        return CloseHandshakeType.TWOWAY;
    }
    
    @Override
    public String toString() {
        String result = super.toString();
        if (this.getExtension() != null) {
            result = result + " extension: " + this.getExtension().toString();
        }
        if (this.getProtocol() != null) {
            result = result + " protocol: " + this.getProtocol().toString();
        }
        result = result + " max frame size: " + this.maxFrameSize;
        return result;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Draft_6455 that = (Draft_6455)o;
        if (this.maxFrameSize != that.getMaxFrameSize()) {
            return false;
        }
        if (this.extension != null) {
            if (this.extension.equals(that.getExtension())) {
                return (this.protocol != null) ? this.protocol.equals(that.getProtocol()) : (that.getProtocol() == null);
            }
        }
        else if (that.getExtension() == null) {
            return (this.protocol != null) ? this.protocol.equals(that.getProtocol()) : (that.getProtocol() == null);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = (this.extension != null) ? this.extension.hashCode() : 0;
        result = 31 * result + ((this.protocol != null) ? this.protocol.hashCode() : 0);
        result = 31 * result + (this.maxFrameSize ^ this.maxFrameSize >>> 32);
        return result;
    }
    
    private ByteBuffer getPayloadFromByteBufferList() throws LimitExceededException {
        long totalSize = 0L;
        final ByteBuffer resultingByteBuffer;
        synchronized (this.byteBufferList) {
            for (final ByteBuffer buffer : this.byteBufferList) {
                totalSize += buffer.limit();
            }
            this.checkBufferLimit();
            resultingByteBuffer = ByteBuffer.allocate((int)totalSize);
            for (final ByteBuffer buffer : this.byteBufferList) {
                resultingByteBuffer.put(buffer);
            }
        }
        resultingByteBuffer.flip();
        return resultingByteBuffer;
    }
    
    private long getByteBufferListSize() {
        long totalSize = 0L;
        synchronized (this.byteBufferList) {
            for (final ByteBuffer buffer : this.byteBufferList) {
                totalSize += buffer.limit();
            }
        }
        return totalSize;
    }
    
    private class TranslatedPayloadMetaData
    {
        private int payloadLength;
        private int realPackageSize;
        
        private int getPayloadLength() {
            return this.payloadLength;
        }
        
        private int getRealPackageSize() {
            return this.realPackageSize;
        }
        
        TranslatedPayloadMetaData(final int newPayloadLength, final int newRealPackageSize) {
            this.payloadLength = newPayloadLength;
            this.realPackageSize = newRealPackageSize;
        }
    }
}
