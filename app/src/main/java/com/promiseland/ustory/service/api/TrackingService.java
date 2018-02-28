package com.promiseland.ustory.service.api;

import android.app.Application;
import com.ajnsnewmedia.kitchenstories.service.base.CustomService;

public interface TrackingService extends CustomService {
    String getAmplitudeId();

    void init(Application application, String str);
}
