package com.mygdx.game.config;

public class GameConfig {

    public static final float MOWER_WIDTH = 30f;
    public static final float MOWER_HEIGHT = 30f;
    public static final float MAX_MOWER_R_SPEED = 180;   // 1s 360
    public static final float MAX_MOWER_SPEED = 1;

    public static float WIDTH = 32 * 30;
    public static float HEIGHT = 32 * 24f;
    public static float W_WIDTH = 32 * 20;
    public static float W_HEIGHT = 32 * 100f;

    public static float POSITION_X = (W_WIDTH - MOWER_WIDTH) / 2;
    public static float POSITION_Y = 0;
    public static boolean debug = true;

    private GameConfig() {
    }
}
