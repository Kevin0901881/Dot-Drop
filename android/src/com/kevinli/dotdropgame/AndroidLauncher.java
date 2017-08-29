package com.kevinli.dotdropgame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;

public class AndroidLauncher extends AndroidApplication implements ActionResolver {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new com.kevinli.dotdropgame.DotDrop(this), config);
	}

	@Override
	public void shareIntent() {
		String path = new FileHandle(Gdx.files.getLocalStoragePath() + "screenshot.png").toString();
		File file = new File(path);

		String message = "Check out my score in Dot Drop! Think you can beat me? Download the game here: https://play.google.com/store/apps/details?id=com.kevinli.dotdropgame";
		Uri screenshotUri = Uri.fromFile(file);
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType("image/*");

		shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Dot Drop");
		shareIntent.putExtra(Intent.EXTRA_TEXT, message);
		shareIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
		shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

		startActivity(Intent.createChooser(shareIntent, "Share screenshot to:"));
	}
}
