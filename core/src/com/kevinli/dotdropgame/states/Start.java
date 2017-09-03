package com.kevinli.dotdropgame.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kevinli.dotdropgame.DotDrop;
import com.kevinli.dotdropgame.sprites.StartDot;


/**
 * Created by Kevin on 7/11/2017.
 */

public class Start extends State implements InputProcessor{

    private DotDrop dd;
    private ShapeRenderer sr;
    // Stores the touch coordinates so that it can be converted then used for the local screen resolution
    private Vector3 touch;
    private StartDot dot;
    private Unlocks unlocks;

    private Sprite wordmark;
    private Sprite star;
    private Sprite downarrow;

    // Start dot
    private Vector2 firstTouch;
    private Vector2 original;
    private Vector2 delta;
    private Vector2[] velocity;
    // Distance between touch coordinate and coordinate of ball,
    // stored so that ball position can be set when dragged
    private Vector2 touchDownDelta;

    // True if touched down within bounds of the start dot
    private boolean tDownStartDot;
    // True if touched down within the bounds of the unlocks dot, when unlocks is in a closed state
    private boolean tDownUnlocksOpen;
    // True if touched down within the up arrow in unlocks, when unlocks in in an opened state
    private boolean tDownUnlocksClose;
    // True if touched down within the dots in unlocks
    private boolean[] tDownDots;
    // If openUnlock = 0 or 2, then unlocks is closed. If openUnlock = 1, then unlocks is opened
    private int openUnlock;
    // Stores the dot being selected or unselected
    private int dotSelect;

    // Stores the alpha of the black rectangle (fade transition)
    private float a;
    // True if the fade in transition has already occurred
    private boolean fadeIn;

    public Start(GameStateManager gsm, DotDrop dd) {
        super(gsm);
        // Enables the use of the ActionResolver and PlayServices interfaces
        this.dd = dd;
        sr = new ShapeRenderer();
        touch = new Vector3();
        // Radius of start dot is 325
        dot = new StartDot(DotDrop.WIDTH / 2 - 325, DotDrop.HEIGHT / 2 - 325);
        unlocks = new Unlocks((float) 100.5, 1850);

        wordmark = new Sprite(new Texture("wordmark.png"));
        star = new Sprite(new Texture("text2.png"));
        downarrow = new Sprite(new Texture("downarrow.png"));
        wordmark.setPosition(15, 15);
        star.setPosition(265, 1675);
        downarrow.setPosition(DotDrop.WIDTH / 2 - downarrow.getWidth() / 2, DotDrop.HEIGHT / 4);
        wordmark.setAlpha(0.2f);
        star.setAlpha(0.4f);
        downarrow.setAlpha(0.4f);

        firstTouch = new Vector2(0, 0);
        original = new Vector2(0, 0);
        delta = new Vector2(0, 0);
        velocity = new Vector2[10];
        for (int i = 0; i < 10; i++) {
            velocity[i] = new Vector2(0, 0);
        }
        touchDownDelta = new Vector2(0, 0);

        tDownDots = new boolean[15];

        // Alpha is 1 because the fade transition starts as all black
        a = 1;

        cam.setToOrtho(false, DotDrop.WIDTH, DotDrop.HEIGHT);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void handleInput() {}

    @Override
    public void update(float dt) {
        handleInput();
        dot.update(dt);

        // Fade in transition plays if alpha is greater than 0
        if (!fadeIn) {
            fade(true);
            if (a <= 0) {
                fadeIn = true;
            }
        }

        // Open and close animations for unlocks
        if (openUnlock == 1) {
            unlocks.open(dt);
            unlocks.fadeIn();
            // Fade in and fade out animation when selecting or deselecting dots in unlocks screen
            unlocks.fadeSelect(unlocks.getDotNames()[dotSelect], dotSelect);
        } else if (openUnlock == 2) {
            unlocks.close(dt);
            unlocks.fadeOut();
        }

        // If start dot goes out of bounds, play fade out animation then start Game
        if ((dot.getPosition().x >= DotDrop.WIDTH) || (dot.getPosition().x + 650 <= 0)
                || (dot.getPosition().y >= DotDrop.HEIGHT) || (dot.getPosition().y + 650 <= 0)) {
            fade(false);
            if (a >= 1) {
                gsm.set(new Game(gsm, dd));
            }
        }
    }

    @Override
        public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(21/255f, 21/255f, 21/255f, 1);
        sb.setProjectionMatrix(cam.combined);
        sb.enableBlending();
        sb.begin();
        wordmark.draw(sb);
        star.draw(sb);
        downarrow.draw(sb);
        sb.draw(dot.getTexture(), dot.getPosition().x, dot.getPosition().y);
        unlocks.getBg2().draw(sb);
        unlocks.getSprite()[1].setPosition(unlocks.getPosition().get(1).x, unlocks.getPosition().get(1).y);
        unlocks.getSprite()[1].draw(sb);
        for (int i = 0; i < 15; i++) {
            if (i != 1) {
                unlocks.getSprite()[i].setPosition(unlocks.getPosition().get(i).x, unlocks.getPosition().get(i).y);
                unlocks.getSprite()[i].draw(sb);
            }
        }
        unlocks.drawText(sb);
        unlocks.getBack().draw(sb);

        sb.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.03f, 0.03f, 0.03f, a);
        sr.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        wordmark.getTexture().dispose();
        star.getTexture().dispose();
        downarrow.getTexture().dispose();
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
        // If unlocks is open, then pressing the back key will close it
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
        // Sets touch coordinates relative to local screen resolution
        touch.set(screenX, screenY, 0);
        if (Gdx.graphics.getHeight() / 16 * 9 >= Gdx.graphics.getWidth()) {
            cam.unproject(touch, 0, Gdx.graphics.getHeight() / 2 - (Gdx.graphics.getWidth() * 16 / 9 / 2),
                    Gdx.graphics.getWidth(), Gdx.graphics.getWidth() * 16 / 9);
        } else {
            cam.unproject(touch, Gdx.graphics.getWidth() / 2 - (Gdx.graphics.getHeight() * 9 / 16 / 2), 0,
                    Gdx.graphics.getHeight() * 9 / 16, Gdx.graphics.getHeight());
        }

        // If unlocks is closed and the start dot is touched, then record initial positions of touch
        if (dot.getBounds().contains(touch.x, touch.y) && openUnlock != 1) {
            tDownStartDot = true;
            firstTouch.set(touch.x, touch.y);
            original.set(touch.x, touch.y);
            touchDownDelta.set(touch.x - dot.getPosition().x,
                    touch.y - dot.getPosition().y);
        }

        // Open or close unlocks
        if (unlocks.getBounds().get(1).contains(touch.x, touch.y) && openUnlock != 1) {
            tDownUnlocksOpen = true;
        } else if (unlocks.getBackBounds().contains(touch.x, touch.y)) {
            tDownUnlocksClose = true;
        }

        // Select or deselect dots when unlocks is open
        if (openUnlock == 1) {
            for (int i = 0; i < 15; i++) {
                if (unlocks.getBounds().get(i).contains(touch.x, touch.y)) {
                    tDownDots[i] = true;
                    dotSelect = i;
                }
            }
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Sets touch coordinates relative to local screen resolution
        touch.set(screenX, screenY, 0);
        if (Gdx.graphics.getHeight() / 16 * 9 >= Gdx.graphics.getWidth()) {
            cam.unproject(touch, 0, Gdx.graphics.getHeight() / 2 - (Gdx.graphics.getWidth() * 16 / 9 / 2),
                    Gdx.graphics.getWidth(), Gdx.graphics.getWidth() * 16 / 9);
        } else {
            cam.unproject(touch, Gdx.graphics.getWidth() / 2 - (Gdx.graphics.getHeight() * 9 / 16 / 2), 0,
                    Gdx.graphics.getHeight() * 9 / 16, Gdx.graphics.getHeight());
        }

        // When start dot is released, send velocity values to start dot object
        if (tDownStartDot) {
            Vector2 total = new Vector2();
            tDownStartDot = false;
            dot.setTDown(tDownStartDot);
            for (int i = 0; i < 10; i++) {
                total.add(velocity[i]);
            }
            total.scl((float) 1/10);
            dot.dotFling(total.x, total.y);
        }
        tDownStartDot = false;

        if (tDownUnlocksOpen && unlocks.getBounds().get(1).contains(touch.x, touch.y)) {
            openUnlock = 1;
            tDownUnlocksOpen = false;
        } else {
            tDownUnlocksOpen = false;
        }

        if (tDownUnlocksClose && unlocks.getBackBounds().contains(touch.x, touch.y)) {
            openUnlock = 2;
            tDownUnlocksClose = false;
        } else {
            tDownUnlocksClose = false;
        }

        if (openUnlock == 1 && tDownDots[dotSelect] && unlocks.getBounds().get(dotSelect).contains(touch.x, touch.y)) {
            unlocks.setSelected(unlocks.getDotNames()[dotSelect]);
            tDownDots[dotSelect] = false;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, screenY, 0);
        if (Gdx.graphics.getHeight() / 16 * 9 >= Gdx.graphics.getWidth()) {
            cam.unproject(touch, 0, Gdx.graphics.getHeight() / 2 - (Gdx.graphics.getWidth() * 16 / 9 / 2),
                    Gdx.graphics.getWidth(), Gdx.graphics.getWidth() * 16 / 9);
        } else {
            cam.unproject(touch, Gdx.graphics.getWidth() / 2 - (Gdx.graphics.getHeight() * 9 / 16 / 2), 0,
                    Gdx.graphics.getHeight() * 9 / 16, Gdx.graphics.getHeight());
        }
        float dt = Gdx.graphics.getDeltaTime();

        // Sets the last 10 velocity values and averages them to achieve initial velocity of start dot
        if (tDownStartDot) {
            dot.setTDown(tDownStartDot);
            dot.setTDragged(true);
            Vector2 currentTouch = new Vector2(touch.x, touch.y);
            delta.set(currentTouch.cpy().sub(firstTouch));
            firstTouch.set(currentTouch);
            if (velocity[0].x == 0 && velocity[0].y == 0) {
                velocity[0].set(delta.x / dt, delta.y / dt);
            } else if (velocity[1].x == 0 && velocity[1].y == 0) {
                velocity[1].set(delta.x / dt, delta.y / dt);
            } else if (velocity[2].x == 0 && velocity[2].y == 0) {
                velocity[2].set(delta.x / dt, delta.y / dt);
            } else if (velocity[3].x == 0 && velocity[3].y == 0) {
                velocity[3].set(delta.x / dt, delta.y / dt);
            } else if (velocity[4].x == 0 && velocity[4].y == 0) {
                velocity[4].set(delta.x / dt, delta.y / dt);
            } else if (velocity[5].x == 0 && velocity[5].y == 0) {
                velocity[5].set(delta.x / dt, delta.y / dt);
            } else if (velocity[6].x == 0 && velocity[6].y == 0) {
                velocity[6].set(delta.x / dt, delta.y / dt);
            } else if (velocity[7].x == 0 && velocity[7].y == 0) {
                velocity[7].set(delta.x / dt, delta.y / dt);
            } else if (velocity[8].x == 0 && velocity[8].y == 0) {
                velocity[8].set(delta.x / dt, delta.y / dt);
            } else if (velocity[9].x == 0 && velocity[9].y == 0) {
                velocity[9].set(delta.x / dt, delta.y / dt);
            } else {
                velocity[0] = velocity[1];
                velocity[1] = velocity[2];
                velocity[2] = velocity[3];
                velocity[3] = velocity[4];
                velocity[4] = velocity[5];
                velocity[5] = velocity[6];
                velocity[6] = velocity[7];
                velocity[7] = velocity[8];
                velocity[8] = velocity[9];
                velocity[9].set(delta.x / dt, delta.y / dt);
            }
            dot.setPosition(touch.x - touchDownDelta.x,
                    touch.y - touchDownDelta.y);
        }

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
