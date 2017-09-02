package com.kevinli.dotdropgame.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.kevinli.dotdropgame.DotDrop;

/**
 * Created by Kevin on 7/30/2017.
 *
 * Legend:
 * 1 - Locked
 * 2 - Unlocked, Unselected
 * 3 - Unlocked, Selected
 */

public class Unlocks {
    private static final int UP_ACCEL = 700;

    private AssetManager assets;
    private TextureAtlas atlas;

    private Preferences pref;

    private Sprite lime;
    private Sprite aquamarine;
    private Sprite fire;
    private Sprite royal;
    private Sprite flamingo;
    private Sprite hotrod;
    private Sprite lilac;
    private Sprite lemon;
    private Sprite snow;
    private Sprite emerald;
    private Sprite chestnut;
    private Sprite shark;
    private Sprite midnight;
    private Sprite danube;
    private Sprite antimatter;
    private Sprite bg2;
    private Sprite back;

    private Sprite[] dots;
    private Array<Vector3> position;
    private Array<Circle> bounds;
    private Rectangle backBounds;

    private BitmapFont font;
    private BitmapFont font2; // faded color font

    private String limetxt;
    private String aquamarinetxt;
    private String firetxt;
    private String royaltxt;
    private String flamingotxt;
    private String hotrodtxt;
    private String lilactxt;
    private String lemontxt;
    private String snowtxt;
    private String emeraldtxt;
    private String chestnuttxt;
    private String sharktxt;
    private String midnighttxt;
    private String danubetxt;
    private String antimattertxt;

    private GlyphLayout gllime;
    private GlyphLayout glaquamarine;
    private GlyphLayout glfire;
    private GlyphLayout glroyal;
    private GlyphLayout glflamingo;
    private GlyphLayout glhotrod;
    private GlyphLayout gllilac;
    private GlyphLayout gllemon;
    private GlyphLayout glsnow;
    private GlyphLayout glemerald;
    private GlyphLayout glchestnut;
    private GlyphLayout glshark;
    private GlyphLayout glmidnight;
    private GlyphLayout gldanube;
    private GlyphLayout glantimatter;

    private float limeWidth;
    private float limeHeight;
    private float aquamarineWidth;
    private float aquamarineHeight;
    private float fireWidth;
    private float fireHeight;
    private float royalWidth;
    private float royalHeight;
    private float flamingoWidth;
    private float flamingoHeight;
    private float hotrodWidth;
    private float hotrodHeight;
    private float lilacWidth;
    private float lilacHeight;
    private float lemonWidth;
    private float lemonHeight;
    private float snowWidth;
    private float snowHeight;
    private float emeraldWidth;
    private float emeraldHeight;
    private float chestnutWidth;
    private float chestnutHeight;
    private float sharkWidth;
    private float sharkHeight;
    private float midnightWidth;
    private float midnightHeight;
    private float danubeWidth;
    private float danubeHeight;
    private float antimatterWidth;
    private float antimatterHeight;

    private float velocity;

    private float y;
    private float x;
    private float a;
    private float aAquamarine = 1;
    private float[] aDots;
    private float aBg2s;
    private float aWarning;

    private int selected;

    private BitmapFont fontWarning;
    private GlyphLayout glWarning;
    private float fontWidthWarning;
    private float fontHeightWarning;
    private String warningTxt;

    public Unlocks(float x, float y) {
        assets = new AssetManager();
        assets.load("udots.pack", TextureAtlas.class);
        assets.finishLoading();
        atlas = assets.get("udots.pack");

        pref = Gdx.app.getPreferences("com.kevin.dotdropgame.settings");
        if (!pref.contains("selected")) {
            pref.putInteger("selected", 0);
            pref.flush();
        }
        selected = pref.getInteger("selected");
        this.y = y;
        this.x = x;
        velocity = 0;
        dots = new Sprite[15];
        aDots = new float[15];
        position = new Array<Vector3>();
        bounds = new Array<Circle>();
        backBounds = new Rectangle();
        font = new BitmapFont(Gdx.files.internal("mulisemi.fnt"));
        font2 = new BitmapFont(Gdx.files.internal("mulisemi.fnt"));
        fontWarning = new BitmapFont(Gdx.files.internal("mulisemi.fnt"));
        font.setColor(1, 1, 1, 0);
        font2.setColor(1, 1, 1, 0);
        fontWarning.setColor(1, 1, 1, 1);
        prefCheck();
        setSprite();
        setSpriteAlpha();
        setText();
        setGlyph();
        createPosition();
        createBounds();
    }

    public void open(float dt) {
        if (position.get(1).y > 876.25) {
            y -= (910 - (position.get(1).y - 876.25)) / 5;
            positionY(y);
        } else if (position.get(2).y > 15) {
            y -= (position.get(1).y / 5);
            positionY(y);
        }

        for (int i = 0; i < 15; i++) {
            bounds.get(i).setPosition(position.get(i).x + dots[i].getWidth() / 2, position.get(i).y + dots[i].getHeight() / 2);
        }
        backBounds.setPosition(DotDrop.WIDTH / 2 - 150, back.getY() - 25);
        bg2sFadeIn();
    }

    public void close(float dt) {
        if (position.get(1).y < 876.25) {
            velocity += UP_ACCEL;
            velocity *= dt;
            for (int i = 0; i < 15; i++) {
                position.get(i).y += velocity;
            }
            velocity /= dt;
            y = position.get(1).y + (float) 122.5;
            positionY(y);
        } else if (position.get(2).y < 1727.5) {
            y += ((1727.5 - position.get(1).y) / 5);
            positionY(y);
        } else {
            y = 1850;
            positionY(y);
        }

        for (int i = 0; i < 15; i++) {
            bounds.get(i).setPosition(position.get(i).x + dots[i].getWidth() / 2, position.get(i).y + dots[i].getHeight() / 2);
        }
        backBounds.setPosition(DotDrop.WIDTH / 2 - 150, back.getY() - 25);
        bg2sFadeOut();
    }

    public void prefCheck() {
        if (!pref.contains("lime")) {
            pref.putInteger("lime", 3);
            pref.putInteger("selected", pref.getInteger("selected") + 1);
            pref.flush();
        }
        if (!pref.contains("aquamarine")) {
            pref.putInteger("aquamarine", 3);
            pref.putInteger("selected", pref.getInteger("selected") + 1);
            pref.flush();
        }
        if (!pref.contains("fire")) {
            pref.putInteger("fire", 3);
            pref.putInteger("selected", pref.getInteger("selected") + 1);
            pref.flush();
        }
        if (!pref.contains("royal")) {
            pref.putInteger("royal", 3);
            pref.putInteger("selected", pref.getInteger("selected") + 1);
            pref.flush();
        }
        if (!pref.contains("flamingo")) {
            pref.putInteger("flamingo", 3);
            pref.putInteger("selected", pref.getInteger("selected") + 1);
            pref.flush();
        }
        if (!pref.contains("hotrod")) {
            pref.putInteger("hotrod", 1);
        }
        if (!pref.contains("lilac")) {
            pref.putInteger("lilac", 1);
        }
        if (!pref.contains("lemon")) {
            pref.putInteger("lemon", 1);
        }
        if (!pref.contains("snow")) {
            pref.putInteger("snow", 1);
        }
        if (!pref.contains("emerald")) {
            pref.putInteger("emerald", 1);
        }
        if (!pref.contains("chestnut")) {
            pref.putInteger("chestnut", 1);
        }
        if (!pref.contains("shark")) {
            pref.putInteger("shark", 1);
        }
        if (!pref.contains("midnight")) {
            pref.putInteger("midnight", 1);
        }
        if (!pref.contains("danube")) {
            pref.putInteger("danube", 1);
        }
        if (!pref.contains("antimatter")) {
            pref.putInteger("antimatter", 1);
        }
        pref.flush();
    }

    public void setSprite() {
        lime = new Sprite(atlas.findRegion("ulime"));
        dots[0] = lime;
        aquamarine = new Sprite(atlas.findRegion("uaquamarine"));
        dots[1] = aquamarine;
        fire =  new Sprite(atlas.findRegion("ufire"));
        dots[2] = fire;
        royal = new Sprite(atlas.findRegion("uroyal"));
        dots[3] = royal;
        flamingo = new Sprite(atlas.findRegion("uflamingo"));
        dots[4] = flamingo;

        if (pref.getInteger("hotrod") == 1) {
            hotrod = new Sprite(new Texture("dotlocked.png"));
        } else {
            hotrod = new Sprite(atlas.findRegion("uhotrod"));
        }
        dots[5] = hotrod;

        if (pref.getInteger("lilac") == 1) {
            lilac = new Sprite(new Texture("dotlocked.png"));
        } else {
            lilac = new Sprite(atlas.findRegion("ulilac"));
        }
        dots[6] = lilac;

        if (pref.getInteger("lemon") == 1) {
            lemon = new Sprite(new Texture("dotlocked.png"));
        } else {
            lemon = new Sprite(atlas.findRegion("ulemon"));
        }
        dots[7] = lemon;

        if (pref.getInteger("snow") == 1) {
            snow = new Sprite(new Texture("dotlocked.png"));
        } else {
            snow = new Sprite(atlas.findRegion("usnow"));
        }
        dots[8] = snow;

        if (pref.getInteger("emerald") == 1) {
            emerald = new Sprite(new Texture("dotlocked.png"));
        } else {
            emerald = new Sprite(atlas.findRegion("uemerald"));
        }
        dots[9] = emerald;

        if (pref.getInteger("chestnut") == 1) {
            chestnut = new Sprite(new Texture("dotlocked.png"));
        } else {
            chestnut = new Sprite(atlas.findRegion("uchestnut"));
        }
        dots[10] = chestnut;

        if (pref.getInteger("shark") == 1) {
            shark = new Sprite(new Texture("dotlocked.png"));
        } else {
            shark = new Sprite(atlas.findRegion("ushark"));
        }
        dots[11] = shark;

        if (pref.getInteger("midnight") == 1) {
            midnight = new Sprite(new Texture("dotlocked.png"));
        } else {
            midnight = new Sprite(atlas.findRegion("umidnight"));
        }
        dots[12] = midnight;

        if (pref.getInteger("danube") == 1) {
            danube = new Sprite(new Texture("dotlocked.png"));
        } else {
            danube = new Sprite(atlas.findRegion("udanube"));
        }
        dots[13] = danube;

        if (pref.getInteger("antimatter") == 1) {
            antimatter = new Sprite(new Texture("dotlocked.png"));
        } else {
            antimatter = new Sprite(atlas.findRegion("uantimatter"));
        }
        dots[14] = antimatter;

        bg2 = new Sprite(new Texture("bg2.png"));
        back = new Sprite(new Texture("back.png"));
    }

    public void setSpriteAlpha() {
        lime.setAlpha(0);
        aquamarine.setAlpha(1);
        fire.setAlpha(0);
        royal.setAlpha(0);
        flamingo.setAlpha(0);
        hotrod.setAlpha(0);
        lilac.setAlpha(0);
        lemon.setAlpha(0);
        snow.setAlpha(0);
        emerald.setAlpha(0);
        chestnut.setAlpha(0);
        shark.setAlpha(0);
        midnight.setAlpha(0);
        danube.setAlpha(0);
        antimatter.setAlpha(0);

        if (pref.getInteger("lime") == 1 || pref.getInteger("lime") == 3) {
            aDots[0] = 1;
        } else {
            aDots[0] = (float) 0.25;
        }

        if (pref.getInteger("aquamarine") == 2) {
            aDots[1] = (float) 0.25;
        } else {
            aDots[1] = 1;
        }

        if (pref.getInteger("fire") == 1 || pref.getInteger("fire") == 3) {
            aDots[2] = 1;
        } else {
            aDots[2] = (float) 0.25;
        }

        if (pref.getInteger("royal") == 1 || pref.getInteger("royal") == 3) {
            aDots[3] = 1;
        } else {
            aDots[3] = (float) 0.25;
        }

        if (pref.getInteger("flamingo") == 1 || pref.getInteger("flamingo") == 3) {
            aDots[4] = 1;
        } else {
            aDots[4] = (float) 0.25;
        }

        if (pref.getInteger("hotrod") == 1 || pref.getInteger("hotrod") == 3) {
            aDots[5] = 1;
        } else {
            aDots[5] = (float) 0.25;
        }

        if (pref.getInteger("lilac") == 1 || pref.getInteger("lilac") == 3) {
            aDots[6] = 1;
        } else {
            aDots[6] = (float) 0.25;
        }

        if (pref.getInteger("lemon") == 1 || pref.getInteger("lemon") == 3) {
            aDots[7] = 1;
        } else {
            aDots[7] = (float) 0.25;
        }

        if (pref.getInteger("snow") == 1 || pref.getInteger("snow") == 3) {
            aDots[8] = 1;
        } else {
            aDots[8] = (float) 0.25;
        }

        if (pref.getInteger("emerald") == 1 || pref.getInteger("emerald") == 3) {
            aDots[9] = 1;
        } else {
            aDots[9] = (float) 0.25;
        }

        if (pref.getInteger("chestnut") == 1 || pref.getInteger("chestnut") == 3) {
            aDots[10] = 1;
        } else {
            aDots[10] = (float) 0.25;
        }

        if (pref.getInteger("shark") == 1 || pref.getInteger("shark") == 3) {
            aDots[11] = 1;
        } else {
            aDots[11] = (float) 0.25;
        }

        if (pref.getInteger("midnight") == 1 || pref.getInteger("midnight") == 3) {
            aDots[12] = 1;
        } else {
            aDots[12] = (float) 0.25;
        }

        if (pref.getInteger("danube") == 1 || pref.getInteger("danube") == 3) {
            aDots[13] = 1;
        } else {
            aDots[13] = (float) 0.25;
        }

        if (pref.getInteger("antimatter") == 1 || pref.getInteger("antimatter") == 3) {
            aDots[14] = 1;
        } else {
            aDots[14] = (float) 0.25;
        }

        bg2.setAlpha(0);
        back.setAlpha(0);
    }

    private void setText() {
        limetxt = "LIME";
        aquamarinetxt = "AQUAMARINE";
        firetxt = "FIRE";
        royaltxt = "ROYAL";
        flamingotxt = "FLAMINGO";

        if (pref.getInteger("hotrod") == 1) {
            hotrodtxt = "5";
        } else {
            hotrodtxt = "HOT ROD";
        }

        if (pref.getInteger("lilac") == 1) {
            lilactxt = "10";
        } else {
            lilactxt = "LILAC";
        }

        if (pref.getInteger("lemon") == 1) {
            lemontxt = "15";
        } else {
            lemontxt = "LEMON";
        }

        if (pref.getInteger("snow") == 1) {
            snowtxt = "25";
        } else {
            snowtxt = "SNOW";
        }

        if (pref.getInteger("emerald") == 1) {
            emeraldtxt = "50";
        } else {
            emeraldtxt = "EMERALD";
        }

        if (pref.getInteger("chestnut") == 1) {
            chestnuttxt = "75";
        } else {
            chestnuttxt = "CHESTNUT";
        }

        if (pref.getInteger("shark") == 1) {
            sharktxt = "100";
        } else {
            sharktxt = "SHARK";
        }

        if (pref.getInteger("midnight") == 1) {
            midnighttxt = "250";
        } else {
            midnighttxt = "MIDNIGHT";
        }

        if (pref.getInteger("danube") == 1) {
            danubetxt = "500";
        } else {
            danubetxt = "DANUBE";
        }

        if (pref.getInteger("antimatter") == 1) {
            antimattertxt = "1000";
        } else {
            antimattertxt = "ANTIMATTER";
        }

        warningTxt = "You must select at least 5 dots";
    }

    private void setGlyph() {
        gllime = new GlyphLayout();
        glaquamarine = new GlyphLayout();
        glfire = new GlyphLayout();
        glroyal = new GlyphLayout();
        glflamingo = new GlyphLayout();
        glhotrod = new GlyphLayout();
        gllilac = new GlyphLayout();
        gllemon = new GlyphLayout();
        glsnow = new GlyphLayout();
        glemerald = new GlyphLayout();
        glchestnut = new GlyphLayout();
        glshark = new GlyphLayout();
        glmidnight = new GlyphLayout();
        gldanube = new GlyphLayout();
        glantimatter = new GlyphLayout();
        glWarning = new GlyphLayout();

        gllime.setText(font, limetxt);
        glaquamarine.setText(font, aquamarinetxt);
        glfire.setText(font, firetxt);
        glroyal.setText(font, royaltxt);
        glflamingo.setText(font, flamingotxt);
        glhotrod.setText(font, hotrodtxt);
        gllilac.setText(font, lilactxt);
        gllemon.setText(font, lemontxt);
        glsnow.setText(font, snowtxt);
        glemerald.setText(font, emeraldtxt);
        glchestnut.setText(font, chestnuttxt);
        glshark.setText(font, sharktxt);
        glmidnight.setText(font, midnighttxt);
        gldanube.setText(font, danubetxt);
        glantimatter.setText(font, antimattertxt);
        glWarning.setText(font, warningTxt);

        limeWidth = gllime.width;
        limeHeight = gllime.height;
        aquamarineWidth = glaquamarine.width;
        aquamarineHeight = glaquamarine.height;
        fireWidth = glfire.width;
        fireHeight = glfire.height;
        royalWidth = glroyal.width;
        royalHeight = glroyal.height;
        flamingoWidth = glflamingo.width;
        flamingoHeight = glflamingo.height;
        hotrodWidth = glhotrod.width;
        hotrodHeight = glhotrod.height;
        lilacWidth = gllilac.width;
        lilacHeight = gllilac.height;
        lemonWidth = gllemon.width;
        lemonHeight = gllemon.height;
        snowWidth = glsnow.width;
        snowHeight = glsnow.height;
        emeraldWidth = glemerald.width;
        emeraldHeight = glemerald.height;
        chestnutWidth = glchestnut.width;
        chestnutHeight = glchestnut.height;
        sharkWidth = glshark.width;
        sharkHeight = glshark.height;
        midnightWidth = glmidnight.width;
        midnightHeight = glmidnight.height;
        danubeWidth = gldanube.width;
        danubeHeight = gldanube.height;
        antimatterWidth = glantimatter.width;
        antimatterHeight = glantimatter.height;
        fontWidthWarning = glWarning.width;
        fontHeightWarning = glWarning.height;
    }

    public void createPosition() {
        position.add(new Vector3((float) -122.5 + x, (float) -122.5 + y, 0));
        position.add(new Vector3(204 + x, (float) -122.5 + y, 0));
        position.add(new Vector3((float) 530.5 + x, (float) -122.5 + y, 0));
        position.add(new Vector3((float) -122.5 + x, (float) 228.5 + y, 0));
        position.add(new Vector3(204 + x, (float) 228.5 + y, 0));

        if (pref.getInteger("hotrod") == 1) {
            position.add(new Vector3(653 + x, 351 + y, 0));
        } else {
            position.add(new Vector3((float) 530.5 + x, (float) 228.5 + y, 0));
        }

        if (pref.getInteger("lilac") == 1) {
            position.add(new Vector3(x, 702 + y, 0));
        } else {
            position.add(new Vector3((float) -122.5 + x, (float) 579.5 + y, 0));
        }

        if (pref.getInteger("lemon") == 1) {
            position.add(new Vector3((float) 326.5 + x, 702 + y, 0));
        } else {
            position.add(new Vector3(204 + x, (float) 579.5 + y, 0));
        }

        if (pref.getInteger("snow") == 1) {
            position.add(new Vector3(653 + x, 702 + y, 0));
        } else {
            position.add(new Vector3((float) 530.5 + x, (float) 579.5 + y, 0));
        }

        if (pref.getInteger("emerald") == 1) {
            position.add(new Vector3(x, 1053 + y, 0));
        } else {
            position.add(new Vector3((float) -122.5 + x, (float) 930.5 + y, 0));
        }

        if (pref.getInteger("chestnut") == 1) {
            position.add(new Vector3((float) 326.5 + x, 1053 + y, 0));
        } else {
            position.add(new Vector3(204 + x, (float) 930.5 + y, 0));
        }

        if (pref.getInteger("shark") == 1) {
            position.add(new Vector3(653 + x, 1053 + y, 0));
        } else {
            position.add(new Vector3((float) 530.5 + x, (float) 930.5 + y, 0));
        }

        if (pref.getInteger("midnight") == 1) {
            position.add(new Vector3(x, 1404 + y, 0));
        } else {
            position.add(new Vector3((float) -122.5 + x, (float) 1281.5 + y, 0));
        }

        if (pref.getInteger("danube") == 1) {
            position.add(new Vector3((float) 326.5 + x, 1404 + y, 0));
        } else {
            position.add(new Vector3(204 + x, (float) 1281.5 + y, 0));
        }

        if (pref.getInteger("antimatter") == 1) {
            position.add(new Vector3(653 + x, 1404 + y, 0));
        } else {
            position.add(new Vector3((float) 530.5 + x, (float) 1281.5 + y, 0));
        }

        back.setPosition(DotDrop.WIDTH / 2 - back.getWidth() / 2, 1720 + y);
    }

    public void positionY(float y) {
        position.get(0).set((float) -122.5 + x, (float) -122.5 + y, 0);
        position.get(1).set(204 + x, (float) -122.5 + y, 0);
        position.get(2).set((float) 530.5 + x, (float) -122.5 + y, 0);
        position.get(3).set((float) -122.5 + x, (float) 228.5 + y, 0);
        position.get(4).set(new Vector3(204 + x, (float) 228.5 + y, 0));

        if (pref.getInteger("hotrod") == 1) {
            position.get(5).set(653 + x, 351 + y, 0);
        } else {
            position.get(5).set((float) 530.5 + x, (float) 228.5 + y, 0);
        }

        if (pref.getInteger("lilac") == 1) {
            position.get(6).set(x, 702 + y, 0);
        } else {
            position.get(6).set((float) -122.5 + x, (float) 579.5 + y, 0);
        }

        if (pref.getInteger("lemon") == 1) {
            position.get(7).set((float) 326.5 + x, 702 + y, 0);
        } else {
            position.get(7).set(204 + x, (float) 579.5 + y, 0);
        }

        if (pref.getInteger("snow") == 1) {
            position.get(8).set(653 + x, 702 + y, 0);
        } else {
            position.get(8).set((float) 530.5 + x, (float) 579.5 + y, 0);
        }

        if (pref.getInteger("emerald") == 1) {
            position.get(9).set(x, 1053 + y, 0);
        } else {
            position.get(9).set((float) -122.5 + x, (float) 930.5 + y, 0);
        }

        if (pref.getInteger("chestnut") == 1) {
            position.get(10).set((float) 326.5 + x, 1053 + y, 0);
        } else {
            position.get(10).set(204 + x, (float) 930.5 + y, 0);
        }

        if (pref.getInteger("shark") == 1) {
            position.get(11).set(653 + x, 1053 + y, 0);
        } else {
            position.get(11).set((float) 530.5 + x, (float) 930.5 + y, 0);
        }

        if (pref.getInteger("midnight") == 1) {
            position.get(12).set(x, 1404 + y, 0);
        } else {
            position.get(12).set((float) -122.5 + x, (float) 1281.5 + y, 0);
        }

        if (pref.getInteger("danube") == 1) {
            position.get(13).set((float) 326.5 + x, 1404 + y, 0);
        } else {
            position.get(13).set(204 + x, (float) 1281.5 + y, 0);
        }

        if (pref.getInteger("antimatter") == 1) {
            position.get(14).set(653 + x, 1404 + y, 0);
        } else {
            position.get(14).set((float) 530.5 + x, (float) 1281.5 + y, 0);
        }

        back.setPosition(DotDrop.WIDTH / 2 - back.getWidth() / 2, 1720 + y);
    }

    public void createBounds() {
        bounds.add(new Circle(x + 113, y + 113, 113));
        bounds.add(new Circle((float) 439.5 + x, y + 113, 113));
        bounds.add(new Circle(x + 766, y + 113, 113));

        bounds.add(new Circle(x + 113, y + 464, 113));
        bounds.add(new Circle((float) 439.5 + x, y + 464, 113));
        bounds.add(new Circle(x + 766, y + 464, 113));

        bounds.add(new Circle(x + 113, y + 815, 113));
        bounds.add(new Circle((float) 439.5 + x, y + 815, 113));
        bounds.add(new Circle(x + 766, y + 815, 113));

        bounds.add(new Circle(x + 113, y + 1161, 113));
        bounds.add(new Circle((float) 439.5 + x, y + 1161, 113));
        bounds.add(new Circle(x + 766, y + 1161, 113));

        bounds.add(new Circle(x + 113, y + 1517, 113));
        bounds.add(new Circle((float) 439.5 + x, y + 1517, 113));
        bounds.add(new Circle(x + 766, y + 1517, 113));

        backBounds.setPosition(DotDrop.WIDTH / 2 - 150, 1685 + y);
        backBounds.setSize(300, 50 + back.getHeight());
    }

    public Sprite[] getSprite() {
        return dots;
    }

    public Array<Vector3> getPosition() {
        return position;
    }

    public Array<Circle> getBounds() {
        return bounds;
    }

    public Rectangle getBackBounds() {
        return backBounds;
    }

    public Sprite getBg2() {
        return bg2;
    }

    public Sprite getBack() {
        return back;
    }

    public String[] getDotNames() {
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

    public void drawText(SpriteBatch sb) {
        if (pref.getInteger("lime") == 3) {
            font.draw(sb, limetxt, lime.getX() + (lime.getWidth() / 2) - limeWidth / 2,
                    lime.getY() + (lime.getHeight() / 2) - 113 - 20 - limeHeight / 2);
        } else {
            font2.draw(sb, limetxt, lime.getX() + (lime.getWidth() / 2) - limeWidth / 2,
                    lime.getY() + (lime.getHeight() / 2) - 113 - 20 - limeHeight / 2);
        }

        if (pref.getInteger("aquamarine") == 3) {
            font.draw(sb, aquamarinetxt, aquamarine.getX() + (aquamarine.getWidth() / 2) - aquamarineWidth / 2,
                    aquamarine.getY() + (aquamarine.getHeight() / 2) - 113 - 20 - aquamarineHeight / 2);
        } else {
            font2.draw(sb, aquamarinetxt, aquamarine.getX() + (aquamarine.getWidth() / 2) - aquamarineWidth / 2,
                    aquamarine.getY() + (aquamarine.getHeight() / 2) - 113 - 20 - aquamarineHeight / 2);
        }

        if (pref.getInteger("fire") == 3) {
            font.draw(sb, firetxt, fire.getX() + (fire.getWidth() / 2) - fireWidth / 2,
                    fire.getY() + (fire.getHeight() / 2) - 113 - 20 - fireHeight / 2);
        } else {
            font2.draw(sb, firetxt, fire.getX() + (fire.getWidth() / 2) - fireWidth / 2,
                    fire.getY() + (fire.getHeight() / 2) - 113 - 20 - fireHeight / 2);
        }

        if (pref.getInteger("royal") == 3) {
            font.draw(sb, royaltxt, royal.getX() + (royal.getWidth() / 2) - royalWidth / 2,
                    royal.getY() + (royal.getHeight() / 2) - 113 - 20 - royalHeight / 2);
        } else {
            font2.draw(sb, royaltxt, royal.getX() + (royal.getWidth() / 2) - royalWidth / 2,
                    royal.getY() + (royal.getHeight() / 2) - 113 - 20 - royalHeight / 2);
        }

        if (pref.getInteger("flamingo") == 3) {
            font.draw(sb, flamingotxt, flamingo.getX() + (flamingo.getWidth() / 2) - flamingoWidth / 2,
                    flamingo.getY() + (flamingo.getHeight() / 2) - 113 - 20 - flamingoHeight / 2);
        } else {
            font2.draw(sb, flamingotxt, flamingo.getX() + (flamingo.getWidth() / 2) - flamingoWidth / 2,
                    flamingo.getY() + (flamingo.getHeight() / 2) - 113 - 20 - flamingoHeight / 2);
        }

        if (pref.getInteger("hotrod") == 1 || pref.getInteger("hotrod") == 2) {
            font2.draw(sb, hotrodtxt, hotrod.getX() + (hotrod.getWidth() / 2) - hotrodWidth / 2,
                    hotrod.getY() + (hotrod.getHeight() / 2) - 113 - 20 - hotrodHeight / 2);
        } else {
            font.draw(sb, hotrodtxt, hotrod.getX() + (hotrod.getWidth() / 2) - hotrodWidth / 2,
                    hotrod.getY() + (hotrod.getHeight() / 2) - 113 - 20 - hotrodHeight / 2);
        }

        if (pref.getInteger("lilac") == 1 || pref.getInteger("lilac") == 2) {
            font2.draw(sb, lilactxt, lilac.getX() + (lilac.getWidth() / 2) - lilacWidth / 2,
                    lilac.getY() + (lilac.getHeight() / 2) - 113 - 20 - lilacHeight / 2);
        } else {
            font.draw(sb, lilactxt, lilac.getX() + (lilac.getWidth() / 2) - lilacWidth / 2,
                    lilac.getY() + (lilac.getHeight() / 2) - 113 - 20 - lilacHeight / 2);
        }

        if (pref.getInteger("lemon") == 1 || pref.getInteger("lemon") == 2) {
            font2.draw(sb, lemontxt, lemon.getX() + (lemon.getWidth() / 2) - lemonWidth / 2,
                    lemon.getY() + (lemon.getHeight() / 2) - 113 - 20 - lemonHeight / 2);
        } else {
            font.draw(sb, lemontxt, lemon.getX() + (lemon.getWidth() / 2) - lemonWidth / 2,
                    lemon.getY() + (lemon.getHeight() / 2) - 113 - 20 - lemonHeight / 2);
        }

        if (pref.getInteger("snow") == 1 || pref.getInteger("snow") == 2) {
            font2.draw(sb, snowtxt, snow.getX() + (snow.getWidth() / 2) - snowWidth / 2,
                    snow.getY() + (snow.getHeight() / 2) - 113 - 20 - snowHeight / 2);
        } else {
            font.draw(sb, snowtxt, snow.getX() + (snow.getWidth() / 2) - snowWidth / 2,
                    snow.getY() + (snow.getHeight() / 2) - 113 - 20 - snowHeight / 2);
        }

        if (pref.getInteger("emerald") == 1 || pref.getInteger("emerald") == 2) {
            font2.draw(sb, emeraldtxt, emerald.getX() + (emerald.getWidth() / 2) - emeraldWidth / 2,
                    emerald.getY() + (emerald.getHeight() / 2) - 113 - 20 - emeraldHeight / 2);
        } else {
            font.draw(sb, emeraldtxt, emerald.getX() + (emerald.getWidth() / 2) - emeraldWidth / 2,
                    emerald.getY() + (emerald.getHeight() / 2) - 113 - 20 - emeraldHeight / 2);
        }

        if (pref.getInteger("chestnut") == 1 || pref.getInteger("chestnut") == 2) {
            font2.draw(sb, chestnuttxt, chestnut.getX() + (chestnut.getWidth() / 2) - chestnutWidth / 2,
                    chestnut.getY() + (chestnut.getHeight() / 2) - 113 - 20 - chestnutHeight / 2);
        } else {
            font.draw(sb, chestnuttxt, chestnut.getX() + (chestnut.getWidth() / 2) - chestnutWidth / 2,
                    chestnut.getY() + (chestnut.getHeight() / 2) - 113 - 20 - chestnutHeight / 2);
        }

        if (pref.getInteger("shark") == 1 || pref.getInteger("shark") == 2) {
            font2.draw(sb, sharktxt, shark.getX() + (shark.getWidth() / 2) - sharkWidth / 2,
                    shark.getY() + (shark.getHeight() / 2) - 113 - 20 - sharkHeight / 2);
        } else {
            font.draw(sb, sharktxt, shark.getX() + (shark.getWidth() / 2) - sharkWidth / 2,
                    shark.getY() + (shark.getHeight() / 2) - 113 - 20 - sharkHeight / 2);
        }

        if (pref.getInteger("midnight") == 1 || pref.getInteger("midnight") == 2) {
            font2.draw(sb, midnighttxt, midnight.getX() + (midnight.getWidth() / 2) - midnightWidth / 2,
                    midnight.getY() + (midnight.getHeight() / 2) - 113 - 20 - midnightHeight / 2);
        } else {
            font.draw(sb, midnighttxt, midnight.getX() + (midnight.getWidth() / 2) - midnightWidth / 2,
                    midnight.getY() + (midnight.getHeight() / 2) - 113 - 20 - midnightHeight / 2);
        }

        if (pref.getInteger("danube") == 1 || pref.getInteger("danube") == 2) {
            font2.draw(sb, danubetxt, danube.getX() + (danube.getWidth() / 2) - danubeWidth / 2,
                    danube.getY() + (danube.getHeight() / 2) - 113 - 20 - danubeHeight / 2);
        } else {
            font.draw(sb, danubetxt, danube.getX() + (danube.getWidth() / 2) - danubeWidth / 2,
                    danube.getY() + (danube.getHeight() / 2) - 113 - 20 - danubeHeight / 2);
        }

        if (pref.getInteger("antimatter") == 1 || pref.getInteger("antimatter") == 2) {
            font2.draw(sb, antimattertxt, antimatter.getX() + (antimatter.getWidth() / 2) - antimatterWidth / 2,
                    antimatter.getY() + (antimatter.getHeight() / 2) - 113 - 20 - antimatterHeight / 2);
        } else {
            font.draw(sb, antimattertxt, antimatter.getX() + (antimatter.getWidth() / 2) - antimatterWidth / 2,
                    antimatter.getY() + (antimatter.getHeight() / 2) - 113 - 20 - antimatterHeight / 2);
        }

        fontWarning.draw(sb, warningTxt, DotDrop.WIDTH / 2 - fontWidthWarning / 2, backBounds.getY());
    }

    public void fadeIn() {
        aAquamarine -= 0.075;
        if (aAquamarine < a / 4) {
            aAquamarine = a / 4;
        }
        a += 0.075;
        if (a > 1) {
            a = 1;
        }

        if (pref.getInteger("lime") == 1 || pref.getInteger("lime") == 3) {
            lime.setAlpha(a);
        } else {
            lime.setAlpha(a / 4);
        }

        if (pref.getInteger("aquamarine") == 3) {
            aquamarine.setAlpha(1);
        } else {
            aquamarine.setAlpha(aAquamarine);
        }

        if (pref.getInteger("fire") == 1 || pref.getInteger("fire") == 3) {
            fire.setAlpha(a);
        } else {
            fire.setAlpha(a / 4);
        }

        if (pref.getInteger("royal") == 1 || pref.getInteger("royal") == 3) {
            royal.setAlpha(a);
        } else {
            royal.setAlpha(a / 4);
        }

        if (pref.getInteger("flamingo") == 1 || pref.getInteger("flamingo") == 3) {
            flamingo.setAlpha(a);
        } else {
            flamingo.setAlpha(a / 4);
        }

        if (pref.getInteger("hotrod") == 1 || pref.getInteger("hotrod") == 3) {
            hotrod.setAlpha(a);
        } else {
            hotrod.setAlpha(a / 4);
        }

        if (pref.getInteger("lilac") == 1 || pref.getInteger("lilac") == 3) {
            lilac.setAlpha(a);
        } else {
            lilac.setAlpha(a / 4);
        }

        if (pref.getInteger("lemon") == 1 || pref.getInteger("lemon") == 3) {
            lemon.setAlpha(a);
        } else {
            lemon.setAlpha(a / 4);
        }

        if (pref.getInteger("snow") == 1 || pref.getInteger("snow") == 3) {
            snow.setAlpha(a);
        } else {
            snow.setAlpha(a / 4);
        }

        if (pref.getInteger("emerald") == 1 || pref.getInteger("emerald") == 3) {
            emerald.setAlpha(a);
        } else {
            emerald.setAlpha(a / 4);
        }

        if (pref.getInteger("chestnut") == 1 || pref.getInteger("chestnut") == 3) {
            chestnut.setAlpha(a);
        } else {
            chestnut.setAlpha(a / 4);
        }

        if (pref.getInteger("shark") == 1 || pref.getInteger("shark") == 3) {
            shark.setAlpha(a);
        } else {
            shark.setAlpha(a / 4);
        }

        if (pref.getInteger("midnight") == 1 || pref.getInteger("midnight") == 3) {
            midnight.setAlpha(a);
        } else {
            midnight.setAlpha(a / 4);
        }

        if (pref.getInteger("danube") == 1 || pref.getInteger("danube") == 3) {
            danube.setAlpha(a);
        } else {
            danube.setAlpha(a / 4);
        }

        if (pref.getInteger("antimatter") == 1 || pref.getInteger("antimatter") == 3) {
            antimatter.setAlpha(a);
        } else {
            antimatter.setAlpha(a / 4);
        }

        back.setAlpha(a);

        font.setColor(1, 1, 1, a * (float) 0.4);
        font2.setColor(1, 1, 1, a * (float) 0.2);
        fontWarning.setColor(1, 1, 1, a * (float) 0.4);
    }

    public void fadeOut() {
        aAquamarine += 0.075;
        if (aAquamarine > 1) {
            aAquamarine = 1;
        }
        a -= 0.075;
        if (a < 0) {
            a = 0;
        }

        if (pref.getInteger("lime") == 1 || pref.getInteger("lime") == 3) {
            lime.setAlpha(a);
        } else {
            lime.setAlpha(a / 4);
        }

        if (pref.getInteger("aquamarine") == 2) {
            aquamarine.setAlpha(aAquamarine);
        }

        if (pref.getInteger("fire") == 1 || pref.getInteger("fire") == 3) {
            fire.setAlpha(a);
        } else {
            fire.setAlpha(a / 4);
        }

        if (pref.getInteger("royal") == 1 || pref.getInteger("royal") == 3) {
            royal.setAlpha(a);
        } else {
            royal.setAlpha(a / 4);
        }

        if (pref.getInteger("flamingo") == 1 || pref.getInteger("flamingo") == 3) {
            flamingo.setAlpha(a);
        } else {
            flamingo.setAlpha(a / 4);
        }

        if (pref.getInteger("hotrod") == 1 || pref.getInteger("hotrod") == 3) {
            hotrod.setAlpha(a);
        } else {
            hotrod.setAlpha(a / 4);
        }

        if (pref.getInteger("lilac") == 1 || pref.getInteger("lilac") == 3) {
            lilac.setAlpha(a);
        } else {
            lilac.setAlpha(a / 4);
        }

        if (pref.getInteger("lemon") == 1 || pref.getInteger("lemon") == 3) {
            lemon.setAlpha(a);
        } else {
            lemon.setAlpha(a / 4);
        }

        if (pref.getInteger("snow") == 1 || pref.getInteger("snow") == 3) {
            snow.setAlpha(a);
        } else {
            snow.setAlpha(a / 4);
        }

        if (pref.getInteger("emerald") == 1 || pref.getInteger("emerald") == 3) {
            emerald.setAlpha(a);
        } else {
            emerald.setAlpha(a / 4);
        }

        if (pref.getInteger("chestnut") == 1 || pref.getInteger("chestnut") == 3) {
            chestnut.setAlpha(a);
        } else {
            chestnut.setAlpha(a / 4);
        }

        if (pref.getInteger("shark") == 1 || pref.getInteger("shark") == 3) {
            shark.setAlpha(a);
        } else {
            shark.setAlpha(a / 4);
        }

        if (pref.getInteger("midnight") == 1 || pref.getInteger("midnight") == 3) {
            midnight.setAlpha(a);
        } else {
            midnight.setAlpha(a / 4);
        }

        if (pref.getInteger("danube") == 1 || pref.getInteger("danube") == 3) {
            danube.setAlpha(a);
        } else {
            danube.setAlpha(a / 4);
        }

        if (pref.getInteger("antimatter") == 1 || pref.getInteger("antimatter") == 3) {
            antimatter.setAlpha(a);
        } else {
            antimatter.setAlpha(a / 4);
        }

        back.setAlpha(a);

        font.setColor(1, 1, 1, a * (float) 0.4);
        font2.setColor(1, 1, 1, a * (float) 0.2);
        fontWarning.setColor(1, 1, 1, a * (float) 0.4);
    }

    public void fadeSelect(String dot, int i) {
        if (pref.getInteger(dot) == 3) {
            aDots[i] += 0.075;
            if (aDots[i] > 1) {
                aDots[i] = 1;
            }
        } else if (pref.getInteger(dot) == 2){
            aDots[i] -= 0.075;
            if (aDots[i] < (float) 0.25) {
                aDots[i] = (float) 0.25;
            }
        }
        dots[i].setAlpha(aDots[i]);
    }

    private void bg2sFadeIn() {
        aBg2s += 0.01;
        if (a >= 1) {
            a = 1;
        }
        bg2.setAlpha(a);
    }

    private void bg2sFadeOut() {
        aBg2s -= 0.075;
        if (a <= 0) {
            a = 0;
        }
        bg2.setAlpha(a);
    }

    public void setSelected(String dot) {
        if (pref.getInteger(dot) == 3 && pref.getInteger("selected") >= 6) {
            pref.putInteger(dot, 2);
            pref.putInteger("selected", pref.getInteger("selected") - 1);
        } else if (pref.getInteger(dot) == 2) {
            pref.putInteger(dot, 3);
            pref.putInteger("selected", pref.getInteger("selected") + 1);
        }
        pref.flush();
    }

    public void dispose() {
        lime.getTexture().dispose();
        aquamarine.getTexture().dispose();
        fire.getTexture().dispose();
        royal.getTexture().dispose();
        flamingo.getTexture().dispose();
        hotrod.getTexture().dispose();
        lilac.getTexture().dispose();
        lemon.getTexture().dispose();
        snow.getTexture().dispose();
        emerald.getTexture().dispose();
        chestnut.getTexture().dispose();
        shark.getTexture().dispose();
        midnight.getTexture().dispose();
        danube.getTexture().dispose();
        antimatter.getTexture().dispose();
        bg2.getTexture().dispose();
        back.getTexture().dispose();
        font.dispose();
        font2.dispose();
        fontWarning.dispose();
    }
}