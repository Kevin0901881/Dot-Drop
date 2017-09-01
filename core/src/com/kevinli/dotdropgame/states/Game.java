package com.kevinli.dotdropgame.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

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

    private Preferences pref;

    private int x;

    private ArrayList<com.kevinli.dotdropgame.sprites.GameDot> gameDots;

    private int distanceY;
    private int passby = 0;

    private Texture line;
    private Texture minidot;

    private Rectangle lineBounds;

    private Random rand;
    private int n;
    private int highestN;

    private int score = 0;
    private String scoreStr;

    private BitmapFont font;
    private GlyphLayout gl;
    private float fontWidth;
    private float fontHeight;

    private ShapeRenderer sr;

    private float a;

    private Sound hit;
    private Sound lose;

    private FPSLogger fpsl;

    private com.kevinli.dotdropgame.DotDrop dd;

    public Game(GameStateManager gsm, com.kevinli.dotdropgame.DotDrop dd) {
        super(gsm);
        pref = Gdx.app.getPreferences("com.kevin.dotdropgame.settings");
        this.dd = dd;
        rand = new Random();
        sr = new ShapeRenderer();
        x = rand.nextInt(920) - 70;
        setHighestN();
        gameDots = new ArrayList<com.kevinli.dotdropgame.sprites.GameDot>();
        int random = generateRandom();
        gameDots.add(new com.kevinli.dotdropgame.sprites.GameDot(x, com.kevinli.dotdropgame.DotDrop.HEIGHT, 0, random));
        passby++;
        lineBounds = new Rectangle(0, 200, com.kevinli.dotdropgame.DotDrop.WIDTH, 4);
        a = 1;
        n = generateRandom();
        scoreStr = Integer.toString(score);
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
        cam.setToOrtho(false, com.kevinli.dotdropgame.DotDrop.WIDTH, com.kevinli.dotdropgame.DotDrop.HEIGHT);
        font = new BitmapFont(Gdx.files.internal("muli.fnt"));
        font.setColor(1, 1, 1, 0.2f);
        gl = new GlyphLayout();
        gl.setText(font, scoreStr);
        fontWidth = gl.width;
        fontHeight = gl.height;
        hit = Gdx.audio.newSound(Gdx.files.internal("dotsfx.mp3"));
        lose = Gdx.audio.newSound(Gdx.files.internal("losesfx.mp3"));
        if (!pref.contains("currentHighscore")) {
            pref.putInteger("currentHighscore", 0);
            pref.flush();
        }
        if (!pref.contains("volume")) {
            pref.putInteger("volume", 1);
            pref.flush();
        }
        distanceY = rand.nextInt(Y_SPACING_MAX - Y_SPACING_MIN + 1) + Y_SPACING_MIN;
        fpsl = new FPSLogger();
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            for (com.kevinli.dotdropgame.sprites.GameDot dots : gameDots) {
                if (dots.getBounds().overlaps(lineBounds) && dots.getn() == n && !dots.getTouched()) {
                    if (pref.getInteger("volume") == 1) {
                        hit.play();
                    }
                    dots.fadeOut();
                    dots.setCleared(true);
                    score++;
                    scoreStr = Integer.toString(score);
                    gl.setText(font, scoreStr);
                    fontWidth = gl.width;
                    fontHeight = gl.height;
                } else if (dots.getBounds().overlaps(lineBounds) && !dots.getTouched()) {
                    pref.putInteger("currentHighscore", score);
                    pref.flush();
                    setPrefs();
                    if (pref.getInteger("volume") == 1) {
                        lose.play();
                    }
                    gsm.set(new HighScore(gsm, dd));
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
            for (com.kevinli.dotdropgame.sprites.GameDot dots : gameDots) {
                dots.update(dt);
            }

            if (gameDots.get(0).getPosition().y < -350 && gameDots.get(0).getn() == n &&
                    !gameDots.get(0).getCleared()) {                                                // Change this to affect now low a dot must be below the screen to reset the game (-300)
                pref.putInteger("currentHighscore", score);
                pref.flush();
                setPrefs();
                if (pref.getInteger("volume") == 1) {
                    lose.play();
                }
                gsm.set(new HighScore(gsm, dd));
            }

            if (com.kevinli.dotdropgame.DotDrop.HEIGHT - gameDots.get(gameDots.size() - 1).getPosition().y >= distanceY) {
                distanceY = rand.nextInt(Y_SPACING_MAX - Y_SPACING_MIN + 1) + Y_SPACING_MIN;
                x = rand.nextInt(920) - 70;
                if (passby == 5) {                                                                  // Change this to affect number of dots that pass by before same color appears
                    gameDots.add(new com.kevinli.dotdropgame.sprites.GameDot(x, com.kevinli.dotdropgame.DotDrop.HEIGHT, n, 0));
                } else {
                    int random = generateRandom();
                    gameDots.add(new com.kevinli.dotdropgame.sprites.GameDot(x, com.kevinli.dotdropgame.DotDrop.HEIGHT, 0, random));
                }
                if (n == gameDots.get(gameDots.size() - 1).getn()) {
                    passby = 0;
                } else {
                    passby++;
                }
            }

            if (gameDots.get(0).getPosition().y < -350) {
                gameDots.remove(0);
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        fpsl.log();
        Gdx.gl.glClearColor(21/255f, 21/255f, 21/255f, 1);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        font.draw(sb, scoreStr, com.kevinli.dotdropgame.DotDrop.WIDTH / 2 - fontWidth / 2,
                com.kevinli.dotdropgame.DotDrop.HEIGHT / 2 + fontHeight / 2);
        for (com.kevinli.dotdropgame.sprites.GameDot dots : gameDots) {
            sb.draw(dots.getTexture(), dots.getPosition().x, dots.getPosition().y);
        }
        sb.draw(line, 0, 200);
        sb.draw(minidot, 25, 1830, 65, 65);                                                         // Change y-coordinate to match viewport height (1835)
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
        for (com.kevinli.dotdropgame.sprites.GameDot dots : gameDots) {
            dots.dispose();
        }
        hit.dispose();
        font.dispose();
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
}
