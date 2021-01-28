package com.example.shovvmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.user.UserApi;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private View drawerView;
    private View logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 키 해시 값 구하기
        String keyHash = Utility.INSTANCE.getKeyHash(this);
        Log.i(TAG, "onCreate: keyHash:" + keyHash);
         */

        logoutButton = findViewById(R.id.logout);

        //로그인 결과 처리
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken != null){

                }
                if(throwable != null){

                }
                updateKakaoLoginUI();
                return null;
            }
        };

        //로그아웃 버튼 (드로어뷰안에)
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        //updateKakaoLoginUI(); //화면 갱신
                        return null;
                    }
                });
            }
        });

        //드로어뷰 설정
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawer);

        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    //로그인ㅇ이 되어ㅣ있는지 확인
    private void updateKakaoLoginUI(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if(user != null){
                    Log.i(TAG, "사용자 정보 요청 성공" +
                            "\n회원번호: "+ user.getId() +
                            "\n이메일: "+ user.getKakaoAccount().getEmail() +
                            "\n닉네임: "+ user.getKakaoAccount().getProfile().getNickname() +
                            "\n프로필사진:"+user.getKakaoAccount().getProfile().getThumbnailImageUrl());


                    TextView nickName = (TextView)findViewById(R.id.nick);
                    ImageView profileImage = findViewById(R.id.profileImage);


                    //drawerView 설정
                    nickName.setText(user.getKakaoAccount().getProfile().getNickname());
                    Glide.with(profileImage).load(user.getKakaoAccount().getProfile().getThumbnailImageUrl()).circleCrop().into(profileImage);

                }else{
                }
                if(throwable != null){
                    Log.w(TAG, "invoke : "+ throwable.getLocalizedMessage());
                }
                return null;
            }
        });
    }


    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {

        //슬라이드했을 때
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            updateKakaoLoginUI();


        }

        //오픈버튼을 눌렀을 때
        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        //닫기 버튼을 눌렀을 때
        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        //상태가 변경되었을 때
        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

}