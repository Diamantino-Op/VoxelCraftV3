package com.diamantino.voxelcraft.common.networking.packets.codec;

import com.diamantino.voxelcraft.common.networking.packets.data.PacketBuffer;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<BasePacket> {
    @Override
    protected void encode(ChannelHandlerContext ctx, BasePacket msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getId());
        msg.writePacketData(new PacketBuffer(out));
    }
}
