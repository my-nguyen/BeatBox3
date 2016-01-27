package com.bignerdranch.android.beatbox3;

/**
 * Created by My on 1/27/2016.
 */
// this class keeps track of the sound filename, the name displayed to the user, and any information
// related to a sound.
public class Sound {
   // full path to the sound file
   private String mAssetPath;
   // the sound name to display to the user
   private String mName;

   public Sound(String assetPath) {
      mAssetPath = assetPath;
      // extract filename, without the full path
      String[] components = assetPath.split("/");
      String filename = components[components.length - 1];
      // remove the ".wav" extension
      mName = filename.replace(".wav", "");
   }

   public String getAssetPath() {
      return mAssetPath;
   }

   public String getName() {
      return mName;
   }
}
