package com.kevinli.dotdropgame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Kevin on 7/11/2017.
 */

public class StartDot {
    private static final int GRAVITY = -110;
    private Vector2 position;
    private Vector2 velocity;
    private Texture dot;
    private Circle bounds;
    // Once ball has been dragged, tDragged becomes true and doesn't change back,
    // meaning gravity is applied when ball is not touched.
    private boolean tDragged;
    // If touched down, gravity won't be applied
    private boolean tDownStartDot;

    public StartDot(float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        dot = new Texture("startdot.png");
        bounds = new Circle(x + 325, y + 325, 230);
    }

    public void update(float dt) {
        if (!tDownStartDot && tDragged) {
            velocity.add(0, GRAVITY);
        }
        velocity.scl(dt);
        position.add(velocity.x, velocity.y);
        if (dt != 0) {
            velocity.scl(1 / dt);
        } else {
            velocity.scl(1 / (float) 0.017);
        }
        bounds.setPosition(position.x + 325, position.y + 325);
    }

    public void dotFling(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return dot;
    }

    public Circle getBounds() {
        return bounds;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public void setVelocity(float x, float y) {
        this.velocity.set(x, y);
    }

    public void setTDragged(boolean tDragged) {
        this.tDragged = tDragged;
    }

    public void setTDown(boolean tDownStartDot) {
        this.tDownStartDot = tDownStartDot;
    }

    public void dispose() {
        dot.dispose();
    }
}