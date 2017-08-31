package com.kevinli.dotdropgame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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
    private int n;
    private boolean cleared = false;
    private boolean touched = false;

    public GameDot(int x, int y, int n2, int nRand) {
        if (n2 == 0) {
            n = nRand;
        } else {
            n = n2;
        }
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        dot = new Texture(getDotNames()[n - 1] + ".png");
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
