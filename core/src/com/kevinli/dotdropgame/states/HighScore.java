package com.kevinli.dotdropgame.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.kevinli.dotdropgame.DotDrop;

import static com.badlogic.gdx.Gdx.gl;

/**
 * Created by Kevin on 8/18/2017.
 */

public class HighScore extends State implements InputProcessor {

    private Preferences pref;

    private Texture share;
    private Texture rate;
    private Texture leaderboards;
    private Texture volume;
    private Texture muted;
    private Texture threestars;
    private Texture start;
    private Texture replay;
    private Texture help;

    private Sprite threestarSprite;
    private Sprite startSprite;
    private Sprite replaySprite;
    private Sprite shareSprite;
    private Sprite rateSprite;
    private Sprite leaderboardsSprite;
    private Sprite volumeSprite;
    private Sprite helpSprite;

    private Rectangle shareBound;
    private Rectangle rateBound;
    private Rectangle leaderboardsBound;
    private Rectangle volumeBound;
    private Rectangle startBound;
    private Rectangle replayBound;
    private Rectangle helpBound;

    private BitmapFont fontS;
    private BitmapFont fontL;
    private BitmapFont fontScore;
    private BitmapFont fontScoreS;
    private GlyphLayout glS;
    private GlyphLayout glL1;
    private GlyphLayout glL2;
    private GlyphLayout glScore;
    private GlyphLayout glScoreS;
    private float fontWidthS;
    private float fontWidthL1;
    private float fontWidthL2;
    private float fontWidthScore;
    private float fontWidthScoreS;
    private float fontHeightS;
    private float fontHeightL1;
    private float fontHeightL2;
    private float fontHeightScore;
    private float fontHeightScoreS;
    private String scoreTxt;
    private String hsSmallTxt;
    private String hsLargeTxt1;
    private String hsLargeTxt2;
    private String scoreSTxt;

    private float a;
    private float a1;
    private float a2;
    private float aStart = 1;
    private float aReplay = 1;
    private float aShare = 1;
    private float aRate = 1;
    private float aLeaderboards = 1;
    private float aVolume = 1;
    private float aHelp = 1;

    private float yL1;
    private float yL2;
    private float yThreeStars;

    private int touchStart = 0;
    private int touchReplay = 0;
    private int touchShare = 0;
    private int touchRate = 0;
    private int touchLeaderboards = 0;
    private int touchVolume = 0;
    private int touchHelp = 0;

    private ShapeRenderer sr;

    private boolean newHS;

    private boolean transition;

    private DotDrop dd;

    private Vector3 touch;

    protected HighScore(GameStateManager gsm, DotDrop dd) {
        super(gsm);
        pref = Gdx.app.getPreferences("com.kevin.dotdropgame.settings");
        this.dd = dd;
        share = new Texture("share.png");
        rate = new Texture("rate.png");
        leaderboards = new Texture("leaderboards.png");
        volume = new Texture("volume.png");
        muted = new Texture("muted.png");
        threestars = new Texture("threestars.png");
        start = new Texture("start.png");
        replay = new Texture("replay.png");
        help = new Texture("help.png");

        threestarSprite = new Sprite(threestars);
        threestarSprite.setAlpha(a2);
        startSprite = new Sprite(start);
        startSprite.setAlpha(1);
        replaySprite = new Sprite(replay);
        replaySprite.setAlpha(1);
        shareSprite = new Sprite(share);
        shareSprite.setAlpha(1);
        rateSprite = new Sprite(rate);
        rateSprite.setAlpha(1);
        leaderboardsSprite = new Sprite(leaderboards);
        leaderboardsSprite.setAlpha(1);
        if (pref.getInteger("volume") == 1) {
            volumeSprite = new Sprite(volume);
        } else {
            volumeSprite = new Sprite(muted);
        }
        volumeSprite.setAlpha(1);
        helpSprite = new Sprite(help);
        helpSprite.setAlpha(1);

        shareBound = new Rectangle(20, DotDrop.HEIGHT - 138, 40 + share.getWidth(), 40 + share.getHeight());
        rateBound = new Rectangle(60 + share.getWidth(),
                DotDrop.HEIGHT - 138, 40 + rate.getWidth(), 40 + rate.getHeight());
        leaderboardsBound = new Rectangle(100 + share.getWidth() + rate.getWidth(),
                DotDrop.HEIGHT - 138, 40 + leaderboards.getWidth(), 40 + leaderboards.getHeight());
        if (pref.getInteger("volume") == 1) {
            volumeBound = new Rectangle(140 + share.getWidth() + rate.getWidth() + leaderboards.getWidth(),
                    DotDrop.HEIGHT - 138, 40 + volume.getWidth(), 40 + volume.getHeight());
        } else {
            volumeBound = new Rectangle(140 + share.getWidth() + rate.getWidth() + leaderboards.getWidth(),
                    DotDrop.HEIGHT - 138, 40 + muted.getWidth(), 40 + muted.getHeight());
        }
        helpBound = new Rectangle(180 + share.getWidth() + rate.getWidth() + leaderboards.getWidth() + volume.getWidth(),
                DotDrop.HEIGHT - 138, 40 + help.getWidth(), 40 + help.getHeight());
        startBound = new Rectangle(DotDrop.WIDTH / 2 - 60 - start.getWidth() - 20,
                DotDrop.HEIGHT / 6 - start.getHeight() - 20, 40 + start.getWidth(), 40 + start.getHeight());
        replayBound = new Rectangle(DotDrop.WIDTH / 2 + 40, DotDrop.HEIGHT / 6 - start.getHeight() - 20,
                40 + replay.getWidth(), 40 + replay.getHeight());

        cam.setToOrtho(false, DotDrop.WIDTH, DotDrop.HEIGHT);

        scoreTxt = Integer.toString(pref.getInteger("currentHighscore"));
        hsSmallTxt = "HIGHSCORE";
        if (pref.getInteger("currentHighscore") > pref.getInteger("highscore")) {
            newHS = true;
            pref.putInteger("highscore", pref.getInteger("currentHighscore"));
            pref.flush();
            dd.playServices.submitScore(pref.getInteger("highscore"));
        }
        scoreSTxt = Integer.toString(pref.getInteger("highscore"));
        if (newHS) {
            hsLargeTxt1 = "NEW";
            hsLargeTxt2 = "HIGHSCORE";
        } else {
            hsLargeTxt1 = "";
            hsLargeTxt2 = "SCORE";
        }
        // TODO: Make font and set them
        fontScore = new BitmapFont(Gdx.files.internal("mulicolor.fnt"));
        fontL = new BitmapFont(Gdx.files.internal("mulil.fnt"));
        fontS = new BitmapFont(Gdx.files.internal("mulis.fnt"));
        fontScoreS = new BitmapFont(Gdx.files.internal("mulihs.fnt"));
        glS = new GlyphLayout();
        glL1 = new GlyphLayout();
        glL2 = new GlyphLayout();
        glScore = new GlyphLayout();
        glScoreS = new GlyphLayout();
        glS.setText(fontS, hsSmallTxt);
        glL1.setText(fontL, hsLargeTxt1);
        glL2.setText(fontL, hsLargeTxt2);
        glScore.setText(fontScore, scoreTxt);
        glScoreS.setText(fontScoreS, scoreSTxt);
        fontWidthScore = glScore.width;
        fontHeightScore = glScore.height;
        fontWidthS = glS.width;
        fontHeightS = glS.height;
        fontWidthL1 = glL1.width;
        fontHeightL1 = glL1.height;
        fontWidthL2 = glL2.width;
        fontHeightL2 = glL2.height;
        fontWidthScoreS = glScoreS.width;
        fontHeightScoreS = glScoreS.height;
        a = 1;
        if (newHS) {
            yL2 = DotDrop.HEIGHT / 2 + fontHeightScore / 2 + 95 - 30;
        } else {
            yL2 = DotDrop.HEIGHT / 2 + fontHeightScore / 2 + 95;
        }
        yL1 = DotDrop.HEIGHT / 2 + fontHeightScore / 2 + 95 + fontHeightL2 + 30 - 30;
        yThreeStars = DotDrop.HEIGHT / 2 + fontHeightScore / 2 + 80 + fontHeightL2 + 20 + fontHeightL1 + 20 - 30;
        sr = new ShapeRenderer();
        if (pref.getInteger("currentHighscore") >= 1000) {
            dd.playServices.unlockAchievement();
            dd.playServices.showAchievement();
        }
        pref.putInteger("adCounter", pref.getInteger("adCounter") + 1);
        pref.flush();
        if (pref.getInteger("adCounter") == 5) {
            pref.putInteger("adCounter", 0);
            pref.flush();
            if (dd.ar.isWifiConnected()) {
                dd.ar.showInterstitialAd(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Interstitial app closed");
                    }
                });
            } else {
                System.out.println("Interstitial ad not loaded");
            }
        }
        Gdx.input.setInputProcessor(this);
        touch = new Vector3();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.BACK) {
            touchStart = 2;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenY, 0);
        cam.unproject(touch);
        if (startBound.contains(touch.x, touch.y) && touchStart == 0) {
            touchStart = 1;
            aStart = 0.5f;
        } else if (replayBound.contains(touch.x, touch.y) && touchReplay == 0) {
            touchReplay = 1;
            aReplay = 0.5f;
        } else if (volumeBound.contains(touch.x, touch.y) && touchVolume == 0) {
            touchVolume = 1;
            aVolume = 0.5f;
        } else if (shareBound.contains(touch.x, touch.y) && touchShare == 0) {
            touchShare = 1;
            aShare = 0.5f;
        } else if (rateBound.contains(touch.x, touch.y) && touchRate == 0) {
            touchRate = 1;
            aRate = 0.5f;
        } else if (leaderboardsBound.contains(touch.x, touch.y) && touchLeaderboards == 0) {
            touchLeaderboards = 1;
            aLeaderboards = 0.5f;
        } else if (helpBound.contains(touch.x, touch.y) && touchHelp == 0) {
            touchHelp = 1;
            aHelp = 0.5f;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenY, 0);
        cam.unproject(touch);
        if (touchStart == 1 && startBound.contains(touch.x, touch.y)) {
            touchStart = 2;
            aStart = 1;
        } else if (touchReplay == 1 && replayBound.contains(touch.x, touch.y)) {
            touchReplay = 2;
            aReplay = 1;
        } else if (touchVolume == 1 && volumeBound.contains(touch.x, touch.y)) {
            if (pref.getInteger("volume") == 1) {
                pref.putInteger("volume", 2);
                volumeSprite.setTexture(muted);
                volumeSprite.setSize(muted.getWidth(), muted.getHeight());
                volumeBound.setSize(40 + muted.getWidth(), 40 + muted.getHeight());
                pref.flush();

            } else {
                pref.putInteger("volume", 1);
                volumeSprite.setTexture(volume);
                volumeSprite.setSize(volume.getWidth(), volume.getHeight());
                volumeBound.setSize(40 + volume.getWidth(), 40 + volume.getHeight());
                pref.flush();
            }
            touchVolume = 0;
            aVolume = 1;
        } else if (touchShare == 1 && shareBound.contains(touch.x, touch.y)) {
            touchShare = 0;
            aShare = 1;

            byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);
            Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
            BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
            PixmapIO.writePNG(Gdx.files.local("screenshot.png"), pixmap);
            pixmap.dispose();

            dd.ar.shareIntent();
        } else if (touchRate == 1 && rateBound.contains(touch.x, touch.y)) {
            touchRate = 0;
            aRate = 1;

            Gdx.net.openURI("market://details?id=com.kevinli.dotdropgame");
        } else if (touchLeaderboards == 1 && leaderboardsBound.contains(touch.x, touch.y)) {
            touchLeaderboards = 0;
            aLeaderboards = 1;

            dd.playServices.showScore();
        } else if (touchHelp == 1 && helpBound.contains(touch.x, touch.y)) {
            touchHelp = 0;
            aHelp = 1;
            pref.putInteger("tutorial", 0);
            pref.flush();
            gsm.set(new Game(gsm, dd));
        } else {
            touchStart = 0;
            touchReplay = 0;
            touchVolume = 0;
            touchShare = 0;
            touchRate = 0;
            touchLeaderboards = 0;
            aStart = 1;
            aReplay = 1;
            aVolume = 1;
            aShare = 1;
            aRate = 1;
            aLeaderboards = 1;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        if (touchStart == 2) {
            fade(false);
            if (a >= 1) {
                gsm.set(new Start(gsm, dd));
            }
        } else if (touchReplay == 2) {
            fade(false);
            if (a >= 1) {
                gsm.set(new Game(gsm, dd));
            }
        } else {
            fade(true);
            if (a == 0 && newHS) {
                transition1();
                if (transition) {
                    transition2();
                }
            } else {
                a1 = 1;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(21/255f, 21/255f, 21/255f, 1);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        if (newHS) {
            fontL.setColor(0.664f, 0.5156f, 0.121f, a1);
        } else {
            fontL.setColor(1, 1, 1, a1);
        }
        fontScore.draw(sb, scoreTxt, DotDrop.WIDTH / 2 - fontWidthScore / 2, DotDrop.HEIGHT / 2 + fontHeightScore / 2);
        fontL.draw(sb, hsLargeTxt2, DotDrop.WIDTH / 2 - fontWidthL2 / 2, yL2);
        fontL.draw(sb, hsLargeTxt1, DotDrop.WIDTH / 2 - fontWidthL1 / 2, yL1);
        fontS.draw(sb, hsSmallTxt, DotDrop.WIDTH / 2 - fontWidthS / 2, DotDrop.HEIGHT / 3 + fontHeightS / 2 + 10);
        fontScoreS.draw(sb, scoreSTxt, DotDrop.WIDTH / 2 - fontWidthScoreS / 2, DotDrop.HEIGHT / 3 + fontHeightS / 2 - fontHeightScoreS / 2);
        if (newHS) {
            threestarSprite.setPosition((DotDrop.WIDTH / 2) - (threestars.getWidth() / 2), yThreeStars);
            threestarSprite.setAlpha(a2);
            threestarSprite.draw(sb);
        }
        startSprite.setPosition(DotDrop.WIDTH / 2 - 60 - start.getWidth(), DotDrop.HEIGHT / 6 - start.getHeight());
        replaySprite.setPosition(DotDrop.WIDTH / 2 + 60, DotDrop.HEIGHT / 6 - replay.getHeight());
        shareSprite.setPosition(40, DotDrop.HEIGHT - 118);
        rateSprite.setPosition(80 + share.getWidth(), DotDrop.HEIGHT - 118);
        leaderboardsSprite.setPosition(120 + share.getWidth() + rate.getWidth(), DotDrop.HEIGHT - 118);
        volumeSprite.setPosition(160 + share.getWidth() + rate.getWidth() + leaderboards.getWidth(), DotDrop.HEIGHT - 118);
        helpSprite.setPosition(200 + share.getWidth() + rate.getWidth() + leaderboards.getWidth() + volume.getWidth(),
                DotDrop.HEIGHT - 118);
        startSprite.setAlpha(aStart);
        replaySprite.setAlpha(aReplay);
        shareSprite.setAlpha(aShare);
        rateSprite.setAlpha(aRate);
        leaderboardsSprite.setAlpha(aLeaderboards);
        volumeSprite.setAlpha(aVolume);
        helpSprite.setAlpha(aHelp);
        shareSprite.draw(sb);
        rateSprite.draw(sb);
        leaderboardsSprite.draw(sb);
        volumeSprite.draw(sb);
        helpSprite.draw(sb);
        startSprite.draw(sb);
        replaySprite.draw(sb);
        sb.end();
        gl.glEnable(GL20.GL_BLEND);
        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.03f, 0.03f, 0.03f, a);
        sr.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        sr.setColor(Color.YELLOW);
//        sr.rect(shareBound.getX(), shareBound.getY(), shareBound.getWidth(), shareBound.getHeight());
//        sr.setColor(Color.RED);
//        sr.rect(rateBound.getX(), rateBound.getY(), rateBound.getWidth(), rateBound.getHeight());
//        sr.rect(leaderboardsBound.getX(), leaderboardsBound.getY(), leaderboardsBound.getWidth(), leaderboardsBound.getHeight());
//        sr.rect(startBound.getX(), startBound.getY(), startBound.getWidth(), startBound.getHeight());
//        sr.rect(replayBound.getX(), replayBound.getY(), replayBound.getWidth(), replayBound.getHeight());
        sr.end();
        gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void dispose() {
        shareSprite.getTexture().dispose();
        share.dispose();
        rateSprite.getTexture().dispose();
        rate.dispose();
        leaderboardsSprite.getTexture().dispose();
        leaderboards.dispose();
        volumeSprite.getTexture().dispose();
        volume.dispose();
        helpSprite.getTexture().dispose();
        help.dispose();
        threestarSprite.getTexture().dispose();
        threestars.dispose();
        startSprite.getTexture().dispose();
        start.dispose();
        replaySprite.getTexture().dispose();
        replay.dispose();
        fontS.dispose();
        fontL.dispose();
        fontScoreS.dispose();
        fontScore.dispose();
        sr.dispose();
    }

    private void fade(boolean out) {
        if (out) {
            a -= 0.1;
            if (a <= 0) {
                a = 0;
            }
        } else {
            a += 0.1;
//            if (a >= 1) {
//                a = 1;
//            }
        }
    }

    private void transition1() {
        a1 += 0.1;
        yL1 += 5;
        yL2 += 5;
        if (a1 >= 1) {
            a1 = 1;
        }
        if (yL1 >= DotDrop.HEIGHT / 2 + fontHeightScore / 2 + 95 + fontHeightL2 + 30) {
            yL1 = DotDrop.HEIGHT / 2 + fontHeightScore / 2 + 95 + fontHeightL2 + 30;
        }
        if (yL2 >=  DotDrop.HEIGHT / 2 + fontHeightScore / 2 + 95) {
            yL2 = DotDrop.HEIGHT / 2 + fontHeightScore / 2 + 95;
            transition = true;
        }
    }

    private void transition2() {
        a2 += 0.1;
        yThreeStars += 5;
        if (a2 >= 1) {
            a2 = 1;
        }
        if (yThreeStars >= DotDrop.HEIGHT / 2 + fontHeightScore / 2 + 80 + fontHeightL2 + 20 + fontHeightL1 + 20) {
            yThreeStars = DotDrop.HEIGHT / 2 + fontHeightScore / 2 + 80 + fontHeightL2 + 20 + fontHeightL1 + 20;
        }
    }
}
