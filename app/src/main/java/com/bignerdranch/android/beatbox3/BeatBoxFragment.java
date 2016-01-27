package com.bignerdranch.android.beatbox3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by My on 1/27/2016.
 */
public class BeatBoxFragment extends Fragment {
   private BeatBox   mBeatBox;

   public static BeatBoxFragment newInstance() {
      return new BeatBoxFragment();
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      // retain the BeatBoxFragment instance across rotation, so a sound in the middle of being
      // played won't be interrupted after rotation
      setRetainInstance(true);
      mBeatBox = new BeatBox(getActivity());
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_beat_box, container, false);
      RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.fragment_beat_box_recycler_view);
      // lay out items in a grid of 3 columns
      recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
      // set adapter as one built from a list of Sound objects created from the SOUNDS_FOLDER
      recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));

      return view;
   }

   @Override
   public void onDestroy() {
      super.onDestroy();
      mBeatBox.release();
   }

   private class SoundHolder extends RecyclerView.ViewHolder {
      private Button mButton;
      private Sound  mSound;

      public SoundHolder(LayoutInflater inflater, ViewGroup container) {
         super(inflater.inflate(R.layout.list_item_sound, container, false));
         mButton = (Button)itemView.findViewById(R.id.list_item_sound_button);
         mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // play the Sound object set up by SoundAdapter.onBindViewHolder()
               mBeatBox.play(mSound);
            }
         });
      }

      public void bindSound(Sound sound) {
         mSound = sound;
         // set the button text to the Sound's user-friendly name
         mButton.setText(mSound.getName());
      }
   }

   private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {
      private List<Sound>  mSounds;

      public SoundAdapter(List<Sound> sounds) {
         mSounds = sounds;
      }

      @Override
      public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         LayoutInflater inflater = LayoutInflater.from(getActivity());
         return new SoundHolder(inflater, parent);
      }

      @Override
      // this method binds the SoundHolder object at given position to the corresponding Sound object
      public void onBindViewHolder(SoundHolder holder, int position) {
         Sound sound = mSounds.get(position);
         holder.bindSound(sound);
      }

      @Override
      public int getItemCount() {
         return mSounds.size();
      }
   }
}
