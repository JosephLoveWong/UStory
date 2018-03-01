package com.promiseland.ustory.service.impl;

import com.promiseland.ustory.service.api.UltronService;
import com.promiseland.ustory.service.base.BackgroundHandler;
import com.promiseland.ustory.service.base.Command;
import com.promiseland.ustory.service.base.CustomService;
import com.promiseland.ustory.ultron.Ultron;

import org.greenrobot.eventbus.EventBus;

import java.net.UnknownHostException;

import retrofit2.HttpException;
import retrofit2.Response;
import timber.log.Timber;

public class UltronServiceImpl implements UltronService, CustomService {
    private final BackgroundHandler mBackgroundHandler;
    private final EventBus mEventBus;
    private long mFeedLoadingTime;
    private boolean mIsLoadingFeed;
    private boolean mIsLoadingHowToFeed;
    private final Ultron mUltron;

    @Override
    public void loadLikeIds() {

    }

    private class FeedPageCommand extends Command {
        private boolean mFirstTimeFeed = false;

        public FeedPageCommand(boolean firstTimeFeed) {
            // TODO commandId
            super(0);
            this.mFirstTimeFeed = firstTimeFeed;
        }

        public void doInBackground() {

        }
    }

    public UltronServiceImpl(EventBus eventBus, Ultron ultron, BackgroundHandler backgroundHandler) {
        this.mEventBus = eventBus;
        this.mEventBus.register(this);
        this.mUltron = ultron;
        this.mBackgroundHandler = backgroundHandler;
    }

    public void loadFeedPage(boolean loadFirstTimeFeed) {
        this.mIsLoadingFeed = true;
        this.mBackgroundHandler.sendMessage(new FeedPageCommand(loadFirstTimeFeed), this);
    }

    public boolean isLoadingFeed() {
        return this.mIsLoadingFeed;
    }


    public boolean isLoadingHowToFeed() {
        return this.mIsLoadingHowToFeed;
    }

    private static void handleLoggingOnFailure(Throwable t, String s) {
        if (t instanceof UnknownHostException) {
            Timber.d(t, s, new Object[0]);
        } else {
            Timber.w(t, s, new Object[0]);
        }
    }

    private static int getErrorCodeByExceptionType(Throwable t) {
        if (t instanceof UnknownHostException) {
            return 2;
        }
        return 128;
    }

    private static void handleLoggingUnsuccessfulResponse(Response response) {
        Timber.d("get status %d for url: %s", Integer.valueOf(response.code()), response.raw().request().url());
    }

    public static void handleLoggingWithException(Throwable error, String errorMessage) {
        if (error instanceof HttpException) {
            handleLoggingUnsuccessfulResponse(((HttpException) error).response());
        } else {
            handleLoggingOnFailure(error, errorMessage);
        }
    }
}
