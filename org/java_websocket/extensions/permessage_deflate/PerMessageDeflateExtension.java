package org.java_websocket.extensions.permessage_deflate;

import java.util.*;
import org.java_websocket.enums.*;
import java.io.*;
import java.util.zip.*;
import java.nio.*;
import org.java_websocket.extensions.*;
import org.java_websocket.framing.*;
import org.java_websocket.exceptions.*;

public class PerMessageDeflateExtension extends CompressionExtension
{
    private static final String EXTENSION_REGISTERED_NAME = "permessage-deflate";
    private static final String SERVER_NO_CONTEXT_TAKEOVER = "server_no_context_takeover";
    private static final String CLIENT_NO_CONTEXT_TAKEOVER = "client_no_context_takeover";
    private static final String SERVER_MAX_WINDOW_BITS = "server_max_window_bits";
    private static final String CLIENT_MAX_WINDOW_BITS = "client_max_window_bits";
    private static final int serverMaxWindowBits = 32768;
    private static final int clientMaxWindowBits = 32768;
    private static final byte[] TAIL_BYTES;
    private static final int BUFFER_SIZE = 1024;
    private boolean serverNoContextTakeover;
    private boolean clientNoContextTakeover;
    private Map<String, String> requestedParameters;
    private Inflater inflater;
    private Deflater deflater;
    
    public PerMessageDeflateExtension() {
        this.serverNoContextTakeover = true;
        this.clientNoContextTakeover = false;
        this.requestedParameters = new LinkedHashMap<String, String>();
        this.inflater = new Inflater(true);
        this.deflater = new Deflater(-1, true);
    }
    
    public Inflater getInflater() {
        return this.inflater;
    }
    
    public void setInflater(final Inflater inflater) {
        this.inflater = inflater;
    }
    
    public Deflater getDeflater() {
        return this.deflater;
    }
    
    public void setDeflater(final Deflater deflater) {
        this.deflater = deflater;
    }
    
    public boolean isServerNoContextTakeover() {
        return this.serverNoContextTakeover;
    }
    
    public void setServerNoContextTakeover(final boolean serverNoContextTakeover) {
        this.serverNoContextTakeover = serverNoContextTakeover;
    }
    
    public boolean isClientNoContextTakeover() {
        return this.clientNoContextTakeover;
    }
    
    public void setClientNoContextTakeover(final boolean clientNoContextTakeover) {
        this.clientNoContextTakeover = clientNoContextTakeover;
    }
    
    @Override
    public void decodeFrame(final Framedata inputFrame) throws InvalidDataException {
        if (!(inputFrame instanceof DataFrame)) {
            return;
        }
        if (inputFrame.getOpcode() == Opcode.CONTINUOUS && inputFrame.isRSV1()) {
            throw new InvalidDataException(1008, "RSV1 bit can only be set for the first frame.");
        }
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            this.decompress(inputFrame.getPayloadData().array(), output);
            if (this.inflater.getRemaining() > 0) {
                this.inflater = new Inflater(true);
                this.decompress(inputFrame.getPayloadData().array(), output);
            }
            if (inputFrame.isFin()) {
                this.decompress(PerMessageDeflateExtension.TAIL_BYTES, output);
                if (this.clientNoContextTakeover) {
                    this.inflater = new Inflater(true);
                }
            }
        }
        catch (DataFormatException e) {
            throw new InvalidDataException(1008, e.getMessage());
        }
        if (inputFrame.isRSV1()) {
            ((DataFrame)inputFrame).setRSV1(false);
        }
        ((FramedataImpl1)inputFrame).setPayload(ByteBuffer.wrap(output.toByteArray(), 0, output.size()));
    }
    
    private void decompress(final byte[] data, final ByteArrayOutputStream outputBuffer) throws DataFormatException {
        this.inflater.setInput(data);
        final byte[] buffer = new byte[1024];
        int bytesInflated;
        while ((bytesInflated = this.inflater.inflate(buffer)) > 0) {
            outputBuffer.write(buffer, 0, bytesInflated);
        }
    }
    
    @Override
    public void encodeFrame(final Framedata inputFrame) {
        if (!(inputFrame instanceof DataFrame)) {
            return;
        }
        if (!(inputFrame instanceof ContinuousFrame)) {
            ((DataFrame)inputFrame).setRSV1(true);
        }
        this.deflater.setInput(inputFrame.getPayloadData().array());
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        final byte[] buffer = new byte[1024];
        int bytesCompressed;
        while ((bytesCompressed = this.deflater.deflate(buffer, 0, buffer.length, 2)) > 0) {
            output.write(buffer, 0, bytesCompressed);
        }
        final byte[] outputBytes = output.toByteArray();
        int outputLength = outputBytes.length;
        if (inputFrame.isFin()) {
            if (endsWithTail(outputBytes)) {
                outputLength -= PerMessageDeflateExtension.TAIL_BYTES.length;
            }
            if (this.serverNoContextTakeover) {
                this.deflater.end();
                this.deflater = new Deflater(-1, true);
            }
        }
        ((FramedataImpl1)inputFrame).setPayload(ByteBuffer.wrap(outputBytes, 0, outputLength));
    }
    
    private static boolean endsWithTail(final byte[] data) {
        if (data.length < 4) {
            return false;
        }
        final int length = data.length;
        for (int i = 0; i < PerMessageDeflateExtension.TAIL_BYTES.length; ++i) {
            if (PerMessageDeflateExtension.TAIL_BYTES[i] != data[length - PerMessageDeflateExtension.TAIL_BYTES.length + i]) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean acceptProvidedExtensionAsServer(final String inputExtension) {
        final String[] split;
        final String[] requestedExtensions = split = inputExtension.split(",");
        for (final String extension : split) {
            final ExtensionRequestData extensionData = ExtensionRequestData.parseExtensionRequest(extension);
            if ("permessage-deflate".equalsIgnoreCase(extensionData.getExtensionName())) {
                final Map<String, String> headers = extensionData.getExtensionParameters();
                this.requestedParameters.putAll(headers);
                if (this.requestedParameters.containsKey("client_no_context_takeover")) {
                    this.clientNoContextTakeover = true;
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean acceptProvidedExtensionAsClient(final String inputExtension) {
        final String[] split;
        final String[] requestedExtensions = split = inputExtension.split(",");
        for (final String extension : split) {
            final ExtensionRequestData extensionData = ExtensionRequestData.parseExtensionRequest(extension);
            if ("permessage-deflate".equalsIgnoreCase(extensionData.getExtensionName())) {
                final Map<String, String> headers = extensionData.getExtensionParameters();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getProvidedExtensionAsClient() {
        this.requestedParameters.put("client_no_context_takeover", "");
        this.requestedParameters.put("server_no_context_takeover", "");
        return "permessage-deflate; server_no_context_takeover; client_no_context_takeover";
    }
    
    @Override
    public String getProvidedExtensionAsServer() {
        return "permessage-deflate; server_no_context_takeover" + (this.clientNoContextTakeover ? "; client_no_context_takeover" : "");
    }
    
    @Override
    public IExtension copyInstance() {
        return new PerMessageDeflateExtension();
    }
    
    @Override
    public void isFrameValid(final Framedata inputFrame) throws InvalidDataException {
        if ((inputFrame instanceof TextFrame || inputFrame instanceof BinaryFrame) && !inputFrame.isRSV1()) {
            throw new InvalidFrameException("RSV1 bit must be set for DataFrames.");
        }
        if (inputFrame instanceof ContinuousFrame && (inputFrame.isRSV1() || inputFrame.isRSV2() || inputFrame.isRSV3())) {
            throw new InvalidFrameException("bad rsv RSV1: " + inputFrame.isRSV1() + " RSV2: " + inputFrame.isRSV2() + " RSV3: " + inputFrame.isRSV3());
        }
        super.isFrameValid(inputFrame);
    }
    
    @Override
    public String toString() {
        return "PerMessageDeflateExtension";
    }
    
    static {
        TAIL_BYTES = new byte[] { 0, 0, -1, -1 };
    }
}
