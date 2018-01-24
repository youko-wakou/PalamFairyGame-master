package jp.palamfairy.project.android.palamfairygame;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by appu2 on 2018/01/23.
 */

public class PetDefaultActivity extends AppCompatActivity {
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
    private ImageView cleanUntiA;
    private ImageView cleanUntiB;
    private ImageView cleanUntiC;
    private ImageView cleanUntiD;
    private AnimationDrawable HandAnimeDrawable;
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
    private int mCount;
    private int removeCount;
    private ArrayList<Integer> ToileRoopList;
    private ImageView OnigiriView;
    private RelativeLayout relativelayout;
    private ImageView toileImg;
    private ImageView petNadeView;
    private ImageView handAnimeView;
    private MediaPlayer petNaderuPlayer;
    private ImageView commentBackView;
    private TextView commentText;
    private String[] talkText;
    private int TalkCount;
    private Random randomTalk;
    private int SelectTalk;
    private String talkString;
    private Random randOutTalk;
    private int randOutCount;
    private AlphaAnimation commentAlphaAnimation;
    private MediaPlayer selectPlayer;
    private MediaPlayer commentPlayer;
    private MediaPlayer menuOpenPlayer;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private DatabaseReference userRef;
    private DatabaseReference databasereference;
    private String petName;
    private String userName;
    private Toolbar MenuToolbar;
    private Intent logoutIntent;
    private ProgressDialog userLogoutDialog;
    //    Handler mhandler= new Handler();
    private int random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_default_activity);
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
        MenuToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(MenuToolbar);
//        ==========================表示名取得=================================================
        auth = FirebaseAuth.getInstance();
        databasereference = FirebaseDatabase.getInstance().getReference();
        user = auth.getCurrentUser();
        userRef = databasereference.child(Const.userPATH).child(user.getUid());
        userRef.addChildEventListener(userInfoListener);
//============================================================================================
//        ================プログレスダイアログ作成========================================================
        userLogoutDialog = new ProgressDialog(this);
        userLogoutDialog.setMessage("ログアウトしています…");
//        ================================================================================================

//        userRef = databasereference.child(Const.userPATH).child(user.getUid());
//        private ChildEventListener userInfoListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                HashMap getNameMap = (HashMap)dataSnapshot.getValue();
//                petName = (String)getNameMap.get("petName");
//                userName = (String)getNameMap.get("userName");
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };

//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                HashMap getNameMap = (HashMap)dataSnapshot.getValue();
//                petName = (String)getNameMap.get("petName");
//                userName = (String)getNameMap.get("userName");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        ======================================================================================
//        =========================おしゃべり配列設定================================================
        commentBackView = (ImageView)findViewById(R.id.commentBG);
        commentText = (TextView)findViewById(R.id.commentTEX);
        commentBackView.setVisibility(View.INVISIBLE);
        commentText.setVisibility(View.INVISIBLE);
//        参照変数にしゃべる内容を入れておく
        talkText = new String[]{
                "今日もいっしょにあそぼうね！",
                String.format("%s をなでなでして～～",petName),
                "おはよう！今日はどんなことがあったかな？" ,
                "何か楽しいことあった？？",
                String.format("%s だいすき＞＜",userName)
        };
        CommentOut();
//        ====================================================================================
//======================掃除道具==================================================
        cleanUntiA = (ImageView)findViewById(R.id.cleanGoodsA);
        cleanUntiB = (ImageView)findViewById(R.id.cleanGoodsB);
        cleanUntiC = (ImageView)findViewById(R.id.cleanGoodsC);
        cleanUntiD = (ImageView)findViewById(R.id.cleanGoodsD);
        cleanUntiA.setVisibility(View.INVISIBLE);
        cleanUntiB.setVisibility(View.INVISIBLE);
        cleanUntiC.setVisibility(View.INVISIBLE);
        cleanUntiD.setVisibility(View.INVISIBLE);
//============================================================================

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
                selectSound();
                toileA.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        toileA.setVisibility(View.INVISIBLE);
                        cleanToile(cleanUntiA);
                        setroopCount(1);
                    }
                });
                toileB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toileB.setVisibility(View.INVISIBLE);
                        cleanToile(cleanUntiB);
                        setroopCount(1);
                    }
                });
                toileC.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        toileC.setVisibility(View.INVISIBLE);
                        cleanToile(cleanUntiC);
                        setroopCount(1);
                    }
                });
                toileD.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        toileD.setVisibility(View.INVISIBLE);
                        cleanToile(cleanUntiD);
                        setroopCount(1);
                    }
                });
            }
        });
//        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        ======================なでなでクリックイベント===================================================================
        petNadeView = (ImageView)findViewById(R.id.petHandSmile);
        petNadeView.setVisibility(View.INVISIBLE);
        handAnimeView = (ImageView)findViewById(R.id.handSlide);
        handAnimeView.setVisibility(View.INVISIBLE);
        handItem.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                selectSound();
                dogDefaultDelete();
                naderuAnime();
            }
        });
//        ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
//        ごはんアイテム
        OnigiriView = (ImageView)findViewById(R.id.OnigiriView);
        OnigiriView.setVisibility(View.INVISIBLE);
        foodItem.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                selectSound();
                OnigiriAlfa = new AlphaAnimation(1,0);
                OnigiriAlfa.setDuration(4000);
                OnigiriView.startAnimation(OnigiriAlfa);
//                petImage.setVisibility(View.INVISIBLE);
                dogSmile();
            }
        });
//        ===============メニューオープンボタン=================================================
        Button fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuOpenSound();
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
    //    ==========================================================================================================
//        ==========================表示名取得リスナーonCreateで呼び出し=================================================
    private ChildEventListener userInfoListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap getNameMap = (HashMap)dataSnapshot.getValue();
            petName = (String)getNameMap.get("petName");
            userName = (String)getNameMap.get("userName");
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
//    ===============================================================================================================
//    ================R.menu.logout_menu表示==========================================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
         int id=item.getItemId();
         if(id == R.id.logout_item){
             if(user !=null) {
                 auth.signOut();
                 Snackbar.make(relativelayout,"ログアウトしました",Snackbar.LENGTH_LONG).show();
                 userLogoutDialog.show();
                 logoutIntent = new Intent(this, LoginActivity.class);
                 startActivity(logoutIntent);
             }else{
                 Snackbar.make(relativelayout,"ログインしていません",Snackbar.LENGTH_LONG).show();
             }
             return true;
         }
         return super.onOptionsItemSelected(item);
    }
//    ==========================================================================================================================
    //    ==============ウンチを何個削除した確認、４つ消したらトイレ呼び出し========================================
    private void setroopCount(int count){
        mCount = count;
        if(mCount!=0) {
//            removeCount = getroopCount();
            ToileRoopList.remove(mCount);
            if(ToileRoopList.size() ==1){
                ToileRoopList.remove(0);
                toile();
            }
        }
    }
    //    ==========================================================================================================
    private int getroopCount(){
        return mCount;
    }
    //    ===================なでなでアニメーション＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    private void naderuAnime(){
        petNadeView.setVisibility(View.VISIBLE);
        handAnimeView.setVisibility(View.VISIBLE);
        handAnimeView.setBackgroundResource(R.drawable.hand_animation);
        HandAnimeDrawable =(AnimationDrawable)handAnimeView.getBackground();
        HandAnimeDrawable.start();
//        なでるときのサウンド
        petNaderuPlayer = MediaPlayer.create(this,R.raw.hand);
        petNaderuPlayer.start();
//        3秒で止める
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                HandAnimeDrawable.stop();
                petDefaultAnime();
                petNadeView.setVisibility(View.INVISIBLE);
                handAnimeView.setVisibility(View.INVISIBLE);
            }
        },3000);
    }
//    ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝

    //    ~~~~~~~~~~~~~~~~~~~~ウンチを掃除するアニメーション~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private void cleanToile(ImageView image){
        ImageView IMG = image;
        cleanGoodsAlpha = new AlphaAnimation(1,0);
        cleanGoodsAlpha.setDuration(2000);
        IMG.startAnimation(cleanGoodsAlpha);

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
//===================コメントが表示されるアニメーション============================================
    private void CommentOut(){
        TalkCount =talkText.length;
        randomTalk = new Random();
        SelectTalk = randomTalk.nextInt(TalkCount);
        talkString = talkText[SelectTalk];
        commentText.setText(talkString);
        randOutTalk = new Random();
        randOutCount = randOutTalk.nextInt(300000)+1000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                commentSound();
                commentAlphaAnimation = new AlphaAnimation(1,0);
                commentAlphaAnimation.setDuration(3000);
                commentText.startAnimation(commentAlphaAnimation);
                commentBackView.startAnimation(commentAlphaAnimation);
                CommentOut();
            }
        },3000);
    }
    //    =============================================================================================
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
    //    =======================トイレお片付けサウンド===================================================================
    private void toileSound(){
        mOnara = MediaPlayer.create(this,R.raw.onara);
        mOnara.start();
    }
    //    =============================================================================================================
//    =======================ボタン選択サウンド==================================================================
    private void selectSound(){
        selectPlayer =MediaPlayer.create(this,R.raw.select);
        selectPlayer.start();
    }
    //    ==========================================================================================================
//    =================コメントサウンド========================================================================
    private void commentSound(){
        commentPlayer = MediaPlayer.create(this,R.raw.comment);
        commentPlayer.start();
    }
//    ==========================================================================================================

    //    ========================メニューオープンサウンド========================================================
    private void menuOpenSound(){
        menuOpenPlayer = MediaPlayer.create(this,R.raw.menu);
        menuOpenPlayer.start();
    }
    //    =======================================================================================================
//    ===========================おにぎりを食べるときのサウンド==============================================
    private void OnigiriSound(){
        OnigiriPlay = MediaPlayer.create(this,R.raw.onigiri);
        OnigiriPlay.start();
    }
//    ============================================================================================================

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

                toileRoop.roop +=1;
                if(toileRoop.roop >4) {
                    toileRoop.roop = 0;
                }

                int count =0;
                count +=1;
                ToileRoopList =toileRoop.getRoopList();
                if(toileRoop.roop !=0) {
                    toileRoop.setRoopList(count);
                }

                if(ToileRoopList.size() <=4){
                    toile();
                }

            }
        },3000);
    }
}
