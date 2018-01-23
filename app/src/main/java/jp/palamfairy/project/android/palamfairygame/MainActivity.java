package jp.palamfairy.project.android.palamfairygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Intent loginIntent;
    private FirebaseUser userCheckFirebase;
    private Intent petDefaultIntent;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        =========================ログインしているか確認==============================================
        userCheckFirebase = FirebaseAuth.getInstance().getCurrentUser();
        if(userCheckFirebase == null){
            loginIntent = new Intent(this,LoginActivity.class);
            startActivity(loginIntent);
        }else{
            petDefaultIntent = new Intent(this,PetDefaultActivity.class);
            startActivity(petDefaultIntent);
        }
//        =============================================================================================

    }

}

