package jp.palamfairy.project.android.palamfairygame;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by appu2 on 2018/01/22.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText passwordEdit;
    private EditText emailEdit;
    private Button loginButton;
    private Button signupButton;
    private FirebaseAuth firebaseauth;
    private FirebaseUser user;
    private DatabaseReference Databasereference;
    private DatabaseReference userRef;
    private OnCompleteListener<AuthResult> AccountListener;
    private OnCompleteListener<AuthResult> LoginListener;
    private String email;
    private String password;
    private ProgressDialog loginProgressDialog;
    private AlertDialog.Builder loginErrorDialog;
    private boolean isCreateAccount = false;
    private InputMethodManager keyBoardShow;
    private RelativeLayout loginViewLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        =========================レイアウトID取得=====================================================
        passwordEdit = (EditText) findViewById(R.id.password);
        emailEdit = (EditText) findViewById(R.id.email);
        loginButton = (Button) findViewById(R.id.login);
        signupButton = (Button) findViewById(R.id.sigin);
        loginViewLayout = (RelativeLayout)findViewById(R.id.loginActivityView);
//        ====================================================================================================
        Databasereference = FirebaseDatabase.getInstance().getReference();
        firebaseauth = FirebaseAuth.getInstance();

        AccountListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
//                ~~~~~~~~~~~~ログインに成功した場合~~~~~~~~~~~~~~~~~~~~~~~~~
                if (task.isSuccessful()) {
                    email = emailEdit.getText().toString();
                    password = passwordEdit.getText().toString();
                    login(email, password);
//                    ~~~~~~~~~~~~~~~~~~~~~ログインに失敗した場合~~~~~~~~~~~~~~~~~~~~~~~
                } else {

                    loginErrorDialog = new AlertDialog.Builder(getApplicationContext());
                    loginErrorDialog.setTitle("ログインエラー");
                    loginErrorDialog.setMessage("やり直してください");
                    loginErrorDialog.setPositiveButton("Ok", null);
                    loginErrorDialog.show();

//                    プログレスダイアログを非表示にする
                    loginProgressDialog.dismiss();
                }
            }
        };

        LoginListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful()){
                    user = firebaseauth.getCurrentUser();
                    userRef = Databasereference.child(Const.userPATH).child(user.getUid());
                    if(isCreateAccount){
                        loginProgressDialog.dismiss();
                    }else{
                        Snackbar.make(loginViewLayout,"ログインに失敗しました",Snackbar.LENGTH_LONG).show();
                        loginProgressDialog.dismiss();
                    }
                }
            }
        };

        setTitle("ログイン");
//        ==================-プログレスダイアログ作成=====================================================
        loginProgressDialog = new ProgressDialog(this);
        loginProgressDialog.setMessage("ログイン処理中…");
//        ===============================================================================================
//    ====================サインアップクリックイベント=====================================================
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                ======================キーボードが出ていたら閉じる======================================================
                keyBoardShow = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                keyBoardShow.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
//                =====================================================================================================

                email = emailEdit.getText().toString();
                password = passwordEdit.getText().toString();
//                ================================メールが未入力でない、パスワードが6文字以上=======================================================
                if(email.length()!=0&&password.length()>=6){
                    isCreateAccount = true;

                    createAccount(email,password);
//                    ~~~~~~~~~~~~~もしメールアドレスが未入力だったら~~~~~~~~~~~~~~~~~~~~~~~~~
                }else if(email.length()==0) {
                    Snackbar.make(v,"メールアドレスが入力されていません",Snackbar.LENGTH_LONG).show();
                }else if(password.length()<6){
                    Snackbar.make(v,"パスワードは６文字以上で入力してください",Snackbar.LENGTH_LONG).show();
                }

            }
        });

//        =====================ログインボタンクリックイベント========================================================
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                keyBoardShow = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                keyBoardShow.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                if(email.length() !=0 && password.length()>=6){
                    isCreateAccount = false;
                    login(email,password);
                }else if(email.length()<6){
                    Snackbar.make(v,"パスワードは6文字以上で入力してください",Snackbar.LENGTH_LONG).show();
                }else if(email.length() ==0){
                    Snackbar.make(v,"メールアドレスが入力されていません",Snackbar.LENGTH_LONG).show();
                }
            }
        });
//        =================================================================================================================
    }
//    ============ここまでonCreate==================================================================
//    =======================アカウント作成メソッド====================================================
    private void createAccount(String email,String password){
        loginProgressDialog.show();
        firebaseauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(AccountListener);
    }
//    =================ログインメソッド=====================================================================
    private void login(String email,String password){
        loginProgressDialog.show();
        firebaseauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginListener);
    }
//    ========================================================================================================
}
