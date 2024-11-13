package com.diamantino.voxelcraft.client.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

/**
 * A utility class for GUI related methods.
 *
 * @author Diamantino
 */
public class GuiHelper {
    public static void setObjectPosition(Widget widget, int x, int y) {
        setObjectPosition(widget, x, y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public static void setObjectPosition(Widget widget, int x, int y, boolean centeredX, boolean centeredY) {
        setObjectPosition(widget, x, y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), centeredX, centeredY);
    }

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
        float scaleX = screenWidth / 1920f;
        float scaleY = screenHeight / 1080f;

        widget.setPosition(Math.round(x * scaleX), Math.round(y * scaleY));
    }

    public static void setObjectSizeX(Widget widget, int width, int height, float guiScale, boolean perfectScale) {
        setObjectSize(widget, width, height, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), guiScale, true, false, perfectScale);
    }

    public static void setObjectSizeX(Widget widget, int width, int height, int screenWidth, int screenHeight, float guiScale, boolean perfectScale) {
        setObjectSize(widget, width, height, screenWidth, screenHeight, guiScale, true, false, perfectScale);
    }

    public static void setObjectSizeY(Widget widget, int width, int height, float guiScale, boolean perfectScale) {
        setObjectSize(widget, width, height, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), guiScale, false, true, perfectScale);
    }

    public static void setObjectSizeY(Widget widget, int width, int height, int screenWidth, int screenHeight, float guiScale, boolean perfectScale) {
        setObjectSize(widget, width, height, screenWidth, screenHeight, guiScale, false, true, perfectScale);
    }

    public static void setObjectSize(Widget widget, int width, int height, float guiScale) {
        setObjectSize(widget, width, height, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), guiScale, false, false, false);
    }

    public static void setObjectSize(Widget widget, int width, int height, float guiScale, boolean scaleX, boolean scaleY) {
        setObjectSize(widget, width, height, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), guiScale, scaleX, scaleY, false);
    }

    public static void setObjectSize(Widget widget, int width, int height, int screenWidth, int screenHeight, float guiScale, boolean scaleX, boolean scaleY, boolean perfectScale) {
        float objectScaleX = screenWidth / 1920f;
        float objectScaleY = screenHeight / 1080f;

        if (scaleX) {
            width = Math.round((width * guiScale) * objectScaleX);
        }

        if (scaleY) {
            height = Math.round((height * guiScale) * objectScaleY);
        }

        if (perfectScale) {
            if (scaleX) {
                height = Math.round((height * guiScale) * objectScaleX);
            }

            if (scaleY) {
                width = Math.round((width * guiScale) * objectScaleY);
            }
        }

        widget.setSize(width, height);
    }
}
