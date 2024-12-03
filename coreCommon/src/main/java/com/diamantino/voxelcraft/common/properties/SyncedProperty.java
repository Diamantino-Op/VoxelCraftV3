package com.diamantino.voxelcraft.common.properties;

import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Property that automatically syncs between server and client.
 *
 * @author Diamantino
 */
public abstract class SyncedProperty<T> {
    protected T value;

    public SyncedProperty(T value) {
        this.value = value;
    }

    public void writeToBuffer(SimpleBytesNcsPacket buf) {
        //TODO
        throw new NotImplementedException();
    }

    public void readFromBuffer(SimpleBytesNcsPacket buf) {
        throw new NotImplementedException();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
