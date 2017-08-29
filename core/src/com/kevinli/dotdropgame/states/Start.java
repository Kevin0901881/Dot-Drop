package com.kevinli.dotdropgame.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.kevinli.dotdropgame.DotDrop;

/**
 * Created by Kevin on 7/11/2017.
 */

public class Start extends State implements InputProcessor{
    private com.kevinli.dotdropgame.sprites.StartDot dot;
    private Unlocks unlocks;

    private Texture wordmark;
    private Texture text2;
    private Texture downarrow;

    private Sprite wordmarks;
    private Sprite text2s;
    private Sprite downarrows;

    // Start dot
    private Vector2 firstTouch;
    private Vector2 original;
    private Vector2 delta;
    private Vector2 velocity;
    private Vector2 velocity2;
    private Vector2 velocity3;
    // Distance between touch coordinate and coordinate of ball,
    // stored so that ball position can be set when dragged
    private Vector2 touchDownDelta;

    private boolean tDown;
    private boolean tDownU;
    private boolean tDownU2;
    private boolean[] tDownDots;
    private int openUnlock;
    private int dotSelect;

    private float y;
    private float a;
    private boolean fadeIn;

    ShapeRenderer sr;

    private Preferences pref;
    private DotDrop dd;

    public Start(GameStateManager gsm, DotDrop dd) {
        super(gsm);
        pref = Gdx.app.getPreferences("com.kevin.dotdropgame.settings");
        this.dd = dd;
        sr = new ShapeRenderer();
        dot = new com.kevinli.dotdropgame.sprites.StartDot(DotDrop.WIDTH / 2 - 325, DotDrop.HEIGHT / 2 - 325);                      // Change radius of image (not bounds) (400)
        y = 1850;
        a = 1;
        unlocks = new Unlocks((float) 100.5, y);
        firstTouch = new Vector2(0, 0);
        original = new Vector2(0, 0);
        delta = new Vector2(0, 0);
        velocity = new Vector2(0, 0);
        velocity2 = new Vector2(0, 0);
        velocity3 = new Vector2(0, 0);
        touchDownDelta = new Vector2(0, 0);
        tDownDots = new boolean[15];
        wordmark = new Texture("wordmark.png");
        text2 = new Texture("text2.png");
        downarrow = new Texture("downarrow.png");
        wordmarks = new Sprite(wordmark);
        text2s = new Sprite(text2);
        downarrows = new Sprite(downarrow);
        wordmarks.setPosition(15, 15);
        text2s.setPosition(265, 1675);
        downarrows.setPosition(DotDrop.WIDTH / 2 - downarrow.getWidth() / 2, DotDrop.HEIGHT / 4);
        wordmarks.setAlpha(0.1f);
        text2s.setAlpha(0.4f);
        downarrows.setAlpha(0.4f);
        cam.setToOrtho(false, DotDrop.WIDTH, DotDrop.HEIGHT);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        dot.update(dt);
        if (!fadeIn) {
            fade(true);
            if (a <= 0) {
                fadeIn = true;
            }
        }
        if (openUnlock == 1) {
            unlocks.open(dt);
            unlocks.fadeIn();
        } else if (openUnlock == 2) {
            unlocks.close(dt);
            unlocks.fadeOut();
        }

        if ((openUnlock == 1 && tDownDots[dotSelect]) || (openUnlock == 1 && !tDownDots[dotSelect])) {
            unlocks.fadeSelect(unlocks.getDotNames()[dotSelect], dotSelect);
        }

        if ((dot.getPosition().x >= DotDrop.WIDTH) || (dot.getPosition().x + 650 <= 0)              // Change diameter of image (not bounds) (400)
                || (dot.getPosition().y >= DotDrop.HEIGHT) || (dot.getPosition().y + 650 <= 0)) {   // Change diameter of image (not bounds) (400)
            fade(false);
            if (a >= 1) {
                gsm.set(new Game(gsm, dd));
            }
        }

//        if (unlocks.getPosition().get(1).y >= 125) {
//            y -= 50;
//            unlocks.setPosition(y);
//        }
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(21/255f, 21/255f, 21/255f, 1);
        sb.setProjectionMatrix(cam.combined);
        sb.enableBlending();
        sb.begin();
        wordmarks.draw(sb);
        text2s.draw(sb);
        downarrows.draw(sb);
        sb.draw(dot.getTexture(), dot.getPosition().x, dot.getPosition().y);
        unlocks.getBg2S().draw(sb);
//        sb.draw(unlocks.getSprite()[1], unlocks.getPosition().get(1).x, unlocks.getPosition().get(1).y);
        unlocks.getSprite()[1].setPosition(unlocks.getPosition().get(1).x, unlocks.getPosition().get(1).y);
        unlocks.getSprite()[1].draw(sb);
        for (int i = 0; i < 15; i++) {
            if (i != 1) {
                unlocks.getSprite()[i].setPosition(unlocks.getPosition().get(i).x, unlocks.getPosition().get(i).y);
                unlocks.getSprite()[i].draw(sb);
                //sb.draw(unlocks.getSprite()[i], unlocks.getPosition().get(i).x, unlocks.getPosition().get(i).y);
            }
        }
        unlocks.drawText(sb);
        unlocks.getBackS().draw(sb);

        sb.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.03f, 0.03f, 0.03f, a);
        sr.rect(0, 0, DotDrop.WIDTH, DotDrop.HEIGHT);
        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
//        sr.begin(ShapeRenderer.ShapeType.Filled);
//        for (int i = 0; i < 15; i++) {
//            sr.circle(unlocks.getBounds().get(i).x, unlocks.getBounds().get(i).y, unlocks.getBounds().get(i).radius);
//        }
//        sr.setColor(com.badlogic.gdx.graphics.Color.RED);
//        sr.circle(dot.getBounds().x, dot.getBounds().y, dot.getBounds().radius);
//        sr.end();
    }

    @Override
    public void dispose() {
        wordmark.dispose();
        text2.dispose();
        downarrow.dispose();
        unlocks.dispose();
        sr.dispose();
        dot.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.BACK && openUnlock == 1) {
            openUnlock = 2;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (dot.getBounds().contains(screenX, DotDrop.HEIGHT - screenY) && openUnlock != 1) {
            tDown = true;
            firstTouch.set(screenX, screenY);
            original.set(screenX, screenY);
            touchDownDelta.set(screenX - dot.getPosition().x,
                    DotDrop.HEIGHT - screenY - dot.getPosition().y);
        }

        if (unlocks.getBounds().get(1).contains(screenX, DotDrop.HEIGHT - screenY) && openUnlock != 1) {
            tDownU = true;
        } else if (unlocks.getBackBounds().contains(screenX, DotDrop.HEIGHT - screenY)) {
            tDownU2 = true;
        }

        if (openUnlock == 1) {
            for (int i = 0; i < 15; i++) {
                if (unlocks.getBounds().get(i).contains(screenX, DotDrop.HEIGHT - screenY)) {
                    tDownDots[i] = true;
                    dotSelect = i;
                }
            }
        }
//
//        if (unlocks.getBounds().get(1).contains(screenX, DotDrop.HEIGHT - screenY)) {
//            System.out.println(screenX + "    " + screenY);
//            tDownU = true;
//            firstTouchU = screenY;
//            originalU = screenY;
//            touchDownDeltaU = DotDrop.HEIGHT - screenY - dot.getPosition().y;
//        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (tDown) {
            tDown = false;
            dot.setTDown(tDown);
            dot.dotFling((velocity2.x + velocity3.x) / 2, -(velocity2.y + velocity3.y) / 2);
        }
        tDown = false;

        if (tDownU && unlocks.getBounds().get(1).contains(screenX, DotDrop.HEIGHT - screenY)) {
//            if (openUnlock == 0 || openUnlock == 2) {
//                openUnlock = 1;
//            } else {
//                openUnlock = 2;
//            }
            openUnlock = 1;
            tDownU = false;
        } else {
            tDownU = false;
        }

        if (tDownU2 && unlocks.getBackBounds().contains(screenX, DotDrop.HEIGHT - screenY)) {
            openUnlock = 2;
            tDownU2 = false;
        } else {
            tDownU2 = false;
        }

        if (openUnlock == 1 && tDownDots[dotSelect] && unlocks.getBounds().get(dotSelect).contains(screenX, DotDrop.HEIGHT - screenY)) {
            unlocks.setSelected(unlocks.getDotNames()[dotSelect]);
            tDownDots[dotSelect] = false;
        }
        return true;
    }

    // TODO: Fling feels a bit weird, maybe decrease update rate
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float dt = Gdx.graphics.getDeltaTime();
        if (tDown) {
            dot.setTDown(tDown);
            dot.setTDragged(true);
            Vector2 currentTouch = new Vector2(screenX, screenY);
            delta.set(currentTouch.cpy().sub(firstTouch));
            firstTouch.set(currentTouch);
            if (velocity.x == 0 && velocity.y == 0) {
                velocity.set(delta.x / dt, delta.y / dt);
            } else if (velocity2.x == 0 && velocity2.y == 0) {
                velocity2.set(delta.x / dt, delta.y / dt);
            } else if (velocity3.x == 0 && velocity3.y == 0) {
                velocity3.set(delta.x / dt, delta.y / dt);
            } else {
                velocity = velocity2;
                velocity2 = velocity3;
                velocity3.set(delta.x / dt, delta.y / dt);
            }
            dot.setPosition(screenX - touchDownDelta.x,
                    DotDrop.HEIGHT - screenY - touchDownDelta.y);
        }

//        if (tDownU) {
//            float currentTouchU = screenY;
//            deltaU = currentTouchU - firstTouchU;
//            firstTouchU = currentTouchU;
//            velocityU = deltaU / dt;
//            unlocks.setPosition(DotDrop.HEIGHT - screenY);
//        }

        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private void fade(boolean out) {
        if (out) {
            a -= 0.1;
            if (a <= 0) {
                a = 0;
            }
        } else {
            a += 0.1;
            if (a >= 1) {
                a = 1;
            }
        }
    }
}
