package com.promiseland.ustory.ultron.testutils.logger;

import timber.log.Timber.Tree;

public class CrashlyticsTree extends Tree {
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority >= 5) {
            // TODO
//            Crashlytics.log(priority, tag, message);
//            if (t != null) {
//                Crashlytics.logException(t);
//            }
        }
    }
}
