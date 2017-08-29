package com.kevinli.dotdropgame.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private Texture lime;
    private Texture aquamarine;
    private Texture fire;
    private Texture royal;
    private Texture flamingo;
    private Texture hotrod;
    private Texture lilac;
    private Texture lemon;
    private Texture snow;
    private Texture emerald;
    private Texture chestnut;
    private Texture shark;
    private Texture midnight;
    private Texture danube;
    private Texture antimatter;
    private Texture bg2;
    private Texture back;

    private Sprite limes;
    private Sprite aquamarines;
    private Sprite fires;
    private Sprite royals;
    private Sprite flamingos;
    private Sprite hotrods;
    private Sprite lilacs;
    private Sprite lemons;
    private Sprite snows;
    private Sprite emeralds;
    private Sprite chestnuts;
    private Sprite sharks;
    private Sprite midnights;
    private Sprite danubes;
    private Sprite antimatters;
    private Sprite bg2S;
    private Sprite backS;

    private Preferences pref;

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
        setTexture();
        setSprite();
        setSpriteAlpha();
        setText();
        setGlyph();
        createPosition();
        createBounds();
    }

    public void open(float dt) {
//        if (position.get(1).y >= -22) {//873.5
//            velocity += DOWN_ACCEL;
////        } else if (position.get(1).y >= -22.5){
////            velocity += UP_ACCEL;
////        } else {
//        } else {
//            velocity = 0;
//        }
//        velocity *= dt;
//        if (position.get(1).y >= -22) {
//            velocity = (float) -126.75;
//        } else {
//            velocity = 0;
//        }
//        for (int i = 0; i < 15; i++) {
//            position.get(i).y += velocity;
//        }
//        velocity /= dt;

        if (position.get(1).y > 876.25) {
//            velocity += DOWN_ACCEL;
//            velocity *= dt;
//            for (int i = 0; i < 15; i++) {
//                position.get(i).y += velocity;
//            }
//            velocity /= dt;
//            y = position.get(1).y + (float) 122.5;
            y -= (910 - (position.get(1).y - 876.25)) / 5;
            positionY(y);
        } else if (position.get(2).y > 15) {
            y -= (position.get(1).y / 5);
            positionY(y);
        }

        for (int i = 0; i < 15; i++) {
            bounds.get(i).setPosition(position.get(i).x + dots[i].getWidth() / 2, position.get(i).y + dots[i].getHeight() / 2);
        }
        backBounds.setPosition(DotDrop.WIDTH / 2 - 150, backS.getY() - 25);
        bg2sFadeIn();
    }

    public void close(float dt) {
//        if (position.get(1).y <= 650) {//1225
//            velocity += UP_ACCEL_2;
//        } else if (position.get(1).y <= 1875){
//            velocity += DOWN_ACCEL_2;
//            if (velocity <= 0) {
//                velocity = 0;
//            }
//        } else {
//            velocity = 0;
//        }
//        velocity *= dt;
//        for (int i = 0; i < 15; i++) {
//            position.get(i).y += velocity;
//        }
//        velocity /= dt;
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
        backBounds.setPosition(DotDrop.WIDTH / 2 - 150, backS.getY() - 25);
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

    public void setTexture() {
        lime = new Texture("ulime.png");
        aquamarine = new Texture("uaquamarine.png");
        fire = new Texture("ufire.png");
        royal = new Texture("uroyal.png");
        flamingo = new Texture("uflamingo.png");

        if (pref.getInteger("hotrod") == 1) {
            hotrod = new Texture("dotlocked.png");
        } else {
            hotrod = new Texture("uhotrod.png");
        }

        if (pref.getInteger("lilac") == 1) {
            lilac = new Texture("dotlocked.png");
        } else {
            lilac = new Texture("ulilac.png");
        }

        if (pref.getInteger("lemon") == 1) {
            lemon = new Texture("dotlocked.png");
        } else {
            lemon = new Texture("ulemon.png");
        }

        if (pref.getInteger("snow") == 1) {
            snow = new Texture("dotlocked.png");
        } else {
            snow = new Texture("usnow.png");
        }

        if (pref.getInteger("emerald") == 1) {
            emerald = new Texture("dotlocked.png");
        } else {
            emerald = new Texture("uemerald.png");
        }

        if (pref.getInteger("chestnut") == 1) {
            chestnut = new Texture("dotlocked.png");
        } else {
            chestnut = new Texture("uchestnut.png");
        }

        if (pref.getInteger("shark") == 1) {
            shark = new Texture("dotlocked.png");
        } else {
            shark = new Texture("ushark.png");
        }

        if (pref.getInteger("midnight") == 1) {
            midnight = new Texture("dotlocked.png");
        } else {
            midnight = new Texture("umidnight.png");
        }

        if (pref.getInteger("danube") == 1) {
            danube = new Texture("dotlocked.png");
        } else {
            danube = new Texture("udanube.png");
        }

        if (pref.getInteger("antimatter") == 1) {
            antimatter = new Texture("dotlocked.png");
        } else {
            antimatter = new Texture("uantimatter.png");
        }

        bg2 = new Texture("bg2.png");
        back = new Texture("back.png");
    }

    public void setSprite() {
        limes = new Sprite(lime);
        dots[0] = limes;
        aquamarines = new Sprite(aquamarine);
        dots[1] = aquamarines;
        fires =  new Sprite(fire);
        dots[2] = fires;
        royals = new Sprite(royal);
        dots[3] = royals;
        flamingos = new Sprite(flamingo);
        dots[4] = flamingos;
        hotrods = new Sprite(hotrod);
        dots[5] = hotrods;
        lilacs = new Sprite(lilac);
        dots[6] = lilacs;
        lemons = new Sprite(lemon);
        dots[7] = lemons;
        snows = new Sprite(snow);
        dots[8] = snows;
        emeralds = new Sprite(emerald);
        dots[9] = emeralds;
        chestnuts = new Sprite(chestnut);
        dots[10] = chestnuts;
        sharks = new Sprite(shark);
        dots[11] = sharks;
        midnights = new Sprite(midnight);
        dots[12] = midnights;
        danubes = new Sprite(danube);
        dots[13] = danubes;
        antimatters = new Sprite(antimatter);
        dots[14] = antimatters;

        bg2S = new Sprite(bg2);
        backS = new Sprite(back);

//        for (int i = 0; i < 15; i++) {
//            if (i != 1) {
//                dots[i].setColor(dots[i].getColor().r, dots[i].getColor().g, dots[i].getColor().b, 0);
//            }
//        }
    }

    public void setSpriteAlpha() {
        limes.setAlpha(0);
        aquamarines.setAlpha(1);
        fires.setAlpha(0);
        royals.setAlpha(0);
        flamingos.setAlpha(0);
        hotrods.setAlpha(0);
        lilacs.setAlpha(0);
        lemons.setAlpha(0);
        snows.setAlpha(0);
        emeralds.setAlpha(0);
        chestnuts.setAlpha(0);
        sharks.setAlpha(0);
        midnights.setAlpha(0);
        danubes.setAlpha(0);
        antimatters.setAlpha(0);

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

        bg2S.setAlpha(0);
        backS.setAlpha(0);
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

        backS.setPosition(DotDrop.WIDTH / 2 - backS.getWidth() / 2, 1720 + y);
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

        backS.setPosition(DotDrop.WIDTH / 2 - backS.getWidth() / 2, 1720 + y);
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

    public Sprite getBg2S() {
        return bg2S;
    }

    public Sprite getBackS() {
        return backS;
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
            font.draw(sb, limetxt, limes.getX() + (limes.getWidth() / 2) - limeWidth / 2,
                    limes.getY() + (limes.getHeight() / 2) - 113 - 20 - limeHeight / 2);
        } else {
            font2.draw(sb, limetxt, limes.getX() + (limes.getWidth() / 2) - limeWidth / 2,
                    limes.getY() + (limes.getHeight() / 2) - 113 - 20 - limeHeight / 2);
        }

        if (pref.getInteger("aquamarine") == 3) {
            font.draw(sb, aquamarinetxt, aquamarines.getX() + (aquamarines.getWidth() / 2) - aquamarineWidth / 2,
                    aquamarines.getY() + (aquamarines.getHeight() / 2) - 113 - 20 - aquamarineHeight / 2);
        } else {
            font2.draw(sb, aquamarinetxt, aquamarines.getX() + (aquamarines.getWidth() / 2) - aquamarineWidth / 2,
                    aquamarines.getY() + (aquamarines.getHeight() / 2) - 113 - 20 - aquamarineHeight / 2);
        }

        if (pref.getInteger("fire") == 3) {
            font.draw(sb, firetxt, fires.getX() + (fires.getWidth() / 2) - fireWidth / 2,
                    fires.getY() + (fires.getHeight() / 2) - 113 - 20 - fireHeight / 2);
        } else {
            font2.draw(sb, firetxt, fires.getX() + (fires.getWidth() / 2) - fireWidth / 2,
                    fires.getY() + (fires.getHeight() / 2) - 113 - 20 - fireHeight / 2);
        }

        if (pref.getInteger("royal") == 3) {
            font.draw(sb, royaltxt, royals.getX() + (royals.getWidth() / 2) - royalWidth / 2,
                    royals.getY() + (royals.getHeight() / 2) - 113 - 20 - royalHeight / 2);
        } else {
            font2.draw(sb, royaltxt, royals.getX() + (royals.getWidth() / 2) - royalWidth / 2,
                    royals.getY() + (royals.getHeight() / 2) - 113 - 20 - royalHeight / 2);
        }

        if (pref.getInteger("flamingo") == 3) {
            font.draw(sb, flamingotxt, flamingos.getX() + (flamingos.getWidth() / 2) - flamingoWidth / 2,
                    flamingos.getY() + (flamingos.getHeight() / 2) - 113 - 20 - flamingoHeight / 2);
        } else {
            font2.draw(sb, flamingotxt, flamingos.getX() + (flamingos.getWidth() / 2) - flamingoWidth / 2,
                    flamingos.getY() + (flamingos.getHeight() / 2) - 113 - 20 - flamingoHeight / 2);
        }

        if (pref.getInteger("hotrod") == 1 || pref.getInteger("hotrod") == 2) {
            font2.draw(sb, hotrodtxt, hotrods.getX() + (hotrods.getWidth() / 2) - hotrodWidth / 2,
                    hotrods.getY() + (hotrods.getHeight() / 2) - 113 - 20 - hotrodHeight / 2);
        } else {
            font.draw(sb, hotrodtxt, hotrods.getX() + (hotrods.getWidth() / 2) - hotrodWidth / 2,
                    hotrods.getY() + (hotrods.getHeight() / 2) - 113 - 20 - hotrodHeight / 2);
        }

        if (pref.getInteger("lilac") == 1 || pref.getInteger("lilac") == 2) {
            font2.draw(sb, lilactxt, lilacs.getX() + (lilacs.getWidth() / 2) - lilacWidth / 2,
                    lilacs.getY() + (lilacs.getHeight() / 2) - 113 - 20 - lilacHeight / 2);
        } else {
            font.draw(sb, lilactxt, lilacs.getX() + (lilacs.getWidth() / 2) - lilacWidth / 2,
                    lilacs.getY() + (lilacs.getHeight() / 2) - 113 - 20 - lilacHeight / 2);
        }

        if (pref.getInteger("lemon") == 1 || pref.getInteger("lemon") == 2) {
            font2.draw(sb, lemontxt, lemons.getX() + (lemons.getWidth() / 2) - lemonWidth / 2,
                    lemons.getY() + (lemons.getHeight() / 2) - 113 - 20 - lemonHeight / 2);
        } else {
            font.draw(sb, lemontxt, lemons.getX() + (lemons.getWidth() / 2) - lemonWidth / 2,
                    lemons.getY() + (lemons.getHeight() / 2) - 113 - 20 - lemonHeight / 2);
        }

        if (pref.getInteger("snow") == 1 || pref.getInteger("snow") == 2) {
            font2.draw(sb, snowtxt, snows.getX() + (snows.getWidth() / 2) - snowWidth / 2,
                    snows.getY() + (snows.getHeight() / 2) - 113 - 20 - snowHeight / 2);
        } else {
            font.draw(sb, snowtxt, snows.getX() + (snows.getWidth() / 2) - snowWidth / 2,
                    snows.getY() + (snows.getHeight() / 2) - 113 - 20 - snowHeight / 2);
        }

        if (pref.getInteger("emerald") == 1 || pref.getInteger("emerald") == 2) {
            font2.draw(sb, emeraldtxt, emeralds.getX() + (emeralds.getWidth() / 2) - emeraldWidth / 2,
                    emeralds.getY() + (emeralds.getHeight() / 2) - 113 - 20 - emeraldHeight / 2);
        } else {
            font.draw(sb, emeraldtxt, emeralds.getX() + (emeralds.getWidth() / 2) - emeraldWidth / 2,
                    emeralds.getY() + (emeralds.getHeight() / 2) - 113 - 20 - emeraldHeight / 2);
        }

        if (pref.getInteger("chestnut") == 1 || pref.getInteger("chestnut") == 2) {
            font2.draw(sb, chestnuttxt, chestnuts.getX() + (chestnuts.getWidth() / 2) - chestnutWidth / 2,
                    chestnuts.getY() + (chestnuts.getHeight() / 2) - 113 - 20 - chestnutHeight / 2);
        } else {
            font.draw(sb, chestnuttxt, chestnuts.getX() + (chestnuts.getWidth() / 2) - chestnutWidth / 2,
                    chestnuts.getY() + (chestnuts.getHeight() / 2) - 113 - 20 - chestnutHeight / 2);
        }

        if (pref.getInteger("shark") == 1 || pref.getInteger("shark") == 2) {
            font2.draw(sb, sharktxt, sharks.getX() + (sharks.getWidth() / 2) - sharkWidth / 2,
                    sharks.getY() + (sharks.getHeight() / 2) - 113 - 20 - sharkHeight / 2);
        } else {
            font.draw(sb, sharktxt, sharks.getX() + (sharks.getWidth() / 2) - sharkWidth / 2,
                    sharks.getY() + (sharks.getHeight() / 2) - 113 - 20 - sharkHeight / 2);
        }

        if (pref.getInteger("midnight") == 1 || pref.getInteger("midnight") == 2) {
            font2.draw(sb, midnighttxt, midnights.getX() + (midnights.getWidth() / 2) - midnightWidth / 2,
                    midnights.getY() + (midnights.getHeight() / 2) - 113 - 20 - midnightHeight / 2);
        } else {
            font.draw(sb, midnighttxt, midnights.getX() + (midnights.getWidth() / 2) - midnightWidth / 2,
                    midnights.getY() + (midnights.getHeight() / 2) - 113 - 20 - midnightHeight / 2);
        }

        if (pref.getInteger("danube") == 1 || pref.getInteger("danube") == 2) {
            font2.draw(sb, danubetxt, danubes.getX() + (danubes.getWidth() / 2) - danubeWidth / 2,
                    danubes.getY() + (danubes.getHeight() / 2) - 113 - 20 - danubeHeight / 2);
        } else {
            font.draw(sb, danubetxt, danubes.getX() + (danubes.getWidth() / 2) - danubeWidth / 2,
                    danubes.getY() + (danubes.getHeight() / 2) - 113 - 20 - danubeHeight / 2);
        }

        if (pref.getInteger("antimatter") == 1 || pref.getInteger("antimatter") == 2) {
            font2.draw(sb, antimattertxt, antimatters.getX() + (antimatters.getWidth() / 2) - antimatterWidth / 2,
                    antimatters.getY() + (antimatters.getHeight() / 2) - 113 - 20 - antimatterHeight / 2);
        } else {
            font.draw(sb, antimattertxt, antimatters.getX() + (antimatters.getWidth() / 2) - antimatterWidth / 2,
                    antimatters.getY() + (antimatters.getHeight() / 2) - 113 - 20 - antimatterHeight / 2);
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
            limes.setAlpha(a);
        } else {
            limes.setAlpha(a / 4);
        }

        if (pref.getInteger("aquamarine") == 3) {
            aquamarines.setAlpha(1);
        } else {
            aquamarines.setAlpha(aAquamarine);
        }

        if (pref.getInteger("fire") == 1 || pref.getInteger("fire") == 3) {
            fires.setAlpha(a);
        } else {
            fires.setAlpha(a / 4);
        }

        if (pref.getInteger("royal") == 1 || pref.getInteger("royal") == 3) {
            royals.setAlpha(a);
        } else {
            royals.setAlpha(a / 4);
        }

        if (pref.getInteger("flamingo") == 1 || pref.getInteger("flamingo") == 3) {
            flamingos.setAlpha(a);
        } else {
            flamingos.setAlpha(a / 4);
        }

        if (pref.getInteger("hotrod") == 1 || pref.getInteger("hotrod") == 3) {
            hotrods.setAlpha(a);
        } else {
            hotrods.setAlpha(a / 4);
        }

        if (pref.getInteger("lilac") == 1 || pref.getInteger("lilac") == 3) {
            lilacs.setAlpha(a);
        } else {
            lilacs.setAlpha(a / 4);
        }

        if (pref.getInteger("lemon") == 1 || pref.getInteger("lemon") == 3) {
            lemons.setAlpha(a);
        } else {
            lemons.setAlpha(a / 4);
        }

        if (pref.getInteger("snow") == 1 || pref.getInteger("snow") == 3) {
            snows.setAlpha(a);
        } else {
            snows.setAlpha(a / 4);
        }

        if (pref.getInteger("emerald") == 1 || pref.getInteger("emerald") == 3) {
            emeralds.setAlpha(a);
        } else {
            emeralds.setAlpha(a / 4);
        }

        if (pref.getInteger("chestnut") == 1 || pref.getInteger("chestnut") == 3) {
            chestnuts.setAlpha(a);
        } else {
            chestnuts.setAlpha(a / 4);
        }

        if (pref.getInteger("shark") == 1 || pref.getInteger("shark") == 3) {
            sharks.setAlpha(a);
        } else {
            sharks.setAlpha(a / 4);
        }

        if (pref.getInteger("midnight") == 1 || pref.getInteger("midnight") == 3) {
            midnights.setAlpha(a);
        } else {
            midnights.setAlpha(a / 4);
        }

        if (pref.getInteger("danube") == 1 || pref.getInteger("danube") == 3) {
            danubes.setAlpha(a);
        } else {
            danubes.setAlpha(a / 4);
        }

        if (pref.getInteger("antimatter") == 1 || pref.getInteger("antimatter") == 3) {
            antimatters.setAlpha(a);
        } else {
            antimatters.setAlpha(a / 4);
        }

        backS.setAlpha(a);

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
            limes.setAlpha(a);
        } else {
            limes.setAlpha(a / 4);
        }

        if (pref.getInteger("aquamarine") == 2) {
            aquamarines.setAlpha(aAquamarine);
        }

        if (pref.getInteger("fire") == 1 || pref.getInteger("fire") == 3) {
            fires.setAlpha(a);
        } else {
            fires.setAlpha(a / 4);
        }

        if (pref.getInteger("royal") == 1 || pref.getInteger("royal") == 3) {
            royals.setAlpha(a);
        } else {
            royals.setAlpha(a / 4);
        }

        if (pref.getInteger("flamingo") == 1 || pref.getInteger("flamingo") == 3) {
            flamingos.setAlpha(a);
        } else {
            flamingos.setAlpha(a / 4);
        }

        if (pref.getInteger("hotrod") == 1 || pref.getInteger("hotrod") == 3) {
            hotrods.setAlpha(a);
        } else {
            hotrods.setAlpha(a / 4);
        }

        if (pref.getInteger("lilac") == 1 || pref.getInteger("lilac") == 3) {
            lilacs.setAlpha(a);
        } else {
            lilacs.setAlpha(a / 4);
        }

        if (pref.getInteger("lemon") == 1 || pref.getInteger("lemon") == 3) {
            lemons.setAlpha(a);
        } else {
            lemons.setAlpha(a / 4);
        }

        if (pref.getInteger("snow") == 1 || pref.getInteger("snow") == 3) {
            snows.setAlpha(a);
        } else {
            snows.setAlpha(a / 4);
        }

        if (pref.getInteger("emerald") == 1 || pref.getInteger("emerald") == 3) {
            emeralds.setAlpha(a);
        } else {
            emeralds.setAlpha(a / 4);
        }

        if (pref.getInteger("chestnut") == 1 || pref.getInteger("chestnut") == 3) {
            chestnuts.setAlpha(a);
        } else {
            chestnuts.setAlpha(a / 4);
        }

        if (pref.getInteger("shark") == 1 || pref.getInteger("shark") == 3) {
            sharks.setAlpha(a);
        } else {
            sharks.setAlpha(a / 4);
        }

        if (pref.getInteger("midnight") == 1 || pref.getInteger("midnight") == 3) {
            midnights.setAlpha(a);
        } else {
            midnights.setAlpha(a / 4);
        }

        if (pref.getInteger("danube") == 1 || pref.getInteger("danube") == 3) {
            danubes.setAlpha(a);
        } else {
            danubes.setAlpha(a / 4);
        }

        if (pref.getInteger("antimatter") == 1 || pref.getInteger("antimatter") == 3) {
            antimatters.setAlpha(a);
        } else {
            antimatters.setAlpha(a / 4);
        }

        backS.setAlpha(a);

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

//    public void fadeInSelect(int i) {
//        float alpha = a / 4;
//        if (alpha < a) {
//            alpha += 0.075;
//        } else {
//            alpha = a;
//        }
//        dots[i].setAlpha(alpha);
//    }
//
//    public void fadeOutSelect(int i) {
//        float alpha = a;
//        if (alpha > a / 4) {
//            alpha -= 0.075;
//        } else {
//            alpha = a / 4;
//        }
//        dots[i].setAlpha(alpha);
//    }

    private void bg2sFadeIn() {
        aBg2s += 0.01;
        if (a >= 1) {
            a = 1;
        }
        bg2S.setAlpha(a);
    }

    private void bg2sFadeOut() {
        aBg2s -= 0.075;
        if (a <= 0) {
            a = 0;
        }
        bg2S.setAlpha(a);
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
//        if (pref.getInteger("lime") == 3) {
//            pref.putInteger("lime", 2);
//        } else if (pref.getInteger("lime") == 2) {
//            pref.putInteger("lime", 3);
//        }
//
//        if (pref.getInteger("aquamarine") == 3) {
//            pref.putInteger("aquamarine", 2);
//        } else if (pref.getInteger("aquamarine") == 2) {
//            pref.putInteger("aquamarine", 3);
//        }
//
//        if (pref.getInteger("fire") == 3) {
//            pref.putInteger("fire", 2);
//        } else if (pref.getInteger("fire") == 2) {
//            pref.putInteger("fire", 3);
//        }
//
//        if (pref.getInteger("royal") == 3) {
//            pref.putInteger("royal", 2);
//        } else if (pref.getInteger("royal") == 2) {
//            pref.putInteger("royal", 3);
//        }
//
//        if (pref.getInteger("flamingo") == 3) {
//            pref.putInteger("flamingo", 2);
//        } else if (pref.getInteger("flamingo") == 2) {
//            pref.putInteger("flamingo", 3);
//        }
//
//        if (pref.getInteger("hotrod") == 3) {
//            pref.putInteger("hotrod", 2);
//        } else if (pref.getInteger("hotrod") == 2) {
//            pref.putInteger("hotrod", 3);
//        }
//
//        if (pref.getInteger("lilac") == 3) {
//            pref.putInteger("lilac", 2);
//        } else if (pref.getInteger("lilac") == 2) {
//            pref.putInteger("lilac", 3);
//        }
//
//        if (pref.getInteger("lemon") == 3) {
//            pref.putInteger("lemon", 2);
//        } else if (pref.getInteger("lemon") == 2) {
//            pref.putInteger("lemon", 3);
//        }
//
//        if (pref.getInteger("snow") == 3) {
//            pref.putInteger("snow", 2);
//        } else if (pref.getInteger("snow") == 2) {
//            pref.putInteger("snow", 3);
//        }
//
//        if (pref.getInteger("emerald") == 3) {
//            pref.putInteger("emerald", 2);
//        } else if (pref.getInteger("emerald") == 2) {
//            pref.putInteger("emerald", 3);
//        }
//
//        if (pref.getInteger("chestnut") == 3) {
//            pref.putInteger("chestnut", 2);
//        } else if (pref.getInteger("chestnut") == 2) {
//            pref.putInteger("chestnut", 3);
//        }
//
//        if (pref.getInteger("shark") == 3) {
//            pref.putInteger("shark", 2);
//        } else if (pref.getInteger("shark") == 2) {
//            pref.putInteger("shark", 3);
//        }
//
//        if (pref.getInteger("midnight") == 3) {
//            pref.putInteger("midnight", 2);
//        } else if (pref.getInteger("midnight") == 2) {
//            pref.putInteger("midnight", 3);
//        }
//
//        if (pref.getInteger("danube") == 3) {
//            pref.putInteger("danube", 2);
//        } else if (pref.getInteger("danube") == 2) {
//            pref.putInteger("danube", 3);
//        }
//
//        if (pref.getInteger("antimatter") == 3) {
//            pref.putInteger("antimatter", 2);
//        } else if (pref.getInteger("antimatter") == 2) {
//            pref.putInteger("antimatter", 3);
//        }
    }

    public void dispose() {
        lime.dispose();
        aquamarine.dispose();
        fire.dispose();
        royal.dispose();
        flamingo.dispose();
        hotrod.dispose();
        lilac.dispose();
        lemon.dispose();
        snow.dispose();
        emerald.dispose();
        chestnut.dispose();
        shark.dispose();
        midnight.dispose();
        danube.dispose();
        antimatter.dispose();
        bg2.dispose();
        back.dispose();
        font.dispose();
        font2.dispose();
        fontWarning.dispose();
    }
}