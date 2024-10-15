package com.diamantino.voxelcraft.common.vdo;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Array VDO class. (Voxel Data Object)
 *
 * @author Diamantino
 */
public class ArrayVDO {
    /**
     * The content of the VDO.
     */
    private final JSONArray vdoContent;

    /**
     * Creates a new Array VDO.
     */
    public ArrayVDO() {
        this.vdoContent = new JSONArray();
    }

    /**
     * Creates a new Array VDO with the specified content.
     *
     * @param vdoContent The content of the VDO.
     */
    public ArrayVDO(JSONArray vdoContent) {
        this.vdoContent = vdoContent;
    }

    /**
     * Get the content of the VDO.
     *
     * @return The content of the VDO.
     */
    public JSONArray getContent() {
        return vdoContent;
    }

    /**
     * Removes a VDO object from the VDO.
     *
     * @param index The index of the VDO object to remove.
     */
    public void removeVDOObject(int index) {
        vdoContent.put(index, JSONObject.NULL);
    }

    // Int VDOs

    public int getIntVDO(int index) {
        return vdoContent.getInt(index);
    }

    public void setIntVDO(int index, int value) {
        vdoContent.put(index, value);
    }

    // Long VDOs

    public long getLongVDO(int index) {
        return vdoContent.getLong(index);
    }

    public void setLongVDO(int index, long value) {
        vdoContent.put(index, value);
    }

    // Float VDOs

    public float getFloatVDO(int index) {
        return vdoContent.getFloat(index);
    }

    public void setFloatVDO(int index, float value) {
        vdoContent.put(index, value);
    }

    // Double VDOs

    public double getDoubleVDO(int index) {
        return vdoContent.getDouble(index);
    }

    public void setDoubleVDO(int index, double value) {
        vdoContent.put(index, value);
    }

    // String VDOs

    public String getStringVDO(int index) {
        return vdoContent.getString(index);
    }

    public void setStringVDO(int index, String value) {
        vdoContent.put(index, value);
    }

    // Boolean VDOs

    public boolean getBooleanVDO(int index) {
        return vdoContent.getBoolean(index);
    }

    public void setBooleanVDO(int index, boolean value) {
        vdoContent.put(index, value);
    }

    // Enum VDOs

    public <T extends Enum<T>> T getEnumVDO(int index, Class<T> enumClass) {
        return vdoContent.getEnum(enumClass, index);
    }

    public <T extends Enum<T>> void setEnumVDO(int index, T value) {
        vdoContent.put(index, value);
    }

    // Array VDOs

    public ArrayVDO getArrayVDO(int index) {
        return new ArrayVDO(vdoContent.getJSONArray(index));
    }

    public void setArrayVDO(int index, ArrayVDO value) {
        vdoContent.put(index, value.getContent());
    }

    // Compound VDOs

    public CompoundVDO getCompoundVDO(int index) {
        return new CompoundVDO(vdoContent.getJSONObject(index));
    }

    public void setCompoundVDO(int index, CompoundVDO value) {
        vdoContent.put(index, value.getContent());
    }
}
