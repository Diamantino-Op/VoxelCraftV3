package com.diamantino.voxelcraft.utils;

public class MathUtils {
    public static int getNearestPO2(int value) {
        int highestOneBit = Integer.highestOneBit(value);
        if (value == highestOneBit) {
            return value;
        }
        return highestOneBit << 1;
    }
}
