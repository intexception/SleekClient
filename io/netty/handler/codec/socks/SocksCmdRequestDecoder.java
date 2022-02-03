package io.netty.handler.codec.socks;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import java.util.*;
import io.netty.util.*;
import io.netty.channel.*;

public class SocksCmdRequestDecoder extends ReplayingDecoder<State>
{
    private static final String name = "SOCKS_CMD_REQUEST_DECODER";
    private SocksProtocolVersion version;
    private int fieldLength;
    private SocksCmdType cmdType;
    private SocksAddressType addressType;
    private byte reserved;
    private String host;
    private int port;
    private SocksRequest msg;
    
    @Deprecated
    public static String getName() {
        return "SOCKS_CMD_REQUEST_DECODER";
    }
    
    public SocksCmdRequestDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
        this.msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf byteBuf, final List<Object> out) throws Exception {
        Label_0315: {
            switch (this.state()) {
                case CHECK_PROTOCOL_VERSION: {
                    this.version = SocksProtocolVersion.valueOf(byteBuf.readByte());
                    if (this.version != SocksProtocolVersion.SOCKS5) {
                        break;
                    }
                    this.checkpoint(State.READ_CMD_HEADER);
                }
                case READ_CMD_HEADER: {
                    this.cmdType = SocksCmdType.valueOf(byteBuf.readByte());
                    this.reserved = byteBuf.readByte();
                    this.addressType = SocksAddressType.valueOf(byteBuf.readByte());
                    this.checkpoint(State.READ_CMD_ADDRESS);
                }
                case READ_CMD_ADDRESS: {
                    switch (this.addressType) {
                        case IPv4: {
                            this.host = SocksCommonUtils.intToIp(byteBuf.readInt());
                            this.port = byteBuf.readUnsignedShort();
                            this.msg = new SocksCmdRequest(this.cmdType, this.addressType, this.host, this.port);
                            break Label_0315;
                        }
                        case DOMAIN: {
                            this.fieldLength = byteBuf.readByte();
                            this.host = byteBuf.readBytes(this.fieldLength).toString(CharsetUtil.US_ASCII);
                            this.port = byteBuf.readUnsignedShort();
                            this.msg = new SocksCmdRequest(this.cmdType, this.addressType, this.host, this.port);
                            break Label_0315;
                        }
                        case IPv6: {
                            this.host = SocksCommonUtils.ipv6toStr(byteBuf.readBytes(16).array());
                            this.port = byteBuf.readUnsignedShort();
                            this.msg = new SocksCmdRequest(this.cmdType, this.addressType, this.host, this.port);
                            break Label_0315;
                        }
                    }
                    break;
                }
            }
        }
        ctx.pipeline().remove(this);
        out.add(this.msg);
    }
    
    enum State
    {
        CHECK_PROTOCOL_VERSION, 
        READ_CMD_HEADER, 
        READ_CMD_ADDRESS;
    }
}
