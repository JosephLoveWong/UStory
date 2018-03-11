package com.promiseland.ustory.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.promiseland.ustory.R;
import com.promiseland.ustory.base.model.ui.base.ImageUiModel;
import com.promiseland.ustory.base.model.ui.user.UserUiModelSuccess;
import com.promiseland.ustory.base.util.FieldHelper;
import com.promiseland.ustory.ui.util.ViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfilePictureView extends RoundedFrameLayout {
    @BindView
    KSImageView mUserImage;
    @BindView
    TextView mUserNameInitials;

    public ProfilePictureView(Context context) {
        super(context);
        init(context);
    }

    public ProfilePictureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProfilePictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.profile_picture_view, this, true);
        ButterKnife.bind((View) this);
    }

    public void setUser(UserUiModelSuccess user) {
        if (user == null) {
            ViewHelper.makeGone(this.mUserImage, this.mUserNameInitials);
        } else if (user.getUserImage() == null || !user.getUserImage().isValid()) {
            setUserNameInitials(user.getUsername());
        } else {
            setProfilePicture(user.getUserImage());
        }
    }

    public void setUser(User author) {
        if (author == null) {
            ViewHelper.makeGone(this.mUserImage, this.mUserNameInitials);
        } else if (author.getUserImage() == null || FieldHelper.isEmpty(author.getUserImage().getUrl())) {
            setUserNameInitials(author.getUsername());
        } else {
            setProfilePicture(author.getUserImage().getUrl());
        }
    }

    public void setProfilePicture(String url) {
        ViewHelper.makeGone(this.mUserNameInitials);
        ViewHelper.makeVisible(this.mUserImage);
        this.mUserImage.loadUrl(url);
    }

    public void setProfilePicture(ImageUiModel imageUiModel) {
        ViewHelper.makeGone(this.mUserNameInitials);
        ViewHelper.makeVisible(this.mUserImage);
        this.mUserImage.load(imageUiModel, 10);
    }

    public void setUserNameInitials(String userName) {
        ViewHelper.makeVisible(this.mUserNameInitials);
        ViewHelper.makeGone(this.mUserImage);
        if (!FieldHelper.isEmpty((CharSequence) userName)) {
            this.mUserNameInitials.setText(userName.substring(0, 1));
        }
    }
}
