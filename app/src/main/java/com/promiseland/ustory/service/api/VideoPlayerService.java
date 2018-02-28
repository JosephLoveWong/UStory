package com.promiseland.ustory.service.api;

import android.view.TextureView;
import com.ajnsnewmedia.kitchenstories.ultron.model.video.Video;
import com.google.android.exoplayer2.SimpleExoPlayer;

public interface VideoPlayerService {
    Video getCurrentlyPlayingVideo();

    SimpleExoPlayer getPlayer(String str);

    void pausePlayer(Video... videoArr);

    SimpleExoPlayer registerPlayer(String str, Video video, int i);

    void releasePlayer(String str, Video... videoArr);

    void releaseVideoSurface(Video... videoArr);

    void setVideoSurface(Video video, TextureView textureView);

    void startPlayWhenReady(Video video);
}
