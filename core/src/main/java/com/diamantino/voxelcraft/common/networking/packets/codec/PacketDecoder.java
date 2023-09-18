package com.diamantino.voxelcraft.common.networking.packets.codec;

import com.diamantino.voxelcraft.common.networking.packets.data.Packets;
import com.diamantino.voxelcraft.common.networking.packets.data.PacketBuffer;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        BasePacket packet = Packets.packets.get(in.readInt()).getDeclaredConstructor().newInstance();
        packet.readPacketData(ctx.name(), new PacketBuffer(in));
        out.add(packet);
    }
}
