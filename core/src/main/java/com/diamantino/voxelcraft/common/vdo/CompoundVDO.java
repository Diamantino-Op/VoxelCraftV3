package com.diamantino.voxelcraft.common.vdo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Compound VDO class. (Voxel Data Object)
 *
 * @author Diamantino
 */
public class CompoundVDO {
    /**
     * The content of the VDO.
     */
    private JSONObject vdoContent;

    /**
     * Creates a new compound VDO.
     */
    public CompoundVDO() {
        this.vdoContent = new JSONObject();
    }

    /**
     * Creates a new compound VDO.
     *
     * @param vdoContent The content of the VDO.
     */
    public CompoundVDO(JSONObject vdoContent) {
        this.vdoContent = vdoContent;
    }

    /**
     * Parses the VDO content.
     *
     * @param content The VDO content.
     */
    public void parseVDO(String content) {
        vdoContent = new JSONObject(content);
    }

    /**
     * Saves the VDO content.
     *
     * @return The VDO content.
     */
    public String saveVDO() {
        return vdoContent.toString();
    }

    /**
     * Gets all the names in the VDO.
     *
     * @return The names in the VDO.
     */
    public String[] getVDONames() {
        return JSONObject.getNames(vdoContent);
    }

    /**
     * Get the content of the VDO.
     *
     * @return The content of the VDO.
     */
    public JSONObject getContent() {
        return vdoContent;
    }

    /**
     * Removes a VDO object from the VDO.
     *
     * @param key The key of the VDO object to remove.
     */
    public void removeVDOObject(String key) {
        vdoContent.put(key, JSONObject.NULL);
    }

    // Int VDOs

    public int getIntVDO(String key) {
        return vdoContent.getInt(key);
    }

    public void setIntVDO(String key, int value) {
        vdoContent.put(key, value);
    }

    // Long VDOs

    public long getLongVDO(String key) {
        return vdoContent.getLong(key);
    }

    public void setLongVDO(String key, long value) {
        vdoContent.put(key, value);
    }

    // Float VDOs

    public float getFloatVDO(String key) {
        return vdoContent.getFloat(key);
    }

    public void setFloatVDO(String key, float value) {
        vdoContent.put(key, value);
    }

    // Double VDOs

    public double getDoubleVDO(String key) {
        return vdoContent.getDouble(key);
    }

    public void setDoubleVDO(String key, double value) {
        vdoContent.put(key, value);
    }

    // String VDOs

    public String getStringVDO(String key) {
        return vdoContent.getString(key);
    }

    public void setStringVDO(String key, String value) {
        vdoContent.put(key, value);
    }

    // Boolean VDOs

    public boolean getBooleanVDO(String key) {
        return vdoContent.getBoolean(key);
    }

    public void setBooleanVDO(String key, boolean value) {
        vdoContent.put(key, value);
    }

    // Enum VDOs

    public <T extends Enum<T>> T getEnumVDO(String key, Class<T> enumClass) {
        return vdoContent.getEnum(enumClass, key);
    }

    public <T extends Enum<T>> void setEnumVDO(String key, T value) {
        vdoContent.put(key, value);
    }

    // Array VDOs

    public ArrayVDO getArrayVDO(String key) {
        return new ArrayVDO(vdoContent.getJSONArray(key));
    }

    public void setArrayVDO(String key, ArrayVDO value) {
        vdoContent.put(key, value.getContent());
    }

    // Compound VDOs

    public CompoundVDO getCompoundVDO(String key) {
        return new CompoundVDO(vdoContent.getJSONObject(key));
    }

    public void setCompoundVDO(String key, CompoundVDO value) {
        vdoContent.put(key, value.getContent());
    }
}
