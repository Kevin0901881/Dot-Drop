package com.kevinli.dotdropgame.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Kevin on 7/17/2017.
 *
 * Legend
 * 1 - Lime
 * 2 - Aquamarine
 * 3 - Fire
 * 4 - Royal
 * 5 - Flamingo
 * 6 - Hot Rod
 * 7 - Lilac
 * 8 - Lemon
 * 9 - Snow
 * 10 - Emerald
 * 11 - Chestnut
 * 12 - Shark
 * 13 - Midnight
 * 14 - Danube
 * 15 - Antimatter
 */

public class GameDot {
    private static final int GRAVITY = -25;                                                         // Change gravity value
    private Vector2 position;
    private Vector2 velocity;
    private Texture dot;
    private Rectangle bounds;
    private Random rand;
    private int n;
    private int highestN;
    private boolean cleared = false;
    private boolean touched = false;
    private Preferences pref;

    public GameDot(int x, int y, int n2, int highestN) {
        pref = Gdx.app.getPreferences("com.kevin.dotdropgame.settings");
        rand = new Random();
        this.highestN = highestN;
        if (n2 == 0) {
            n = generateRandom();
        } else {
            n = n2;
        }
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        dot = new Texture(getDotNames()[n - 1] + ".png");

//        if (n == 1) {
//            dot = new Texture("lime.png");
//        } else if (n == 2) {
//            dot = new Texture("aquamarine.png");
//        } else if (n == 3) {
//            dot = new Texture("fire.png");
//        } else if (n == 4) {
//            dot = new Texture("royal.png");
//        } else if (n == 5) {
//            dot = new Texture("flamingo.png");
//        } else if (n == 6) {
//            dot = new Texture("hotrod.png");
//        } else if (n == 7) {
//            dot = new Texture("lilac.png");
//        } else if (n == 8) {
//            dot = new Texture("lemon.png");
//        } else if (n == 9) {
//            dot = new Texture("snow.png");
//        } else if (n == 10) {
//            dot = new Texture("emerald.png");
//        } else if (n == 11) {
//            dot = new Texture("chestnut.png");
//        } else if (n == 12) {
//            dot = new Texture("shark.png");
//        } else if (n == 13) {
//            dot = new Texture("midnight.png");
//        } else if (n == 14) {
//            dot = new Texture("danube.png");
//        } else if (n == 15) {
//            dot = new Texture("antimatter.png");
//        }
        bounds = new Rectangle(x + 78, y + 72, 156, 156);                                           // Change radius of image (not bounds) (100)
                                                                                                    //      Change radius of bounds (50)
    }

    public void update(float dt) {
        velocity.add(0, GRAVITY);
        if (dt == 0) {
            dt = (float) 0.017;
        }
        velocity.scl(dt);
        position.add(0, velocity.y);
        velocity.scl(1 / dt);
        bounds.setPosition(position.x + 78, position.y + 72);                                       // Change radius of image (not bounds) (100)
    }

    public void fadeOut() {
        touched = true;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return dot;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getn() {
        return n;
    }

    public boolean getTouched() {
        return touched;
    }

    public boolean getCleared() {
        return cleared;
    }

    public void setCleared(boolean clear) {
        cleared = clear;
    }

    public void dispose() {
        dot.dispose();
    }

//    private void setHighestN() {
//        if (pref.getInteger("highscore") >= 1000) {
//            highestN = 15;
//        } else if (pref.getInteger("highscore") >= 500) {
//            highestN = 14;
//        } else if (pref.getInteger("highscore") >= 250) {
//            highestN = 13;
//        } else if (pref.getInteger("highscore") >= 100) {
//            highestN = 12;
//        } else if (pref.getInteger("highscore") >= 75) {
//            highestN = 11;
//        } else if (pref.getInteger("highscore") >= 50) {
//            highestN = 10;
//        } else if (pref.getInteger("highscore") >= 25) {
//            highestN = 9;
//        } else if (pref.getInteger("highscore") >= 15) {
//            highestN = 8;
//        } else if (pref.getInteger("highscore") >= 10) {
//            highestN = 7;
//        } else if (pref.getInteger("highscore") >= 5) {
//            highestN = 6;
//        } else {
//            highestN = 5;
//        }
//    }

    private int generateRandom() {
        int num = rand.nextInt(highestN) + 1;
        int diff = 0;
        int r = rand.nextInt(2) + 1;
        while (pref.getInteger(getDotNames()[num - 1]) == 2 && r == 1) {
            if (num == 1) {
                num += diff + 1;
                while (pref.getInteger(getDotNames()[num - 1]) == 2) {
                    num++;
                    if (pref.getInteger(getDotNames()[num - 1]) == 3) {
                        return num;
                    }
                }
            }
            num--;
            diff++;
        }
        while (pref.getInteger(getDotNames()[num - 1]) == 2 && r == 2) {
            if (num == highestN) {
                num -= diff;
                num -= 1;
                while (pref.getInteger(getDotNames()[num - 1]) == 2) {
                    num--;
                    if (pref.getInteger(getDotNames()[num - 1]) == 3) {
                        return num;
                    }
                }
            }
            num++;
            diff++;
        }
        return num;
    }

    private String[] getDotNames() {
        String[] dotNames = new String[15];
        dotNames[0] = "lime";
        dotNames[1] = "aquamarine";
        dotNames[2] = "fire";
        dotNames[3] = "royal";
        dotNames[4] = "flamingo";
        dotNames[5] = "hotrod";
        dotNames[6] = "lilac";
        dotNames[7] = "lemon";
        dotNames[8] = "snow";
        dotNames[9] = "emerald";
        dotNames[10] = "chestnut";
        dotNames[11] = "shark";
        dotNames[12] = "midnight";
        dotNames[13] = "danube";
        dotNames[14] = "antimatter";
        return dotNames;
    }
}
