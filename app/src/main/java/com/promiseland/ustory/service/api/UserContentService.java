package com.promiseland.ustory.service.api;

import com.promiseland.ustory.service.base.CustomService;

public interface UserContentService extends CustomService {
    void deleteFeedItemFromCookbook(String str, String str2);

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
}
