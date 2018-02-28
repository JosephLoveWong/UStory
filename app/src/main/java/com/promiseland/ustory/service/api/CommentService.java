package com.promiseland.ustory.service.api;

import com.ajnsnewmedia.kitchenstories.base.model.ui.comment.CommentImageUiModel;
import com.ajnsnewmedia.kitchenstories.mvp.base.recyclerview.PageablePageLoader;
import com.ajnsnewmedia.kitchenstories.service.base.CustomService;
import com.ajnsnewmedia.kitchenstories.ui.util.image.ImageInfo;
import com.ajnsnewmedia.kitchenstories.ultron.model.base.BaseFeedItem;
import com.ajnsnewmedia.kitchenstories.ultron.model.comment.Comment;
import com.ajnsnewmedia.kitchenstories.ultron.model.comment.CommentImage;
import com.ajnsnewmedia.kitchenstories.ultron.model.comment.CommentImagePage;
import com.ajnsnewmedia.kitchenstories.ultron.model.comment.CommentPage;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;

public interface CommentService extends CustomService {
    PageablePageLoader<Comment, CommentPage> getCommentAnswers(String str);

    PageablePageLoader<CommentImage, CommentImagePage> getCommentImagesForFeedItem(String str);

    PageablePageLoader<Comment, CommentPage> getCommentsForFeedItem(String str);

    CommentImageUiModel getLatestImageUiModel(String str, String str2);

    Single<Comment> getSingleCommentWithFeedItem(String str);

    List<CommentImageUiModel> mergeCommentImageListsHelper(Comment comment);

    List<CommentImageUiModel> mergeCommentImageListsHelper(List<CommentImage> list, String str, List<String> list2);

    Single<Comment> saveComment(String str, BaseFeedItem baseFeedItem, Comment comment);

    Observable<CommentImage> startImageUpload(Comment comment, ImageInfo imageInfo);

    void uploadRemainingImagesAndCleanUp();
}
