package com.kevinli.dotdropgame;

/**
 * Created by Kevin on 8/28/2017.
 */

public interface ActionResolver {
    public void shareIntent();
    public void showInterstitialAd(Runnable then);
    public boolean isWifiConnected();
}
