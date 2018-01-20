package jp.palamfairy.project.android.palamfairygame;

import android.media.AudioManager;
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
import android.view.animation.Animation;
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
    private Button foodItem;
    private Button handItem;
    private Button cleanItem;
    private boolean showItem;
    private boolean wantShow;
    private int imagewidth;
    private int imageheight;
//    private RelativeLayout mParentLayout;
//    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener;
//    private ImageView toileA;
//    private ImageView toileB;
//    private ImageView toileC;
//    private ImageView toileD;
    private RelativeLayout relativelayout;
    private LinearLayout layout;
    private ImageView toileImg;
//    Handler mhandler= new Handler();
    private int random;

//    private Matrix matrix;
//    private String roop;
//    private PetSub petsub = new PetSub();
//    private ArrayList<String> toileList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
////        トイレのビュー取得
//        toileA = (ImageView)findViewById(R.id.toileA);
//        toileB = (ImageView)findViewById(R.id.toileB);
//        toileC = (ImageView)findViewById(R.id.toileC);
//        toileD = (ImageView)findViewById(R.id.toileD);
//        //        トイレの表示
//        toileA.setVisibility(View.INVISIBLE);
//        toileB.setVisibility(View.INVISIBLE);
//        toileC.setVisibility(View.INVISIBLE);
//        toileD.setVisibility(View.INVISIBLE);
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
//    private void petGrouMenu(){
//
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
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
        mBgm.start();
    }
//
//    private void setToileList(String value){
////        mToileKey = key;
//        mToileValue = value;
//        toileList.add(mToileValue);
//    }
//    private ArrayList<String> getToileList(){
//        return toileList;
//    }
//    ★
    private void toile(){
//        Random randomInt = new Random();
//         random = randomInt.nextInt(600000)+60000;
//        Timer toileTime = new Timer();
//        toileTime.schedule(new TimerTask(){
//            @Override
//            public void run(){
//                toileImg = new ImageView(getApplicationContext());
//                toileImg.setImageResource(R.drawable.unti);
//                toileImg.setMaxHeight(90);
//                toileImg.setMaxWidth(90);
//                relativelayout.addView(toileImg);

//                Handler mhandler= new Handler();
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        layout = new LinearLayout(getApplicationContext());
                        Random gravityRand = new Random();
                        int gravityInt =gravityRand.nextInt(500)+90;
                        layout.setGravity(gravityInt);
                        layout.setLayoutParams(new LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.MATCH_PARENT,
                                gravityInt,
//                                LinearLayout.LayoutParams.MATCH_PARENT
                                gravityInt
                        ));
//                        relativelayout.setLayoutParams(new LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.MATCH_PARENT,
//                                LinearLayout.LayoutParams.MATCH_PARENT
//                        ));
                        setContentView(layout);
//                        setContentView(relativelayout);
                        toileImg = new ImageView(getApplicationContext());
                        toileImg.setImageResource(R.drawable.unti);
                        imagewidth = 90;
                        imageheight= 90;
                        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(imagewidth,imageheight);
                        toileImg.setLayoutParams(layoutparams);
                        layout.addView(toileImg);
//                        relativelayout.addView(toileImg);

//                        relativelayout.addView(layout);
//                        toileImg.setMaxWidth(petImage.getWidth()/2);
//                        toileImg.setMaxHeight(petImage.getHeight()/2);
//                        ViewTreeObserver vto = toileImg.getViewTreeObserver();
//                        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//
//                            @Override
//                            public void onGlobalLayout() {
//                                ViewGroup.LayoutParams layoutparams = toileImg.getLayoutParams();
//
//                                layoutparams.height = 80;
//                                layoutparams.width = 80;
//                                toileImg.setLayoutParams(layoutparams);
//                                relativelayout.addView(toileImg, layoutparams);
//                            }
//                        });
                        toileSound();
                        toile();
                    }
                },1000);
//
//            }
//        },
//                100
//                ,
//                100
//        );
    }
}
