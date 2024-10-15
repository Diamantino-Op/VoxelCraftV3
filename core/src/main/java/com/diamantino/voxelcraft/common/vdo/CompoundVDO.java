package com.diamantino.voxelcraft.common.vdo;

import com.badlogic.gdx.Gdx;
import com.diamantino.voxelcraft.common.Constants;
import org.json.JSONArray;
import org.json.JSONException;
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
        try {
            vdoContent.put(key, JSONObject.NULL);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error removing VDO: " + key, e);
        }
    }

    // Int VDOs

    public int getIntVDO(String key) {
        try {
            return vdoContent.getInt(key);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting int VDO: " + key, e);

            return 0;
        }
    }

    public void setIntVDO(String key, int value) {
        try {
            vdoContent.put(key, value);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting int VDO: " + key, e);
        }
    }

    // Long VDOs

    public long getLongVDO(String key) {
        try {
            return vdoContent.getLong(key);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting long VDO: " + key, e);

            return 0;
        }
    }

    public void setLongVDO(String key, long value) {
        try {
            vdoContent.put(key, value);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting long VDO: " + key, e);
        }
    }

    // Float VDOs

    public float getFloatVDO(String key) {
        try {
            return vdoContent.getFloat(key);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting float VDO: " + key, e);

            return 0;
        }
    }

    public void setFloatVDO(String key, float value) {
        try {
            vdoContent.put(key, value);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting float VDO: " + key, e);
        }
    }

    // Double VDOs

    public double getDoubleVDO(String key) {
        try {
            return vdoContent.getDouble(key);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting double VDO: " + key, e);

            return 0;
        }
    }

    public void setDoubleVDO(String key, double value) {
        try {
            vdoContent.put(key, value);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting double VDO: " + key, e);
        }
    }

    // String VDOs

    public String getStringVDO(String key) {
        try {
            return vdoContent.getString(key);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting string VDO: " + key, e);

            return null;
        }
    }

    public void setStringVDO(String key, String value) {
        try {
            vdoContent.put(key, value);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting string VDO: " + key, e);
        }
    }

    // Boolean VDOs

    public boolean getBooleanVDO(String key) {
        try {
            return vdoContent.getBoolean(key);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting boolean VDO: " + key, e);

            return false;
        }
    }

    public void setBooleanVDO(String key, boolean value) {
        try {
            vdoContent.put(key, value);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting boolean VDO: " + key, e);
        }
    }

    // Enum VDOs

    public <T extends Enum<T>> T getEnumVDO(String key, Class<T> enumClass) {
        try {
            return vdoContent.getEnum(enumClass, key);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting enum VDO: " + key, e);

            return null;
        }
    }

    public <T extends Enum<T>> void setEnumVDO(String key, T value) {
        try {
            vdoContent.put(key, value);
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting enum VDO: " + key, e);
        }
    }

    // Array VDOs

    public ArrayVDO getArrayVDO(String key) {
        try {
            return new ArrayVDO(vdoContent.getJSONArray(key));
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting array VDO: " + key, e);

            return null;
        }
    }

    public void setArrayVDO(String key, ArrayVDO value) {
            try {
                vdoContent.put(key, value.getContent());
            } catch (Exception e) {
                Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting array VDO: " + key, e);
            }
    }

    // Compound VDOs

    public CompoundVDO getCompoundVDO(String key) {
        try {
            return new CompoundVDO(vdoContent.getJSONObject(key));
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error getting compound VDO: " + key, e);

            return null;
        }
    }

    public void setCompoundVDO(String key, CompoundVDO value) {
        try {
            vdoContent.put(key, value.getContent());
        } catch (Exception e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag,"Error setting compound VDO: " + key, e);
        }
    }
}
