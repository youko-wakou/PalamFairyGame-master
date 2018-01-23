package jp.palamfairy.project.android.palamfairygame;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by appu2 on 2018/01/23.
 */

public class UserPetEntryActivity extends AppCompatActivity {
    private Bundle UserprofBundle;
    private ArrayList<String> userProfArray;
    private TranslateAnimation dogJumpTransAnime;
    private ImageView dogJumpAnimeView;
    private MediaPlayer entryBgmPlayer;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_pet_entry);
//        ==============レイアウトID読み込み===========================================================================
        dogJumpAnimeView = (ImageView)findViewById(R.id.EntrydogView);
//        ============================================================================================================
//        ======================intentで渡ってきた情報をここで受け取る===================================================
        UserprofBundle = getIntent().getExtras();
        userProfArray = (ArrayList<String>)UserprofBundle.get("userProfArray");
//        ================================================================================================================
//        ★アニメーション呼び出し★
        dogJumpAnime();
        entryBgm();
        setTitle("スタート");
    }
//    ===========犬がジャンプするアニメーション==========================================================
    private void dogJumpAnime(){
        dogJumpTransAnime = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,0,
                TranslateAnimation.RELATIVE_TO_SELF,0,
                TranslateAnimation.RELATIVE_TO_SELF,0,
                TranslateAnimation.RELATIVE_TO_SELF,1
        );
        dogJumpTransAnime.setDuration(2000);
        dogJumpTransAnime.setRepeatMode(TranslateAnimation.RESTART);
        dogJumpTransAnime.setRepeatCount(TranslateAnimation.INFINITE);
        dogJumpAnimeView.startAnimation(dogJumpTransAnime);
    }
//    ====================================================================================================
//        ==============================BGMの設定===============================================================
    private void entryBgm(){
        entryBgmPlayer = MediaPlayer.create(this,R.raw.entry);
        entryBgmPlayer.setLooping(true);
        entryBgmPlayer.setVolume(0.3f,0.3f);
        entryBgmPlayer.start();
    }
//===============================================================================================================
}
