package jp.palamfairy.project.android.palamfairygame;

import android.media.AudioManager;
import android.media.Image;
import android.media.MediaActionSound;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.opengl.Matrix;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.os.Handler;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mBgm;
    private MediaPlayer mOnara;
    private ImageView petImage;
    private MediaPlayer OnigiriPlay;
    private Button foodItem;
    private Button handItem;
    private Button cleanItem;
    private boolean showItem;
    private boolean wantShow;
    private ToileRoop toileRoop;
    private AnimationSet OnigiriSet;
    private TranslateAnimation OnigiriTrans;
    private AlphaAnimation OnigiriAlfa;
    //    private RelativeLayout mParentLayout;
//    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener;
    private Button toileA;
    private Button toileB;
    private Button toileC;
    private Button toileD;

    private ImageView OnigiriView;
    private RelativeLayout relativelayout;
    private LinearLayout layout;
    private ImageView toileImg;

    //    Handler mhandler= new Handler();
    private int random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        トイレのビュー取得
        toileA = (Button)findViewById(R.id.toileA);
        toileB = (Button)findViewById(R.id.toileB);
        toileC = (Button)findViewById(R.id.toileC);
        toileD = (Button)findViewById(R.id.toileD);
        //        トイレの表示
        toileA.setVisibility(View.INVISIBLE);
        toileB.setVisibility(View.INVISIBLE);
        toileC.setVisibility(View.INVISIBLE);
        toileD.setVisibility(View.INVISIBLE);

        toileRoop = new ToileRoop();

        foodItem = (Button)findViewById(R.id.foodItem);
        handItem = (Button)findViewById(R.id.handItem);
        cleanItem = (Button)findViewById(R.id.cleanItem);
        foodItem.setVisibility(View.INVISIBLE);
        handItem.setVisibility(View.INVISIBLE);
        cleanItem.setVisibility(View.INVISIBLE);
        setShowItem(false);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        relativelayout = (RelativeLayout)findViewById(R.id.petcontainer);
//        setContentView(relativelayout);
//        layout = new LinearLayout(getApplicationContext());

//        ごはんアイテム
        OnigiriView = (ImageView)findViewById(R.id.OnigiriView);
        OnigiriView.setVisibility(View.INVISIBLE);
        foodItem.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                OnigiriView.setVisibility(View.VISIBLE);
                OnigiriSet = new AnimationSet(true);
                OnigiriTrans = new TranslateAnimation(
                        TranslateAnimation.RELATIVE_TO_SELF,0,
                        TranslateAnimation.RELATIVE_TO_SELF,0,
                        TranslateAnimation.RELATIVE_TO_SELF,0,
                        TranslateAnimation.RELATIVE_TO_SELF,3
                );
                OnigiriSet.addAnimation(OnigiriTrans);

                OnigiriSound();
                OnigiriAlfa = new AlphaAnimation(1,0);
                OnigiriSet.addAnimation(OnigiriAlfa);
                OnigiriSet.setDuration(5000);
                OnigiriView.startAnimation(OnigiriSet);
            }
        });
//        ボタン
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wantShow = getShowItem();
                if(wantShow) {
                    foodItem.setVisibility(View.INVISIBLE);
                    handItem.setVisibility(View.INVISIBLE);
                    cleanItem.setVisibility(View.INVISIBLE);
                    setShowItem(false);
                }else{
                    foodItem.setVisibility(View.VISIBLE);
                    handItem.setVisibility(View.VISIBLE);
                    cleanItem.setVisibility(View.VISIBLE);
                    setShowItem(true);
                }
            }
        });
    }

    private void OnigiriSound(){
        OnigiriPlay = new MediaPlayer();
        OnigiriPlay.create(this,R.raw.onigiri);
        OnigiriPlay.start();
    }


    @Override
    protected void onStart() {
        super.onStart();
        sound();
//        AnimationPet mAnimationPet = new AnimationPet();
//        mAnimationPet.petSlide(petImage);
        petImage = (ImageView) findViewById(R.id.petViewImage);
        TranslateAnimation petMoveRight = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, -1,
                TranslateAnimation.RELATIVE_TO_SELF, 1,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0
        );
        petMoveRight.setDuration(5000);
        petMoveRight.setRepeatMode(Animation.REVERSE);
        petMoveRight.setRepeatCount(Animation.INFINITE);
        petImage.startAnimation(petMoveRight);
        toile();
    }
    private void setShowItem(boolean isShow){
        showItem = isShow;
    }
    private boolean getShowItem(){
        return showItem;
    }
    public void sound() {
        mBgm = MediaPlayer.create(this, R.raw.bgm);
        mBgm.setLooping(true);
        mBgm.setVolume(0.3f,0.3f);
        mBgm.start();
    }
    private void toileSound(){
        mOnara = MediaPlayer.create(this,R.raw.onara);
        mOnara.start();
    }

//    ★
    private void toile(){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Random randomInt = new Random();
                random = randomInt.nextInt(600000)+60000;
                Log.d("hatena","実験");
//                トイレを4回呼びたい。
                if(toileRoop.roop == 1){
                    toileSound();
                    toileA.setVisibility(View.VISIBLE);
                }else if(toileRoop.roop == 2){
                    toileSound();
                    toileB.setVisibility(View.VISIBLE);
                }else if(toileRoop.roop == 3){
                    toileSound();
                    toileC.setVisibility(View.VISIBLE);
                }else if(toileRoop.roop == 4){
                    toileSound();
                    toileD.setVisibility(View.VISIBLE);
                }
                toile();
                toileRoop.roop +=1;
                if(toileRoop.roop >4) {
                    toileRoop.roop = 0;
                }
            }
        },random);
    }
}
