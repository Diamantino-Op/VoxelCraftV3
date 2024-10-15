package com.diamantino.voxelcraft.common.vdo;

import com.badlogic.gdx.Gdx;
import com.diamantino.voxelcraft.common.Constants;
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
        try {
            vdoContent.put(index, JSONObject.NULL);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error removing VDO: " + index, e);
        }
    }

    // Int VDOs

    public int getIntVDO(int index) {
        try {
            return vdoContent.getInt(index);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting int VDO: " + index, e);

            return 0;
        }
    }

    public void setIntVDO(int index, int value) {
        try {
            vdoContent.put(index, value);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting int VDO: " + index, e);
        }
    }

    // Long VDOs

    public long getLongVDO(int index) {
        try {
            return vdoContent.getLong(index);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting long VDO: " + index, e);

            return 0;
        }
    }

    public void setLongVDO(int index, long value) {
        try {
            vdoContent.put(index, value);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting long VDO: " + index, e);
        }
    }

    // Float VDOs

    public float getFloatVDO(int index) {
        try {
            return vdoContent.getFloat(index);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting float VDO: " + index, e);

            return 0;
        }
    }

    public void setFloatVDO(int index, float value) {
        try {
            vdoContent.put(index, value);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting float VDO: " + index, e);
        }
    }

    // Double VDOs

    public double getDoubleVDO(int index) {
        try {
            return vdoContent.getDouble(index);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting double VDO: " + index, e);

            return 0;
        }
    }

    public void setDoubleVDO(int index, double value) {
        try {
            vdoContent.put(index, value);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting double VDO: " + index, e);
        }
    }

    // String VDOs

    public String getStringVDO(int index) {
        try {
            return vdoContent.getString(index);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting string VDO: " + index, e);

            return null;
        }
    }

    public void setStringVDO(int index, String value) {
        try {
            vdoContent.put(index, value);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting string VDO: " + index, e);
        }
    }

    // Boolean VDOs

    public boolean getBooleanVDO(int index) {
        try {
            return vdoContent.getBoolean(index);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting boolean VDO: " + index, e);

            return false;
        }
    }

    public void setBooleanVDO(int index, boolean value) {
        try {
            vdoContent.put(index, value);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting boolean VDO: " + index, e);
        }
    }

    // Enum VDOs

    public <T extends Enum<T>> T getEnumVDO(int index, Class<T> enumClass) {
        try {
            return vdoContent.getEnum(enumClass, index);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting enum VDO: " + index, e);

            return null;
        }
    }

    public <T extends Enum<T>> void setEnumVDO(int index, T value) {
        try {
            vdoContent.put(index, value);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting enum VDO: " + index, e);
        }
    }

    // Array VDOs

    public ArrayVDO getArrayVDO(int index) {
        try {
            return new ArrayVDO(vdoContent.getJSONArray(index));
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting array VDO: " + index, e);

            return null;
        }
    }

    public void setArrayVDO(int index, ArrayVDO value) {
        try {
            vdoContent.put(index, value.getContent());
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting array VDO: " + index, e);
        }
    }

    // Compound VDOs

    public CompoundVDO getCompoundVDO(int index) {
        try {
            return new CompoundVDO(vdoContent.getJSONObject(index));
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting compound VDO: " + index, e);

            return null;
        }
    }

    public void setCompoundVDO(int index, CompoundVDO value) {
        try {
            vdoContent.put(index, value.getContent());
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting compound VDO: " + index, e);
        }
    }
}
