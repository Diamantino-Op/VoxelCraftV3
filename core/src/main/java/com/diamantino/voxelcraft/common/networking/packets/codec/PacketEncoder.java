package com.diamantino.voxelcraft.common.networking.packets.codec;

import com.diamantino.voxelcraft.common.networking.packets.data.PacketBuffer;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Encoder class for the packets, which writes the packet ID and the packet data to the ByteBuf.
 *
 * @author Diamantino
 */
public class PacketEncoder extends MessageToByteEncoder<BasePacket> {
    /**
     * Encodes the packet ID and the packet data to the ByteBuf.
     *
     * @param ctx The ChannelHandlerContext.
     * @param msg The packet to encode.
     * @param out The ByteBuf to write the encoded data to.
     * @throws Exception If an error occurs during encoding.
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, BasePacket msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getId());
        msg.writePacketData(new PacketBuffer(out));
    }
}
