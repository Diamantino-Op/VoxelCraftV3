package com.diamantino.voxelcraft.common.networking.packets.codec;

import com.diamantino.voxelcraft.common.networking.packets.data.Packets;
import com.diamantino.voxelcraft.common.networking.packets.data.PacketBuffer;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Decoder class that reads the incoming ByteBuf and decodes it into a BasePacket object.
 *
 * @author Diamantino
 */
public class PacketDecoder extends ByteToMessageDecoder {
    /**
     * Decodes the incoming ByteBuf into a BasePacket object.
     *
     * @param ctx The ChannelHandlerContext for which the data is being decoded.
     * @param in The ByteBuf to be decoded.
     * @param out The List to which decoded messages should be added.
     * @throws Exception If an error occurs.
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        BasePacket packet = Packets.registeredPackets.get(in.readInt()).getDeclaredConstructor().newInstance();
        packet.readPacketData(ctx.name(), new PacketBuffer(in));
        out.add(packet);
    }
}
