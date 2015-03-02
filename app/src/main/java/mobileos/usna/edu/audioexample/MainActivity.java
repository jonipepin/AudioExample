package mobileos.usna.edu.audioexample;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, SoundPool.OnLoadCompleteListener {

    MediaPlayer mediaPlayer;
    
    SoundPool mSoundPool;
    Button soundButton1, soundButton2, soundButton3, soundButton4;
    int sound1, sound2, sound3, sound4;
    int numSoundsLoaded = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // instantiate buttons for SoundPool example
        soundButton1 = (Button) findViewById(R.id.soundButton1);
        soundButton2 = (Button) findViewById(R.id.soundButton2);
        soundButton3 = (Button) findViewById(R.id.soundButton3);
        soundButton4 = (Button) findViewById(R.id.soundButton4);
        
        // set onClick listener for each button
        soundButton1.setOnClickListener(this);
        soundButton2.setOnClickListener(this);
        soundButton3.setOnClickListener(this);
        soundButton4.setOnClickListener(this);
        
    }

    @Override
    public void onStart(){
        super.onStart();
        
        // Create and prepare MediaPlayer
        initializePlayer();
        
        // create SoundPool
        mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        
        // the way to create a SoundPool with API 21 and up:
//        SoundPool.Builder spb = new SoundPool.Builder();
//        spb.setMaxStreams(4);
//        SoundPool mSoundPool = spb.build();

        // use onLoadComplete Listener implemented by Activity
        mSoundPool.setOnLoadCompleteListener(this);
        
        // load sound files into SoundPool using res > raw id
        // parameters: (context, file_id, priority)
        sound1 = mSoundPool.load(this, R.raw.wilhelm_scream, 1);
        sound2 = mSoundPool.load(this, R.raw.boing, 1);
        sound3 = mSoundPool.load(this, R.raw.blast, 1);
        sound4 = mSoundPool.load(this, R.raw.wand, 1);
        
    }

    @Override
    public void onStop(){
        super.onStop();
        
        if(mediaPlayer != null) {
            destroyPlayer();
        }

        if(mSoundPool != null) {
            mSoundPool.release();
            mSoundPool = null;
        }
    }

    @Override
    public void onClick(View v){
        if(numSoundsLoaded == 4){
            if(v == soundButton1){
                mSoundPool.play(sound1, 1, 1, 1, 1, 1);
            } else if(v == soundButton2){
                mSoundPool.play(sound2, 1, 1, 1, 1, 1);
            } else if(v == soundButton3){
                mSoundPool.play(sound3, 1, 1, 1, 1, 1);
            } else if(v == soundButton4){
                mSoundPool.play(sound4, 1, 1, 1, 1, 1);
            }
        }
        
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        // let us know that a sound has been loaded by the SoundPool
        numSoundsLoaded++;
        Toast.makeText(this, "Sound " + numSoundsLoaded + " Loaded", Toast.LENGTH_SHORT).show();
    }

    private void initializePlayer(){
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(getBaseContext(),
                    R.raw.sound_file_1);
        } 
    }

    private void destroyPlayer(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void playPlayer(View view){
        initializePlayer();
        mediaPlayer.start();
    }

    public void pausePlayer(View view){
        if(mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void stopPlayer(View view){
        if(mediaPlayer != null) {
            destroyPlayer();
        }
    }

}
