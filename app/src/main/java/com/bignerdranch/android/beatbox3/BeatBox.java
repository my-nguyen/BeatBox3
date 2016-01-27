package com.bignerdranch.android.beatbox3;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by My on 1/27/2016.
 */
public class BeatBox {
   private static final String   TAG = "BeatBox";
   private static final String   SOUNDS_FOLDER = "sample_sounds";
   // resources can store sounds. you can do all the usual resource things, like having different
   // sounds for different orientations, languages, versions of Android, etc. however, BeatBox has
   // a lot of sounds: 22 files. resources do not let you just ship them all out in one big folder,
   // nor do they allow you to give your resources anything other than a totally flat structure.
   // assets are like a little file system that ships with your packaged application. you can use
   // whatever folder structure you want. assets are commonly used for loading graphics and sound in
   // applications that have a lot of those things, like games.
   private AssetManager mAssets;
   private List<Sound>  mSounds = new ArrayList<>();

   public BeatBox(Context context) {
      // save a copy of the AssetManager
      mAssets = context.getAssets();
      loadSounds();
   }

   public List<Sound> getSounds() {
      return mSounds;
   }

   private void loadSounds() {
      String[] soundNames;
      try {
         // list all filenames contained in the SOUNDS_FOLDER
         soundNames = mAssets.list(SOUNDS_FOLDER);
         Log.i(TAG, "Found " + soundNames.length + " sounds");
      }
      catch (IOException ioe) {
         Log.d(TAG, "Could not list assets", ioe);
         return;
      }

      for (String filename : soundNames) {
         String assetPath = SOUNDS_FOLDER + "/" + filename;
         Sound sound = new Sound(assetPath);
         mSounds.add(sound);
      }
   }
}
