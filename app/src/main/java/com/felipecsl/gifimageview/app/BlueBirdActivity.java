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

public class BlueBirdActivity extends AppCompatActivity  {
  private static final String TAG = "BirdAnimation";
  private GifImageView gifImageView;


  MediaPlayer mPlayer;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_blue_bird);

    gifImageView = (GifImageView) findViewById(R.id.gifImageView_bluebird);


    gifImageView.setOnAnimationStop(new GifImageView.OnAnimationStop() {
      @Override public void onAnimationStop() {
        runOnUiThread(new Runnable() {
          @Override public void run() {
            Toast.makeText(BlueBirdActivity.this, "Animation stopped", Toast.LENGTH_SHORT).show();
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
    }.execute("https://s3.ap-south-1.amazonaws.com/ngoprojectimages/LittleGreenFrog/BirdAnimation/birdy.gif");

  }


  @Override
  protected void onPause() {
    super.onPause();
    if (mPlayer != null) {
      mPlayer.stop();
      mPlayer.reset();
    }
  }


  void playSound() {
    if (mPlayer != null) {
      mPlayer.stop();
      mPlayer.reset();
    }

    mPlayer = MediaPlayer.create(BlueBirdActivity.this, R.raw.birdy);
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
    if (item.getItemId() == R.id.green_frog) {
      Intent intent = new Intent(BlueBirdActivity.this, MainActivity.class);
      startActivity(intent);
      finish();
      return true;
    }

    else if (item.getItemId() == R.id.white_cow) {

      Intent intent = new Intent(BlueBirdActivity.this, WhiteCowActivity.class);
      startActivity(intent);
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }


}
