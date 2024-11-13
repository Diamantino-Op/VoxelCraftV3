package com.diamantino.voxelcraft.client.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Widget;

/**
 * A utility class for GUI related methods.
 *
 * @author Diamantino
 */
public class GuiHelper {
    public static void setObjectPosition(Widget widget, int x, int y, int screenWidth, int screenHeight, boolean centeredX, boolean centeredY) {
        if (centeredX) {
            x = Math.round(((float) screenWidth / 2) - (widget.getWidth() / 2)) + x;
        }

        if (centeredY) {
            y = Math.round(((float) screenHeight / 2) - (widget.getHeight() / 2)) + y;
        }

        setObjectPosition(widget, x, y, screenWidth, screenHeight);
    }

    public static void setObjectPosition(Widget widget, int x, int y, int screenWidth, int screenHeight) {

    }

    public static void setObjectSize(Widget widget, int width, int height, int screenWidth, int screenHeight, float guiScale) {

    }
}
