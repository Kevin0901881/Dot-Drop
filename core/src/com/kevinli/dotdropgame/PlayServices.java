package com.kevinli.dotdropgame;

/**
 * Created by Kevin on 8/29/2017.
 */

public interface PlayServices {
    public void signIn();
    public void signOut();
    public void unlockAchievement();
    public void submitScore(int highScore);
    public void showAchievement();
    public void showScore();
    public boolean isSignedIn();
}
