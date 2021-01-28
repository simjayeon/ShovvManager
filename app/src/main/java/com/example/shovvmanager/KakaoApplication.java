package com.example.shovvmanager;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this, "70034769c6b011900c0a3c9ed1a7af28");
    }
}
