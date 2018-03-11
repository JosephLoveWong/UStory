package com.promiseland.ustory.ui.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.promiseland.ustory.R;
import com.promiseland.ustory.ui.util.ViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmptyStateRecyclerView extends FrameLayout {
    @BindView(R.id.empty_stub)
    ViewStub mEmptyStub;
    private View mEmptyView;
    @BindView(R.id.error_stub)
    ViewStub mErrorStub;
    private View mErrorView;
    @BindView(R.id.loading_stub)
    ViewStub mLoadingStub;
    private View mLoadingView;
    @BindView(R.id.recycler_view_inner)
    RecyclerViewForVerticalScrollChildren mRecyclerViewInner;

    public EmptyStateRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public EmptyStateRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmptyStateRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.empty_state_recycler_view, this, true);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this, this);
    }

    public RecyclerView getRecyclerView() {
        return this.mRecyclerViewInner;
    }

    public void showLoadingIndicator() {
        if (this.mLoadingView == null) {
            this.mLoadingView = this.mLoadingStub.inflate();
            this.mLoadingStub = null;
        } else {
            ViewHelper.makeVisible(this.mLoadingView);
        }
        ViewHelper.makeGone(this.mErrorView, this.mEmptyView, this.mRecyclerViewInner);
    }

    public void showError(int stringResId, OnClickListener retryClickListener) {
        showError(R.layout.list_item_error, R.id.btn_try_again, R.id.error_text, stringResId, retryClickListener);
    }

    public void showError(int loadingErrorLayoutResource, int retryClickResource, int errorTextViewId, int errorStringId, OnClickListener retryClickListener) {
        if (this.mErrorView == null) {
            this.mErrorStub.setLayoutResource(loadingErrorLayoutResource);
            this.mErrorView = this.mErrorStub.inflate();
            TextView errorTextView = this.mErrorView.findViewById(errorTextViewId);
            if (errorTextView != null) {
                errorTextView.setText(errorStringId);
            }
            this.mErrorStub = null;
        } else {
            ViewHelper.makeVisible(this.mErrorView);
        }
        this.mErrorView.findViewById(retryClickResource).setOnClickListener(retryClickListener);
        ViewHelper.makeGone(this.mLoadingView, this.mEmptyView, this.mRecyclerViewInner);
    }

    public void showEmptyList(int emptyListLayoutResource) {
        if (this.mEmptyView == null) {
            this.mEmptyStub.setLayoutResource(emptyListLayoutResource);
            this.mEmptyView = this.mEmptyStub.inflate();
            this.mEmptyStub = null;
        } else {
            ViewHelper.makeVisible(this.mEmptyView);
        }
        ViewHelper.makeGone(this.mLoadingView, this.mErrorView, this.mRecyclerViewInner);
    }

    public void hideEmptyViews() {
        ViewHelper.makeGone(this.mLoadingView, this.mErrorView, this.mEmptyView);
        ViewHelper.makeVisible(this.mRecyclerViewInner);
    }

    public void enableVerticalScrollEventPassThrough() {
        this.mRecyclerViewInner.enableVerticalScrollEventPassThrough();
    }

    public void updateRecyclerViewPadding(int extraPadding) {
        updateRecyclerViewPadding(extraPadding, extraPadding, extraPadding, extraPadding);
    }

    public void updateRecyclerViewPadding(int extraPaddingLeft, int extraPaddingTop, int extraPaddingRight, int extraPaddingBottom) {
        this.mRecyclerViewInner.setClipToPadding(false);
        this.mRecyclerViewInner.setPadding(this.mRecyclerViewInner.getPaddingLeft() + extraPaddingLeft, this.mRecyclerViewInner.getPaddingTop() + extraPaddingTop, this.mRecyclerViewInner.getPaddingRight() + extraPaddingRight, this.mRecyclerViewInner.getPaddingBottom() + extraPaddingBottom);
    }
}
