package jp.palamfairy.project.android.palamfairygame;

import android.graphics.drawable.AnimationDrawable;
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

public class MainActivity extends AppCompatActivity{
    private MediaPlayer mBgm;
    private MediaPlayer mOnara;
    private ImageView petImage;
    private MediaPlayer OnigiriPlay;
    private Button foodItem;
    private Button handItem;
    private Button cleanItem;
    private AnimationDrawable dogSmileAnime;
    private AlphaAnimation cleanGoodsAlpha;
    private boolean showItem;
    private boolean wantShow;
    private ToileRoop toileRoop;
    private MediaPlayer untiplayer;
    private ImageView cleanUnti;
    private TranslateAnimation petMoveRight;
//    private TranslateAnimation OnigiriTrans;
    private AlphaAnimation OnigiriAlfa;
    private ImageView petFaceAnimeView;
//    private LinearLayout layoutClean;
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

        cleanUnti = (ImageView)findViewById(R.id.cleanGoods);
        cleanUnti.setVisibility(View.INVISIBLE);


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
//      ~~~~~~~~~~~~~~~~~~~~~~~~~~トイレ掃除クリックイベント~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        cleanItem.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                toileA.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        toileA.setVisibility(View.INVISIBLE);
                        int id = R.id.toileA;
                        cleanToile(id);
                    }
                });
                toileB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toileB.setVisibility(View.INVISIBLE);
                        int id = R.id.toileB;
                        cleanToile(id);
                    }
                });
                toileC.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        toileC.setVisibility(View.INVISIBLE);
                        int id = R.id.toileC;
                        cleanToile(id);
                    }
                });
                toileD.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        toileD.setVisibility(View.INVISIBLE);
                        int id = R.id.toileD;
                        cleanToile(id);
                    }
                });
            }
        });
//        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        ごはんアイテム
        OnigiriView = (ImageView)findViewById(R.id.OnigiriView);
        OnigiriView.setVisibility(View.INVISIBLE);
        foodItem.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                OnigiriAlfa = new AlphaAnimation(1,0);
                OnigiriAlfa.setDuration(4000);
                OnigiriView.startAnimation(OnigiriAlfa);
//                petImage.setVisibility(View.INVISIBLE);
                dogSmile();
            }
        });
//        ボタン
        Button fab = (Button) findViewById(R.id.fab);
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
        OnigiriPlay = MediaPlayer.create(this,R.raw.onigiri);
        OnigiriPlay.start();
    }

//    ~~~~~~~~~~~~~~~~~~~~ウンチを掃除するアニメーション~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private void cleanToile(int id){
        layout = new LinearLayout(this);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        param.addRule(RelativeLayout.ALIGN_LEFT,id);
        layout.addView(cleanUnti);
        relativelayout.removeAllViews();
        relativelayout.addView(layout,param);
        cleanGoodsAlpha = new AlphaAnimation(1,0);
        cleanGoodsAlpha.setDuration(2000);
        cleanUnti.startAnimation(cleanGoodsAlpha);

        untiplayer = MediaPlayer.create(this,R.raw.clean);
        untiplayer.start();
    }
//    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    ~~~~~~~~~~~~~~~~~~~~~~~~犬が食べるアニメーション~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private void dogSmile(){
        dogDefaultDelete();
        petFaceAnimeView = (ImageView)findViewById(R.id.petFaceAnime);
        petFaceAnimeView.setVisibility(View.VISIBLE);
        petFaceAnimeView.setBackgroundResource(R.drawable.dogface_animation);
        dogSmileAnime = (AnimationDrawable)petFaceAnimeView.getBackground();
        dogSmileAnime.start();
        OnigiriSound();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                petDefaultAnime();
                petFaceAnimeView.setVisibility(View.INVISIBLE);
                dogSmileAnime.stop();
            }
        },3000);
    }
//    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    //  ~~~~~~~~~~~~~~~~~~~~~~犬のデフォルトアニメーション~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private void petDefaultAnime(){
        petImage = (ImageView) findViewById(R.id.petViewImage);
        petImage.setVisibility(View.VISIBLE);
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
//        petAnimeRun();
    }
    private void dogDefaultDelete(){
        petImage = (ImageView) findViewById(R.id.petViewImage);
        TranslateAnimation petMoveRight = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0
        );
        petMoveRight.setDuration(0);
        petMoveRight.setRepeatMode(0);
        petMoveRight.setRepeatCount(0);
        petImage.startAnimation(petMoveRight);
        petImage.setVisibility(View.INVISIBLE);
    }

//    }
//    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Override
    protected void onStart() {
        super.onStart();
        sound();
        petDefaultAnime();
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
        },3000);
    }
}

