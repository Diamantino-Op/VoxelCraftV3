package com.diamantino.voxelcraft.client.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

/**
 * A utility class for GUI related methods.
 *
 * @author Diamantino
 */
public class GuiHelper {
    public static void setObjectPosition(Actor widget, int x, int y) {
        widget.setPosition(x, y);
    }

    public static void setObjectPosition(Actor widget, int x, int y, boolean centeredX, boolean centeredY) {
        setObjectPosition(widget, x, y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), centeredX, centeredY);
    }

    public static void setObjectPosition(Actor widget, int x, int y, int screenWidth, int screenHeight, boolean centeredX, boolean centeredY) {
        if (centeredX) {
            x = Math.round(((float) screenWidth / 2) - (widget.getWidth() / 2)) + x;
        }

        if (centeredY) {
            y = Math.round(((float) screenHeight / 2) - (widget.getHeight() / 2)) + y;
        }

        setObjectPosition(widget, x, y);
    }

    public static void setObjectSizeX(Actor widget, int width, int height, float guiScale, boolean perfectScale) {
        setObjectSize(widget, width, height, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), guiScale, true, false, perfectScale);
    }

    public static void setObjectSizeX(Actor widget, int width, int height, int screenWidth, int screenHeight, float guiScale, boolean perfectScale) {
        setObjectSize(widget, width, height, screenWidth, screenHeight, guiScale, true, false, perfectScale);
    }

    public static void setObjectSizeY(Actor widget, int width, int height, float guiScale, boolean perfectScale) {
        setObjectSize(widget, width, height, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), guiScale, false, true, perfectScale);
    }

    public static void setObjectSizeY(Actor widget, int width, int height, int screenWidth, int screenHeight, float guiScale, boolean perfectScale) {
        setObjectSize(widget, width, height, screenWidth, screenHeight, guiScale, false, true, perfectScale);
    }

    public static void setObjectSize(Actor widget, int width, int height, float guiScale) {
        setObjectSize(widget, width, height, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), guiScale, false, false, false);
    }

    public static void setObjectSize(Actor widget, int width, int height, float guiScale, boolean scaleX, boolean scaleY) {
        setObjectSize(widget, width, height, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), guiScale, scaleX, scaleY, false);
    }

    public static void setObjectSize(Actor widget, int width, int height, int screenWidth, int screenHeight, float guiScale, boolean scaleX, boolean scaleY, boolean perfectScale) {
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
