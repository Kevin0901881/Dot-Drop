package com.kevinli.dotdropgame.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kevinli.dotdropgame.DotDrop;
import com.kevinli.dotdropgame.sprites.GameDot;

import java.util.ArrayList;
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

public class Game extends State {
    private static final int Y_SPACING_MIN = 20;
    private static final int Y_SPACING_MAX = 35;

    private AssetManager assets;
    private TextureAtlas atlas;

    private DotDrop dd;
    private Preferences pref;
    private ArrayList<GameDot> gameDots;
    private ShapeRenderer sr;

    private int n;
    private int highestN;
    private Random rand;

    // X coordinate of the next dot to be generated
    private int x;

    // Distance passed for next dot to be generated
    private int distanceY;
    // Number of dots that have been generated
    private int passby = 0;

    private Texture line;
    private Texture minidot;

    private Rectangle lineBounds;

    private int score = 0;
    private String scoreStr;
    private String message = "Tap anywhere on the screen when the line";
    private String message2 = "overlaps a dot of the same color";

    private BitmapFont font;
    private BitmapFont fontMessage;
    private GlyphLayout gl;
    private GlyphLayout glMessage;
    private GlyphLayout glMessage2;
    private float fontWidth;
    private float fontMessageWidth;
    private float fontMessage2Width;
    private float fontHeight;
    private float fontMessageHeight;
    private float fontMessage2Height;

    private float a = 1;
    private float aMessage = 0;

    private Sound hit;
    private Sound lose;

    private boolean runTutorial;
    private boolean paused;
    // Outputs fps to console every second
//    private FPSLogger fpsl;

    public Game(GameStateManager gsm, DotDrop dd) {
        super(gsm);
        assets = new AssetManager();
        assets.load("dots.pack", TextureAtlas.class);
        assets.finishLoading();
        atlas = assets.get("dots.pack");

        this.dd = dd;
        pref = Gdx.app.getPreferences("com.kevin.dotdropgame.settings");
        gameDots = new ArrayList<GameDot>();
        rand = new Random();
        // Sets highestN to the highest unlocked dot
        setHighestN();
        // Sets n to a number between the highest and lowest dot, excluding unselected dots
        n = generateRandom();
        int random = generateRandom();
        sr = new ShapeRenderer();

        x = rand.nextInt(920) - 70;

        distanceY = rand.nextInt(Y_SPACING_MAX - Y_SPACING_MIN + 1) + Y_SPACING_MIN;
        passby++;

        if (n == 1) {
            line = new Texture("limeline.png");
            minidot = new Texture("minilime.png");
        } else if (n == 2) {
            line = new Texture("aquamarineline.png");
            minidot = new Texture("miniaquamarine.png");
        } else if (n == 3) {
            line = new Texture("fireline.png");
            minidot = new Texture("minifire.png");
        } else if (n == 4) {
            line = new Texture("royalline.png");
            minidot = new Texture("miniroyal.png");
        } else if (n == 5) {
            line = new Texture("flamingoline.png");
            minidot = new Texture("miniflamingo.png");
        } else if (n == 6) {
            line = new Texture("hotrodline.png");
            minidot = new Texture("minihotrod.png");
        } else if (n == 7) {
            line = new Texture("lilacline.png");
            minidot = new Texture("minililac.png");
        } else if (n == 8) {
            line = new Texture("lemonline.png");
            minidot = new Texture("minilemon.png");
        } else if (n == 9) {
            line = new Texture("snowline.png");
            minidot = new Texture("minisnow.png");
        } else if (n == 10) {
            line = new Texture("emeraldline.png");
            minidot = new Texture("miniemerald.png");
        } else if (n == 11) {
            line = new Texture("chestnutline.png");
            minidot = new Texture("minichestnut.png");
        } else if (n == 12) {
            line = new Texture("sharkline.png");
            minidot = new Texture("minishark.png");
        } else if (n == 13) {
            line = new Texture("midnightline.png");
            minidot = new Texture("minimidnight.png");
        } else if (n == 14) {
            line = new Texture("danubeline.png");
            minidot = new Texture("minidanube.png");
        } else if (n == 15) {
            line = new Texture("antimatterline.png");
            minidot = new Texture("miniantimatter.png");
        }
        lineBounds = new Rectangle(0, 200, DotDrop.WIDTH, 4);

        scoreStr = Integer.toString(score);
        font = new BitmapFont(Gdx.files.internal("muli.fnt"));
        fontMessage = new BitmapFont(Gdx.files.internal("mulisemi.fnt"));
        font.setColor(1, 1, 1, 0.2f);
        fontMessage.setColor(1, 1, 1, aMessage);
        gl = new GlyphLayout();
        glMessage = new GlyphLayout();
        glMessage2 = new GlyphLayout();
        gl.setText(font, scoreStr);
        glMessage.setText(fontMessage, message);
        glMessage2.setText(fontMessage, message2);
        fontWidth = gl.width;
        fontHeight = gl.height;
        fontMessageWidth = glMessage.width;
        fontMessageHeight = glMessage.height;
        fontMessage2Width = glMessage2.width;
        fontMessage2Height = glMessage2.height;

        hit = Gdx.audio.newSound(Gdx.files.internal("dotsfx.mp3"));
        lose = Gdx.audio.newSound(Gdx.files.internal("losesfx.mp3"));

        // Resets the current score to 0
        if (!pref.contains("currentHighscore")) {
            pref.putInteger("currentHighscore", 0);
            pref.flush();
        }

        // Sets the volume to on
        if (!pref.contains("volume")) {
            pref.putInteger("volume", 1);
            pref.flush();
        }

        // Initiates tutorial on first startup or when help button is pressed
        if (!pref.contains("tutorial")) {
            pref.putInteger("tutorial", 1);
            pref.flush();
            gameDots.add(new GameDot(x, DotDrop.HEIGHT, n, 0));
            runTutorial = true;
        } else if (pref.getInteger("tutorial") == 0) {
            pref.putInteger("tutorial", 1);
            pref.flush();
            gameDots.add(new GameDot(x, DotDrop.HEIGHT, n, 0));
            runTutorial = true;
        } else {
            gameDots.add(new GameDot(x, DotDrop.HEIGHT, 0, random));
        }

//        fpsl = new FPSLogger();

        cam.setToOrtho(false, DotDrop.WIDTH, DotDrop.HEIGHT);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            for (GameDot dots : gameDots) {
                if (dots.getn() == n && dots.getBounds().overlaps(lineBounds)) {
                    if (pref.getInteger("volume") == 1) {
                        hit.play();
                    }
                    dots.setCleared(true);
                    score++;
                    scoreStr = Integer.toString(score);
                    gl.setText(font, scoreStr);
                    fontWidth = gl.width;
                    fontHeight = gl.height;
                } else if (dots.getBounds().overlaps(lineBounds)) {
                    pref.putInteger("currentHighscore", score);
                    pref.flush();
                    setPrefs();
                    if (pref.getInteger("volume") == 1) {
                        lose.play();
                    }
                    gsm.set(new HighScore(gsm, dd));
                }
                if (runTutorial && dots.getBounds().overlaps(lineBounds) && dots.getn() == n) {
                    runTutorial = false;
                    paused = false;
                    resume();
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        if (a > 0) {
            fade(true);
        } else {
            for (GameDot dots : gameDots) {
                if ((runTutorial && dots.getBounds().overlaps(lineBounds) && dots.getn() == n) || paused) {
                    paused = true;
                    pause();
                } else {
                    dots.update(dt);
                }
            }

            if (gameDots.get(0).getPosition().y < -300 && gameDots.get(0).getn() == n &&
                    !gameDots.get(0).getCleared()) {                                                // Change this to affect now low a dot must be below the screen to reset the game (-300)
                pref.putInteger("currentHighscore", score);
                pref.flush();
                setPrefs();
                if (pref.getInteger("volume") == 1) {
                    lose.play();
                }
                gsm.set(new HighScore(gsm, dd));
            }

            if (DotDrop.HEIGHT - gameDots.get(gameDots.size() - 1).getPosition().y >= distanceY) {
                distanceY = rand.nextInt(Y_SPACING_MAX - Y_SPACING_MIN + 1) + Y_SPACING_MIN;
                x = rand.nextInt(920) - 70;
                if (passby == 5) {                                                                  // Change this to affect number of dots that pass by before same color appears
                    gameDots.add(new GameDot(x, DotDrop.HEIGHT, n, 0));
                } else {
                    int random = generateRandom();
                    gameDots.add(new GameDot(x, DotDrop.HEIGHT, 0, random));
                }
                if (n == gameDots.get(gameDots.size() - 1).getn()) {
                    passby = 0;
                } else {
                    passby++;
                }
            }

            if (gameDots.get(0).getPosition().y < -300) {
                gameDots.remove(0);
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
//        fpsl.log();
        Gdx.gl.glClearColor(21/255f, 21/255f, 21/255f, 1);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        font.draw(sb, scoreStr, DotDrop.WIDTH / 2 - fontWidth / 2,
                DotDrop.HEIGHT / 2 + fontHeight / 2);
        for (GameDot dots : gameDots) {
            sb.draw(atlas.findRegion(getDotNames()[dots.getn() - 1]), dots.getPosition().x, dots.getPosition().y);
        }
        sb.draw(line, 0, 200);
        sb.draw(minidot, 25, 1830, 65, 65);                                                         // Change y-coordinate to match viewport height (1835)
        fontMessage.draw(sb, message, DotDrop.WIDTH / 2 - fontMessageWidth / 2,
                140);
        fontMessage.draw(sb, message2, DotDrop.WIDTH / 2 - fontMessage2Width / 2,
                90);
        sb.end();
//        sr.begin(ShapeRenderer.ShapeType.Filled);
//        sr.setColor(Color.YELLOW);
//        for (GameDot dots : gameDots) {
//            sr.rect(dots.getPosition().x + dots.getTexture().getHeight(), dots.getPosition().y, dots.getBounds().width, dots.getBounds().height);
//        }
//        sr.rect(lineBounds.x, lineBounds.y, line.getWidth(), line.getHeight());
//        sr.end();
        if (a > 0) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(0.03f, 0.03f, 0.03f, a);
            sr.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            sr.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    @Override
    public void dispose() {
        hit.dispose();
        font.dispose();
        fontMessage.dispose();
        line.dispose();
        minidot.dispose();
        lose.dispose();
        sr.dispose();
    }

    private void setHighestN() {
        if (pref.getInteger("highscore") >= 1000) {
            highestN = 15;
        } else if (pref.getInteger("highscore") >= 500) {
            highestN = 14;
        } else if (pref.getInteger("highscore") >= 250) {
            highestN = 13;
        } else if (pref.getInteger("highscore") >= 100) {
            highestN = 12;
        } else if (pref.getInteger("highscore") >= 75) {
            highestN = 11;
        } else if (pref.getInteger("highscore") >= 50) {
            highestN = 10;
        } else if (pref.getInteger("highscore") >= 25) {
            highestN = 9;
        } else if (pref.getInteger("highscore") >= 15) {
            highestN = 8;
        } else if (pref.getInteger("highscore") >= 10) {
            highestN = 7;
        } else if (pref.getInteger("highscore") >= 5) {
            highestN = 6;
        } else {
            highestN = 5;
        }
    }

    private void setPrefs() {
        if (pref.getInteger("currentHighscore") >= 1000 && pref.getInteger("antimatter") == 1) {
            pref.putInteger("antimatter", 2);
        }
        if (pref.getInteger("currentHighscore") >= 500 && pref.getInteger("danube") == 1) {
            pref.putInteger("danube", 2);
        }
        if (pref.getInteger("currentHighscore") >= 250 && pref.getInteger("midnight") == 1) {
            pref.putInteger("midnight", 2);
        }
        if (pref.getInteger("currentHighscore") >= 100 && pref.getInteger("shark") == 1) {
            pref.putInteger("shark", 2);
        }
        if (pref.getInteger("currentHighscore") >= 75 && pref.getInteger("chestnut") == 1) {
            pref.putInteger("chestnut", 2);
        }
        if (pref.getInteger("currentHighscore") >= 50 && pref.getInteger("emerald") == 1) {
            pref.putInteger("emerald", 2);
        }
        if (pref.getInteger("currentHighscore") >= 25 && pref.getInteger("snow") == 1) {
            pref.putInteger("snow", 2);
        }
        if (pref.getInteger("currentHighscore") >= 15 && pref.getInteger("lemon") == 1) {
            pref.putInteger("lemon", 2);
        }
        if (pref.getInteger("currentHighscore") >= 10 && pref.getInteger("lilac") == 1) {
            pref.putInteger("lilac", 2);
        }
        if (pref.getInteger("currentHighscore") >= 5 && pref.getInteger("hotrod") == 1) {
            pref.putInteger("hotrod", 2);
        }

        pref.flush();
    }

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

    private void pause() {
        aMessage = 1;
        fontMessage.setColor(1, 1, 1, aMessage);
    }

    private void resume() {
        aMessage = 0;
        fontMessage.setColor(1, 1, 1, aMessage);
    }
}
