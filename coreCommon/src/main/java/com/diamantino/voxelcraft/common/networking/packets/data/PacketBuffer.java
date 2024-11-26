package com.diamantino.voxelcraft.common.networking.packets.data;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PacketBuffer extends ByteBuf {
    protected final ByteBuf buf;

    public PacketBuffer(final ByteBuf buffer) {
        this.buf = buffer;
    }

    public static int getVarIntSize(final int input) {
        for (int index = 1; index < 5; ++index) {
            if ((input & -1 << index * 7) == 0) {
                return index;
            }
        }
        return 5;
    }

    public byte[] readByteArray() {
        final byte[] bytes = new byte[this.readVarIntFromBuffer()];
        this.readBytes(bytes);
        return bytes;
    }

    public void writeByteArray(final byte @NotNull [] array) {
        this.writeVarIntToBuffer(array.length);
        this.writeBytes(array);
    }

    public final <T extends Enum<T>> T readEnumValue(final @NotNull Class<T> enumClass) {
        return (T) (enumClass.getEnumConstants())[this.readVarIntFromBuffer()];
    }

    public void writeEnumValue(final @NotNull Enum<?> value) {
        this.writeVarIntToBuffer(value.ordinal());
    }

    public int readVarIntFromBuffer() {
        int value = 0;
        int size = 0;
        while (true) {
            final byte length = this.readByte();
            value |= (length & 127) << size++ * 7;
            if (size > 5) {
                throw new RuntimeException("VarInt too big");
            }
            if ((length & 128) != 128) {
                break;
            }
        }
        return value;
    }

    public void writeVarIntToBuffer(int input) {
        while ((input & -128) != 0) {
            this.writeByte(input & 127 | 128);
            input >>>= 7;
        }
        this.writeByte(input);
    }

    public final long readVarLong() {
        long value = 0L;
        int size = 0;
        while (true) {
            byte length = this.readByte();
            value |= (long) (length & 127) << size++ * 7;
            if (size > 10) {
                throw new RuntimeException("VarLong too big");
            }
            if ((length & 128) != 128) {
                break;
            }
        }
        return value;
    }

    public void writeVarLong(long value) {
        while ((value & -128L) != 0L) {
            this.writeByte((int) (value & 127L) | 128);
            value >>>= 7;
        }
        this.writeByte((int) value);
    }

    @Contract(" -> new")
    public final @NotNull UUID readUuid() {
        return new UUID(this.readLong(), this.readLong());
    }

    public void writeUuid(final @NotNull UUID uuid) {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
    }

    public final @NotNull String readStringFromBuffer(final int maxLength) {
        final int length = this.readVarIntFromBuffer();
        if (length > maxLength * 4) {
            throw new DecoderException(
                String.format("The received encoded string buffer length is longer than maximum allowed (%s > %s)",
                    length, (maxLength * 4)));
        } else if (length < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        } else {
            final String string = this.toString(this.readerIndex(), length, StandardCharsets.UTF_8);
            this.readerIndex(this.readerIndex() + length);
            if (string.length() > length) {
                throw new DecoderException(String.format(
                    "The received string length is longer than maximum allowed (%s > %s)", length, maxLength));
            } else {
                return string;
            }
        }
    }

    public PacketBuffer writeString(final @NotNull String string) {
        final byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > 32767) {
            throw new EncoderException(
                String.format("String too big (was %s bytes encoded, max 32767)", string.length()));
        } else {
            this.writeVarIntToBuffer(bytes.length);
            this.writeBytes(bytes);
            return this;
        }
    }

    @Override
    public final int refCnt() {
        return this.buf.refCnt();
    }

    @Override
    public final boolean release() {
        return this.buf.release();
    }

    @Override
    public final boolean release(final int decrement) {
        return this.buf.release(decrement);
    }

    @Override
    public final int capacity() {
        return this.buf.capacity();
    }

    @Override
    public final ByteBuf capacity(final int newCapacity) {
        return this.buf.capacity(newCapacity);
    }

    @Override
    public final int maxCapacity() {
        return this.buf.maxCapacity();
    }

    @Override
    public final ByteBufAllocator alloc() {
        return this.buf.alloc();
    }

    @Override
    @SuppressWarnings("deprecation")
    public final ByteOrder order() {
        return this.buf.order();
    }

    @Override
    @SuppressWarnings("deprecation")
    public final ByteBuf order(ByteOrder endianness) {
        return this.buf.order(endianness);
    }

    @Override
    public final ByteBuf unwrap() {
        return this.buf.unwrap();
    }

    @Override
    public final boolean isDirect() {
        return this.buf.isDirect();
    }

    @Override
    public final boolean isReadOnly() {
        return this.buf.isReadOnly();
    }

    @Override
    public final ByteBuf asReadOnly() {
        return this.buf.asReadOnly();
    }

    @Override
    public final int readerIndex() {
        return this.buf.readerIndex();
    }

    @Override
    public final ByteBuf readerIndex(final int readerIndex) {
        return this.buf.readerIndex(readerIndex);
    }

    @Override
    public final int writerIndex() {
        return this.buf.writerIndex();
    }

    @Override
    public final ByteBuf writerIndex(final int writerIndex) {
        return this.buf.writerIndex(writerIndex);
    }

    @Override
    public final ByteBuf setIndex(final int readerIndex, final int writerIndex) {
        return this.buf.setIndex(readerIndex, writerIndex);
    }

    @Override
    public final int readableBytes() {
        return this.buf.readableBytes();
    }

    @Override
    public final int writableBytes() {
        return this.buf.writableBytes();
    }

    @Override
    public final int maxWritableBytes() {
        return this.buf.maxWritableBytes();
    }

    @Override
    public final boolean isReadable() {
        return this.buf.isReadable();
    }

    @Override
    public final boolean isReadable(final int size) {
        return this.buf.isReadable(size);
    }

    @Override
    public final boolean isWritable() {
        return this.buf.isWritable();
    }

    @Override
    public final boolean isWritable(final int size) {
        return this.buf.isWritable(size);
    }

    @Override
    public final ByteBuf clear() {
        return this.buf.clear();
    }

    @Override
    public final ByteBuf markReaderIndex() {
        return this.buf.markReaderIndex();
    }

    @Override
    public final ByteBuf resetReaderIndex() {
        return this.buf.resetReaderIndex();
    }

    @Override
    public final ByteBuf markWriterIndex() {
        return this.buf.markWriterIndex();
    }

    @Override
    public final ByteBuf resetWriterIndex() {
        return this.buf.resetReaderIndex();
    }

    @Override
    public final ByteBuf discardReadBytes() {
        return this.buf.discardReadBytes();
    }

    @Override
    public final ByteBuf discardSomeReadBytes() {
        return this.buf.discardSomeReadBytes();
    }

    @Override
    public final ByteBuf ensureWritable(final int minWritableBytes) {
        return this.buf.ensureWritable(minWritableBytes);
    }

    @Override
    public final int ensureWritable(final int minWritableBytes, final boolean force) {
        return this.buf.ensureWritable(minWritableBytes, force);
    }

    @Override
    public final boolean getBoolean(final int index) {
        return this.buf.getBoolean(index);
    }

    @Override
    public byte getByte(final int index) {
        return this.buf.getByte(index);
    }

    @Override
    public final short getUnsignedByte(final int index) {
        return this.buf.getUnsignedByte(index);
    }

    @Override
    public final short getShort(final int index) {
        return this.buf.getShort(index);
    }

    @Override
    public final short getShortLE(final int index) {
        return this.buf.getShortLE(index);
    }

    @Override
    public final int getUnsignedShort(final int index) {
        return this.buf.getUnsignedShort(index);
    }

    @Override
    public final int getUnsignedShortLE(final int index) {
        return this.buf.getUnsignedShortLE(index);
    }

    @Override
    public final int getMedium(final int index) {
        return this.buf.getMedium(index);
    }

    @Override
    public final int getMediumLE(final int index) {
        return this.buf.getMediumLE(index);
    }

    @Override
    public final int getUnsignedMedium(final int index) {
        return this.buf.getUnsignedMedium(index);
    }

    @Override
    public final int getUnsignedMediumLE(final int index) {
        return this.buf.getUnsignedMediumLE(index);
    }

    @Override
    public final int getInt(final int index) {
        return this.buf.getInt(index);
    }

    @Override
    public final int getIntLE(final int index) {
        return this.buf.getIntLE(index);
    }

    @Override
    public final long getUnsignedInt(final int index) {
        return this.buf.getUnsignedInt(index);
    }

    @Override
    public final long getUnsignedIntLE(final int index) {
        return this.buf.getUnsignedIntLE(index);
    }

    @Override
    public final long getLong(final int index) {
        return this.buf.getLong(index);
    }

    @Override
    public final long getLongLE(final int index) {
        return this.buf.getLongLE(index);
    }

    @Override
    public char getChar(final int index) {
        return this.buf.getChar(index);
    }

    @Override
    public float getFloat(final int index) {
        return this.buf.getFloat(index);
    }

    @Override
    public double getDouble(final int index) {
        return this.buf.getDouble(index);
    }

    @Override
    public final ByteBuf getBytes(final int index, final ByteBuf dst) {
        return this.buf.getBytes(index, dst);
    }

    @Override
    public final ByteBuf getBytes(final int index, final ByteBuf dst, final int length) {
        return this.buf.getBytes(index, dst, length);
    }

    @Override
    public final ByteBuf getBytes(final int index, final ByteBuf dst, final int dstIndex, final int length) {
        return this.buf.getBytes(index, dst, dstIndex, length);
    }

    @Override
    public final ByteBuf getBytes(final int index, final byte[] dst) {
        return this.buf.getBytes(index, dst);
    }

    @Override
    public final ByteBuf getBytes(final int index, final byte[] dst, final int dstIndex, final int length) {
        return this.buf.getBytes(index, dst, dstIndex, length);
    }

    @Override
    public final ByteBuf getBytes(final int index, final ByteBuffer dst) {
        return this.buf.getBytes(index, dst);
    }

    @Override
    public final ByteBuf getBytes(final int index, OutputStream out, final int length) throws IOException {
        return this.buf.getBytes(index, out, length);
    }

    @Override
    public final int getBytes(final int index, GatheringByteChannel out, final int length) throws IOException {
        return this.buf.getBytes(index, out, length);
    }

    @Override
    public final int getBytes(final int index, FileChannel out, final long position, final int length) throws IOException {
        return this.buf.getBytes(index, out, position, length);
    }

    @Override
    public CharSequence getCharSequence(final int index, final int length, final Charset charset) {
        return this.buf.getCharSequence(index, length, charset);
    }

    @Override
    public final ByteBuf setBoolean(final int index, final boolean value) {
        return this.buf.setBoolean(index, value);
    }

    @Override
    public final ByteBuf setByte(final int index, final int value) {
        return this.buf.setByte(index, value);
    }

    @Override
    public final ByteBuf setShort(final int index, final int value) {
        return this.buf.setShort(index, value);
    }

    @Override
    public final ByteBuf setShortLE(final int index, final int value) {
        return this.buf.setShortLE(index, value);
    }

    @Override
    public final ByteBuf setMedium(final int index, final int value) {
        return this.buf.setMedium(index, value);
    }

    @Override
    public final ByteBuf setMediumLE(final int index, final int value) {
        return this.buf.setMediumLE(index, value);
    }

    @Override
    public final ByteBuf setInt(final int index, final int value) {
        return this.buf.setInt(index, value);
    }

    @Override
    public final ByteBuf setIntLE(final int index, final int value) {
        return this.buf.setIntLE(index, value);
    }

    @Override
    public final ByteBuf setLong(final int index, final long value) {
        return this.buf.setLong(index, value);
    }

    @Override
    public final ByteBuf setLongLE(final int index, final long value) {
        return this.buf.setLongLE(index, value);
    }

    @Override
    public final ByteBuf setChar(final int index, final int value) {
        return this.buf.setChar(index, value);
    }

    @Override
    public final ByteBuf setFloat(final int index, float value) {
        return this.buf.setFloat(index, value);
    }

    @Override
    public final ByteBuf setDouble(final int index, double value) {
        return this.buf.setDouble(index, value);
    }

    @Override
    public final ByteBuf setBytes(final int index, final ByteBuf src) {
        return this.buf.setBytes(index, src);
    }

    @Override
    public final ByteBuf setBytes(final int index, final ByteBuf src, final int length) {
        return this.buf.setBytes(index, src, length);
    }

    @Override
    public final ByteBuf setBytes(final int index, final ByteBuf src, final int srcIndex, final int length) {
        return this.buf.setBytes(srcIndex, src, srcIndex, length);
    }

    @Override
    public final ByteBuf setBytes(final int index, final byte[] src) {
        return this.buf.setBytes(index, src);
    }

    @Override
    public final ByteBuf setBytes(final int index, final byte[] src, final int srcIndex, final int length) {
        return this.buf.setBytes(index, src, srcIndex, length);
    }

    @Override
    public final ByteBuf setBytes(final int index, final ByteBuffer src) {
        return this.buf.setBytes(index, buf);
    }

    @Override
    public final int setBytes(final int index, InputStream in, final int length) throws IOException {
        return this.buf.setBytes(index, in, length);
    }

    @Override
    public final int setBytes(final int index, ScatteringByteChannel in, final int length) throws IOException {
        return this.buf.setBytes(index, in, length);
    }

    @Override
    public final int setBytes(final int index, FileChannel in, final long position, final int length)
        throws IOException {
        return this.buf.setBytes(index, in, position, length);
    }

    @Override
    public final ByteBuf setZero(final int index, final int length) {
        return this.buf.setZero(index, length);
    }

    @Override
    public final int setCharSequence(final int index, CharSequence sequence, final Charset charset) {
        return this.buf.setCharSequence(index, sequence, charset);
    }

    @Override
    public final boolean readBoolean() {
        return this.buf.readBoolean();
    }

    @Override
    public byte readByte() {
        return this.buf.readByte();
    }

    @Override
    public final short readUnsignedByte() {
        return this.buf.readUnsignedByte();
    }

    @Override
    public final short readShort() {
        return this.buf.readShort();
    }

    @Override
    public final short readShortLE() {
        return this.buf.readShortLE();
    }

    @Override
    public final int readUnsignedShort() {
        return this.buf.readUnsignedShort();
    }

    @Override
    public final int readUnsignedShortLE() {
        return this.buf.readUnsignedShortLE();
    }

    @Override
    public final int readMedium() {
        return this.buf.readMedium();
    }

    @Override
    public final int readMediumLE() {
        return this.buf.readMediumLE();
    }

    @Override
    public final int readUnsignedMedium() {
        return this.buf.readUnsignedMedium();
    }

    @Override
    public final int readUnsignedMediumLE() {
        return this.buf.readUnsignedMediumLE();
    }

    @Override
    public final int readInt() {
        return this.buf.readInt();
    }

    @Override
    public final int readIntLE() {
        return this.buf.readIntLE();
    }

    @Override
    public final long readUnsignedInt() {
        return this.buf.readUnsignedInt();
    }

    @Override
    public final long readUnsignedIntLE() {
        return this.buf.readUnsignedIntLE();
    }

    @Override
    public final long readLong() {
        return this.buf.readLong();
    }

    @Override
    public final long readLongLE() {
        return this.buf.readLongLE();
    }

    @Override
    public char readChar() {
        return this.buf.readChar();
    }

    @Override
    public float readFloat() {
        return this.buf.readFloat();
    }

    @Override
    public double readDouble() {
        return this.buf.readDouble();
    }

    @Override
    public final ByteBuf readBytes(final int length) {
        return this.buf.readBytes(length);
    }

    @Override
    public final ByteBuf readSlice(final int length) {
        return this.buf.readSlice(length);
    }

    @Override
    public final ByteBuf readRetainedSlice(final int length) {
        return this.buf.readRetainedSlice(length);
    }

    @Override
    public final ByteBuf readBytes(ByteBuf dst) {
        return this.buf.readBytes(dst);
    }

    @Override
    public final ByteBuf readBytes(ByteBuf dst, final int length) {
        return this.buf.readBytes(dst, length);
    }

    @Override
    public final ByteBuf readBytes(ByteBuf dst, final int dstIndex, final int length) {
        return this.buf.readBytes(dst, dstIndex, length);
    }

    @Override
    public final ByteBuf readBytes(final byte[] dst) {
        return this.buf.readBytes(dst);
    }

    @Override
    public final ByteBuf readBytes(final byte[] dst, final int dstIndex, final int length) {
        return this.buf.readBytes(dst, dstIndex, length);
    }

    @Override
    public final ByteBuf readBytes(ByteBuffer dst) {
        return this.buf.readBytes(dst);
    }

    @Override
    public final ByteBuf readBytes(OutputStream out, final int length) throws IOException {
        return this.buf.readBytes(out, length);
    }

    @Override
    public final int readBytes(GatheringByteChannel out, final int length) throws IOException {
        return this.buf.readBytes(out, length);
    }

    @Override
    public CharSequence readCharSequence(final int length, final Charset charset) {
        return this.buf.readCharSequence(length, charset);
    }

    @Override
    public final int readBytes(FileChannel out, final long position, final int length) throws IOException {
        return this.buf.readBytes(out, position, length);
    }

    @Override
    public final ByteBuf skipBytes(final int length) {
        return this.buf.skipBytes(length);
    }

    @Override
    public final ByteBuf writeBoolean(boolean value) {
        return this.buf.writeBoolean(value);
    }

    @Override
    public final ByteBuf writeByte(final int value) {
        return this.buf.writeByte(value);
    }

    @Override
    public final ByteBuf writeShort(final int value) {
        return this.buf.writeShort(value);
    }

    @Override
    public final ByteBuf writeShortLE(final int value) {
        return this.buf.writeShortLE(value);
    }

    @Override
    public final ByteBuf writeMedium(final int value) {
        return this.buf.writeMedium(value);
    }

    @Override
    public final ByteBuf writeMediumLE(final int value) {
        return this.buf.writeMediumLE(value);
    }

    @Override
    public final ByteBuf writeInt(final int value) {
        return this.buf.writeInt(value);
    }

    @Override
    public final ByteBuf writeIntLE(final int value) {
        return this.buf.writeIntLE(value);
    }

    @Override
    public final ByteBuf writeLong(long value) {
        return this.buf.writeLong(value);
    }

    @Override
    public final ByteBuf writeLongLE(long value) {
        return this.buf.writeLongLE(value);
    }

    @Override
    public final ByteBuf writeChar(final int value) {
        return this.buf.writeChar(value);
    }

    @Override
    public final ByteBuf writeFloat(float value) {
        return this.buf.writeFloat(value);
    }

    @Override
    public final ByteBuf writeDouble(double value) {
        return this.buf.writeDouble(value);
    }

    @Override
    public final ByteBuf writeBytes(ByteBuf src) {
        return this.buf.writeBytes(src);
    }

    @Override
    public final ByteBuf writeBytes(ByteBuf src, final int length) {
        return this.buf.writeBytes(src, length);
    }

    @Override
    public final ByteBuf writeBytes(ByteBuf src, final int srcIndex, final int length) {
        return this.buf.writeBytes(src, srcIndex, length);
    }

    @Override
    public final ByteBuf writeBytes(final byte[] src) {
        return this.buf.writeBytes(src);
    }

    @Override
    public final ByteBuf writeBytes(final byte[] src, final int srcIndex, final int length) {
        return this.buf.writeBytes(src, srcIndex, length);
    }

    @Override
    public final ByteBuf writeBytes(ByteBuffer src) {
        return this.buf.writeBytes(src);
    }

    @Override
    public final int writeBytes(InputStream in, final int length) throws IOException {
        return this.buf.writeBytes(in, length);
    }

    @Override
    public final int writeBytes(ScatteringByteChannel in, final int length) throws IOException {
        return this.buf.writeBytes(in, length);
    }

    @Override
    public final int writeBytes(FileChannel in, final long position, final int length) throws IOException {
        return this.buf.writeBytes(in, position, length);
    }

    @Override
    public final ByteBuf writeZero(final int length) {
        return this.buf.writeZero(length);
    }

    @Override
    public final int writeCharSequence(CharSequence sequence, final Charset charset) {
        return this.buf.writeCharSequence(sequence, charset);
    }

    @Override
    public final int indexOf(final int fromIndex, final int toIndex, final byte value) {
        return this.buf.indexOf(fromIndex, toIndex, value);
    }

    @Override
    public final int bytesBefore(byte value) {
        return this.buf.bytesBefore(value);
    }

    @Override
    public final int bytesBefore(final int length, final byte value) {
        return this.buf.bytesBefore(length, value);
    }

    @Override
    public final int bytesBefore(final int index, final int length, final byte value) {
        return this.buf.bytesBefore(index, length, value);
    }

    @Override
    public final int forEachByte(ByteProcessor processor) {
        return this.buf.forEachByte(processor);
    }

    @Override
    public final int forEachByte(final int index, final int length, final ByteProcessor processor) {
        return this.buf.forEachByte(index, length, processor);
    }

    @Override
    public final int forEachByteDesc(ByteProcessor processor) {
        return this.buf.forEachByteDesc(processor);
    }

    @Override
    public final int forEachByteDesc(final int index, final int length, final ByteProcessor processor) {
        return this.buf.forEachByte(index, length, processor);
    }

    @Override
    public final ByteBuf copy() {
        return this.buf.copy();
    }

    @Override
    public final ByteBuf copy(final int index, final int length) {
        return this.buf.copy(index, length);
    }

    @Override
    public final ByteBuf slice() {
        return this.buf.slice();
    }

    @Override
    public final ByteBuf retainedSlice() {
        return this.buf.retainedSlice();
    }

    @Override
    public final ByteBuf slice(final int index, final int length) {
        return this.buf.slice();
    }

    @Override
    public final ByteBuf retainedSlice(final int index, final int length) {
        return this.buf.retainedSlice(index, length);
    }

    @Override
    public final ByteBuf duplicate() {
        return this.buf.duplicate();
    }

    @Override
    public final ByteBuf retainedDuplicate() {
        return this.buf.retainedDuplicate();
    }

    @Override
    public final int nioBufferCount() {
        return this.buf.nioBufferCount();
    }

    @Override
    public final ByteBuffer nioBuffer() {
        return this.buf.nioBuffer();
    }

    @Override
    public final ByteBuffer nioBuffer(final int index, final int length) {
        return this.buf.nioBuffer(index, length);
    }

    @Override
    public final ByteBuffer internalNioBuffer(final int index, final int length) {
        return this.buf.internalNioBuffer(index, length);
    }

    @Override
    public final ByteBuffer[] nioBuffers() {
        return this.buf.nioBuffers();
    }

    @Override
    public final ByteBuffer[] nioBuffers(final int index, final int length) {
        return this.buf.nioBuffers(index, length);
    }

    @Override
    public final boolean hasArray() {
        return this.buf.hasArray();
    }

    @Override
    public final byte[] array() {
        return this.buf.array();
    }

    @Override
    public final int arrayOffset() {
        return this.buf.arrayOffset();
    }

    @Override
    public final boolean hasMemoryAddress() {
        return this.buf.hasMemoryAddress();
    }

    @Override
    public final long memoryAddress() {
        return this.buf.memoryAddress();
    }

    @Override
    public final String toString(Charset charset) {
        return this.buf.toString(charset);
    }

    @Override
    public final String toString(final int index, final int length, final Charset charset) {
        return this.buf.toString(index, length, charset);
    }

    @Override
    public final int hashCode() {
        return this.buf.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        return this.buf.equals(obj);
    }

    @Override
    public final int compareTo(ByteBuf buffer) {
        return this.buf.compareTo(buffer);
    }

    @Override
    public final String toString() {
        return this.buf.toString();
    }

    @Override
    public final ByteBuf retain(final int increment) {
        return this.buf.retain(increment);
    }

    @Override
    public final ByteBuf retain() {
        return this.buf.retain();
    }

    @Override
    public final ByteBuf touch() {
        return this.buf.touch();
    }

    @Override
    public final ByteBuf touch(final Object hint) {
        return this.buf.touch(hint);
    }
}
