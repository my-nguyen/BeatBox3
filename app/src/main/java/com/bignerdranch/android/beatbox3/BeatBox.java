package com.bignerdranch.android.beatbox3;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
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
   private static final int      MAX_SOUNDS = 5;
   // resources can store sounds. you can do all the usual resource things, like having different
   // sounds for different orientations, languages, versions of Android, etc. however, BeatBox has
   // a lot of sounds: 22 files. resources do not let you just ship them all out in one big folder,
   // nor do they allow you to give your resources anything other than a totally flat structure.
   // assets are like a little file system that ships with your packaged application. you can use
   // whatever folder structure you want. assets are commonly used for loading graphics and sound in
   // applications that have a lot of those things, like games.
   private AssetManager mAssets;
   // list of Sound objects
   private List<Sound>  mSounds = new ArrayList<>();
   // a SoundPool can load a large set of sounds into memory and control the maximum number of
   // sounds that are playing back at any one time. SoundPool is responsive: it will play a sound
   // immediately, but you must load sounds into SoundPool before playing.
   private SoundPool    mSoundPool;

   public BeatBox(Context context) {
      // save a copy of the AssetManager
      mAssets = context.getAssets();
      // this constructor is deprecated but we need it for compatibility
      // create a SoundPool which can play up to 5 different sounds at a time
      mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
      // load all sound files from SOUNDS_FOLDER into SoundPool
      loadSounds();
   }

   public List<Sound> getSounds() {
      return mSounds;
   }

   // this method plays a Sound object from the SoundPool
   public void play(Sound sound) {
      Integer soundId = sound.getSoundId();
      if (soundId != null)
         // SoundPool.play(sound-ID, left-volume, right-volume, priority, loop-or-not, playback-rate)
         mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
   }

   public void release() {
      mSoundPool.release();
   }

   // this method loads all sound files in the SOUNDS_FOLDER into the member SoundPool and retains
   // them in a member list of Sound objects.
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

      for (String filename : soundNames)
         try {
            // create a full path for each sound filename
            String assetPath = SOUNDS_FOLDER + "/" + filename;
            // create a Sound object with the full path
            Sound sound = new Sound(assetPath);
            // load the sound file into SoundPool
            load(sound);
            // save sound in member list of Sound objects
            mSounds.add(sound);
         }
         catch (IOException ioe) {
            Log.e(TAG, "Could not load sound " + filename, ioe);
         }
   }

   // this method loads a sound file into the member SoundPool for later playback
   private void load(Sound sound) throws IOException {
      AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
      int soundId = mSoundPool.load(afd, 1);
      sound.setSoundId(soundId);
   }
}
