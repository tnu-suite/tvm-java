package com.techzealot.tvm.hotspot.src.share.tools;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;

@Slf4j
public class ByteStream implements Closeable {

    private final DataInputStream dis;

    public ByteStream(byte[] content) {
        dis = new DataInputStream(new ByteArrayInputStream(content));
    }

    /**
     * @return 剩余可读字节数
     */
    @SneakyThrows
    public int available() {
        return dis.available();
    }

    @SneakyThrows
    public int readBytes(byte[] bytes) {
        return dis.read(bytes);
    }

    @SneakyThrows
    public int readU1() {
        return dis.readUnsignedByte();
    }

    @SneakyThrows
    public int readU2() {
        return dis.readUnsignedShort();
    }

    @SneakyThrows
    public int readU4() {
        return dis.readInt();
    }

    @SneakyThrows
    public int readByte() {
        return dis.readByte();
    }

    @SneakyThrows
    public int readUnsignedByte() {
        return dis.readUnsignedByte();
    }

    @SneakyThrows
    public int readUnsignedShort() {
        return dis.readUnsignedShort();
    }

    @SneakyThrows
    public int readShort() {
        return dis.readShort();
    }

    @SneakyThrows
    public int readInt() {
        return dis.readInt();
    }

    @SneakyThrows
    public float readFloat() {
        return dis.readFloat();
    }

    @SneakyThrows
    public double readDouble() {
        return dis.readDouble();
    }

    @SneakyThrows
    public long readLong() {
        return dis.readLong();
    }

    public void close() {
        try {
            dis.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
