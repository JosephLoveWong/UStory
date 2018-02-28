package com.promiseland.ustory.service.api;

import com.ajnsnewmedia.kitchenstories.base.model.ui.base.ResultUiModel;
import com.ajnsnewmedia.kitchenstories.service.base.CustomService;
import com.ajnsnewmedia.kitchenstories.ultron.model.base.BaseFeedItem;
import com.ajnsnewmedia.kitchenstories.ultron.model.upload.CookbookUploadData;
import com.ajnsnewmedia.kitchenstories.ultron.model.user.Cookbook;
import io.reactivex.Observable;
import java.util.List;
import java.util.Set;

public interface UserContentService extends CustomService {
    void deleteFeedItemFromCookbook(String str, String str2);

    void deleteLike(BaseFeedItem baseFeedItem);

    Set<String> getAllLikeIdsSnapshot();

    List<Cookbook> getCookbookList();

    List<BaseFeedItem> getFeedItemsForCookbook(String str);

    List<BaseFeedItem> getLikes();

    Observable<ResultUiModel<String>> getToggleCommentLikeObservable();

    boolean hasMoreFeedItemsForCookbook(String str);

    boolean hasMoreLikes();

    boolean isLikedItem(String str);

    boolean isLoadingCookbooks();

    boolean isLoadingFeedItemsForCookbook(String str);

    boolean isLoadingLikes();

    void loadCookbooksOfUser();

    void loadFeedItemsForCookbook(String str);

    void loadLikeIds();

    void loadLikes();

    void loadMoreFeedItemsForCookbook(String str);

    void loadMoreLikes();

    void logoutUser();

    void moveFeedItemToCookbook(BaseFeedItem baseFeedItem, String str, Cookbook cookbook);

    void removeCookbook(String str);

    void saveCookbook(CookbookUploadData cookbookUploadData);

    void saveFeedItemToCookbook(BaseFeedItem baseFeedItem, Cookbook cookbook);

    void saveLike(BaseFeedItem baseFeedItem);

    void saveLike(BaseFeedItem baseFeedItem, int i);

    void toggleCommentLike(String str);

    void updateCookbook(Cookbook cookbook);
}
