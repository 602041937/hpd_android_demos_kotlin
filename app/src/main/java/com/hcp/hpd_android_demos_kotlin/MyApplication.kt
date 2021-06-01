package com.hcp.hpd_android_demos_kotlin

import androidx.multidex.MultiDexApplication
import com.youzan.androidsdk.YouzanSDK
import com.youzan.androidsdkx5.YouZanSDKX5Adapter


class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        YouzanSDK.isDebug(true)
        YouzanSDK.init(
            this,
            "d259ac4afc33521d0e",
            "a270386882e34007b25869ed2f4e2f5f",
            YouZanSDKX5Adapter()
        )
    }
}