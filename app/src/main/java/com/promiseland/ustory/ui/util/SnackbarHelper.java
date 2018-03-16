package com.promiseland.ustory.ui.util;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.View.OnClickListener;

import com.promiseland.ustory.base.Constants;
import com.promiseland.ustory.ui.base.ui.BaseActivity;

public class SnackbarHelper {
    public static void show(BaseActivity activity, int messageId) {
        show(activity, messageId, 0);
    }

    public static void show(BaseActivity activity, int messageId, int length) {
        if (activity != null && activity.getSnackBarView() != null) {
            show(activity.getSnackBarView(), messageId, length);
        }
    }

    public static void show(BaseActivity activity, String message) {
        show(activity, message, 0);
    }

    public static void show(BaseActivity activity, String message, int length) {
        if (activity != null && activity.getSnackBarView() != null) {
            show(activity.getSnackBarView(), message, length);
        }
    }

    public static void show(View view, int messageId) {
        show(view, messageId, 0);
    }

    public static void show(View view, int messageId, int length) {
        if (Constants.NO_MESSAGE != messageId && view != null) {
            Snackbar.make(view, messageId, length).show();
        }
    }

    public static void show(View view, String message, int length) {
        if (view != null) {
            Snackbar.make(view, (CharSequence) message, length).show();
        }
    }

    public static void show(BaseActivity activity, int msgId, int actionId, int length, OnClickListener listener) {
        if (activity != null && activity.getSnackBarView() != null) {
            Snackbar.make(activity.getSnackBarView(), msgId, length).setAction(actionId, listener).show();
        }
    }

    public static Snackbar show(View view, int msgId, int actionId, int length, OnClickListener listener) {
        if (view == null) {
            return null;
        }
        Snackbar snackbar = Snackbar.make(view, msgId, length).setAction(actionId, listener);
        snackbar.show();
        return snackbar;
    }

    public static Snackbar show(View view, String message, String action, int length, OnClickListener listener) {
        if (view == null) {
            return null;
        }
        Snackbar snackbar = Snackbar.make(view, (CharSequence) message, length).setAction((CharSequence) action, listener);
        snackbar.show();
        return snackbar;
    }
}
