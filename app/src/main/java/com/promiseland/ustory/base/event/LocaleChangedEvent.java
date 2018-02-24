package com.promiseland.ustory.base.event;

import java.util.Locale;

/**
 * Created by Administrator on 2018/2/24.
 */

public class LocaleChangedEvent {
    public final Locale locale;

    public LocaleChangedEvent(Locale locale) {
        this.locale = locale;
    }
}
