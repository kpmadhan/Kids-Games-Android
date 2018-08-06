package com.felipecsl.gifimageview.app;

import android.content.Intent;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.felipecsl.gifimageview.library.GifImageView;

public class MainActivity extends AppCompatActivity  {
  private static final String TAG = "FrogAnimation";
  private GifImageView gifImageView;


  MediaPlayer mPlayer;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    gifImageView = (GifImageView) findViewById(R.id.gifImageView);


    gifImageView.setOnAnimationStop(new GifImageView.OnAnimationStop() {
      @Override public void onAnimationStop() {
        runOnUiThread(new Runnable() {
          @Override public void run() {
            Toast.makeText(MainActivity.this, "Animation stopped", Toast.LENGTH_SHORT).show();
          }
        });
      }
    });

    gifImageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (gifImageView.isAnimating()) {

          if (mPlayer != null) {
            mPlayer.pause();
          }
          gifImageView.stopAnimation();
        }
        else{
          gifImageView.startAnimation();
          if (mPlayer != null) {
            mPlayer.start();
          }

        }
      }
    });

    new GifDataDownloader() {
      @Override protected void onPostExecute(final byte[] bytes) {
        gifImageView.setBytes(bytes);
        gifImageView.startAnimation();
        playSound();
      }
    }.execute("https://s3.ap-south-1.amazonaws.com/ngoprojectimages/LittleGreenFrog/FrogAnimation/frogjump.gif");

  }

  @Override
  protected void onPause() {
    super.onPause();
    if (mPlayer != null) {
      mPlayer.stop();
      mPlayer = null;
    }
  }

  void playSound() {
    if (mPlayer != null) {
      mPlayer.stop();
      mPlayer.reset();
    }

    mPlayer = MediaPlayer.create(MainActivity.this, R.raw.frog);
    mPlayer.setLooping(true);
    mPlayer.start();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  public void onDestroy() {
    // Stop the sound
    if (mPlayer != null) {
      mPlayer.stop();
      mPlayer = null;
    }

    super.onDestroy();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (mPlayer != null) {
      mPlayer.stop();
      mPlayer = null;
    }
    if (item.getItemId() == R.id.blue_bird) {

      Intent intent = new Intent(MainActivity.this, BlueBirdActivity.class);
      startActivity(intent);
      finish();
      return true;
    }
   else if (item.getItemId() == R.id.white_cow) {

      Intent intent = new Intent(MainActivity.this, WhiteCowActivity.class);
      startActivity(intent);
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }


}
