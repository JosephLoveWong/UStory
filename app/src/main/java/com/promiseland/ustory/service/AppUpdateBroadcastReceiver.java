package com.promiseland.ustory.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppUpdateBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

    }
//    InstallationDataService mInstallationDataService;
//
//    public AppUpdateBroadcastReceiver() {
//        KitchenStoriesApp.getAppComponent().inject(this);
//    }
//
//    public void onReceive(Context context, Intent intent) {
//        if (new IntentFilter("android.intent.action.MY_PACKAGE_REPLACED").hasAction(intent.getAction())) {
//            this.mInstallationDataService.updateInstallation();
//        }
//    }
}
