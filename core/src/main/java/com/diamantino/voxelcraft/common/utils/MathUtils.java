package com.diamantino.voxelcraft.common.utils;

import java.util.Map;

/**
 * Utility class for mathematical operations.
 *
 * @author Diamantino
 */
public class MathUtils {
    /**
     * Returns the nearest power of 2 value greater than or equal to the given value.
     *
     * @param value The value to calculate the nearest power of 2.
     * @return The nearest power of 2 value greater than or equal to the given value.
     */
    public static int getNearestPO2(int value) {
        int highestOneBit = Integer.highestOneBit(value);
        if (value == highestOneBit) {
            return value;
        }
        return highestOneBit << 1;
    }

    /**
     * Get the first available short in a map.
     *
     * @param map The map to check.
     *
     * @return The first available short in the map.
     */
    public static short getFirstAvailableShort(Map<Short, ?> map) {
        short i = 0;

        while (map.containsValue(i)) {
            i++;
        }

        return i;
    }
}
