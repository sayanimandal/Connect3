package com.sayanimandal.connect3;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import com.sayanimandal.connect3.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    InterstitialAd mInterstitialAd;

    int player =0; //red
    int[] gamestates = {2,2,2,2,2,2,2,2,2}; // positions unplayed
    int[][] winningsets= {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameActive = true;
    int totCount = 0;
    boolean draw= true;
    ActivityMainBinding binding;
    int red_Win = 0;
    int yellow_Win = 0;
    private MediaPlayer mPlayer;
    boolean playing = true;

    public void clicked (View view) {
        ImageView imageView = (ImageView) view; //the current view clicked on
        //ImageView playerTurn = (ImageView) findViewById(R.id.playerTurn);
        binding.playerTurn.setImageResource(R.drawable.red_green);
            int tappedCounter = Integer.parseInt(imageView.getTag().toString()); //position of current view
            if (gamestates[tappedCounter] == 2 && gameActive) {
                //gamestates[tappedCounter] = 1; // position played
                totCount++;
                imageView.animate().alpha(1f);
                imageView.setTranslationY(300);
                if (player == 0) {
                    imageView.setImageResource(R.drawable.red);
                    player = 1;
                    gamestates[tappedCounter] = 1; // position played
                    binding.playerTurn.setImageResource(R.drawable.yellow_green);
                } else {
                    imageView.setImageResource(R.drawable.yellow);
                    player = 0;
                    gamestates[tappedCounter] = 3; // position played
                    binding.playerTurn.setImageResource(R.drawable.red_green);
                }
                imageView.animate().translationYBy(-300f).rotation(360).setDuration(300);
                //if someone wins
                    for (int[] set : winningsets) {
                        if (gamestates[set[0]] == gamestates[set[1]]
                                && gamestates[set[1]] == gamestates[set[2]]
                                && gamestates[set[0]] != 2) {
                            //LinearLayout layout1 = (LinearLayout) findViewById(R.id.layout1);
                            //TextView welcomemsg = (TextView) findViewById(R.id.welcomemsg);
                            GridLayout gridLo = (GridLayout) findViewById(R.id.gridLayout);
                            ((ImageView) gridLo.getChildAt(set[0])).animate().rotation(360f).setDuration(1000);
                            ((ImageView) gridLo.getChildAt(set[1])).animate().rotation(360f).setDuration(1000);
                            ((ImageView) gridLo.getChildAt(set[2])).animate().rotation(360f).setDuration(1000);
                            if (player == 1) {
                                draw = false;
                                binding.welcomemsg.setText("Red has Won!");
                                red_Win++;
                                //welcomemsg.setTextColor(111);
                                binding.gridLayout.setAlpha(.5f);
                            } else {
                                draw = false;
                                binding.welcomemsg.setText("Yellow has Won!");
                                //welcomemsg.setTextColor(222);
                                yellow_Win++;
                                binding.gridLayout.setAlpha(.5f);
                            }
                            binding.upperLayout.setVisibility(View.VISIBLE);
                            binding.resetLayout.setVisibility(View.INVISIBLE);
                            gameActive = false;
                        }
                    }
                if (totCount == 9 && draw){
                    //LinearLayout lay1 = (LinearLayout) findViewById(R.id.layout1);
                    //TextView welcomemsg1 = (TextView) findViewById(R.id.welcomemsg);
                    binding.welcomemsg.setText("It's a draw!");
                    binding.resetLayout.setVisibility(View.INVISIBLE);
                    binding.upperLayout.setVisibility(View.VISIBLE);
                }
            }
            binding.redWin.setText("     Red Wins: " + red_Win);
            binding.yellowWin.setText("     Yellow Wins: " + yellow_Win);
        }

    public void playAgain(View view) {
        //LinearLayout layout1 = (LinearLayout) findViewById(R.id.layout1);
        binding.gridLayout.setAlpha(1f);
        binding.upperLayout.setVisibility(View.INVISIBLE);
        binding.resetLayout.setVisibility(View.VISIBLE);
            player = 0;
            totCount=0;
            draw = true;
        //GridLayout gridLayout = (GridLayout) findViewById(R.id.layout0);
        //ImageView playerTurn = findViewById(R.id.playerTurn);
        binding.playerTurn.setImageResource(R.drawable.red_green);
        for (int i = 0; i < binding.gridLayout.getChildCount(); i++) {
            gamestates[i] = 2;
            gameActive = true;
            ((ImageView) binding.gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    public void onExitGame(View view) {
        finish();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.musicPause.setBackgroundResource(R.drawable.stopplay);

        //banner ad
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //interstitial ad
        mInterstitialAd = new InterstitialAd(this);
        //replace adUnitId with your ad id from admob website
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mPlayer = MediaPlayer.create(this,R.raw.gamemenulooping);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                finish();
            }
        });

        mPlayer.start();
    }

    public void showInterstitial(){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {finish();}
    }

    @Override
    public void onBackPressed() {
        showInterstitial();  //show ad of app exit
    }

    public void pausePlaying(View view){
        if (playing) {
            mPlayer.pause();
            binding.musicPause.setBackgroundResource(R.drawable.stopplay);
            playing = false;
        } else {
            mPlayer.start();
            binding.musicPause.setBackgroundResource(R.drawable.playing);
            playing = true;
        }

    }
}
