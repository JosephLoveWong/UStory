package com.promiseland.ustory.service.api;

import com.ajnsnewmedia.kitchenstories.ultron.UltronCallback;
import com.ajnsnewmedia.kitchenstories.ultron.model.base.BaseFeedItem;
import com.ajnsnewmedia.kitchenstories.ultron.model.base.TilePage;
import com.ajnsnewmedia.kitchenstories.ultron.model.base.UltronErrorInstance;
import com.ajnsnewmedia.kitchenstories.ultron.model.base.UltronId;
import com.ajnsnewmedia.kitchenstories.ultron.model.feed.FeedModule;
import com.ajnsnewmedia.kitchenstories.ultron.model.upload.AuthenticationData;
import com.ajnsnewmedia.kitchenstories.ultron.model.upload.CookbookUploadData;
import com.ajnsnewmedia.kitchenstories.ultron.model.upload.Installation;
import com.ajnsnewmedia.kitchenstories.ultron.model.upload.NewsletterOptInData;
import com.ajnsnewmedia.kitchenstories.ultron.model.upload.UserUploadData;
import com.ajnsnewmedia.kitchenstories.ultron.model.user.Cookbook;
import com.ajnsnewmedia.kitchenstories.ultron.model.user.CookbookPage;
import com.ajnsnewmedia.kitchenstories.ultron.model.video.VideoTag;
import io.reactivex.Single;
import java.util.List;

public interface UltronService {
    void addFeedItemRating(String str, float f);

    void authenticateUser(AuthenticationData authenticationData);

    void countVideoView(String str);

    Single<UltronErrorInstance> deleteCommentLike(String str);

    void deleteCookbook(String str);

    void deleteFeedItemFromCookbook(String str, String str2);

    void deleteLike(BaseFeedItem baseFeedItem, UltronCallback<Void> ultronCallback);

    void forgotPassword(UserUploadData userUploadData);

    List<FeedModule> getFeedPageData();

    void getUserFeedItemRating(String str);

    boolean isLoadingFeed();

    boolean isLoadingHowToFeed();

    Single<UltronErrorInstance> likeComment(UltronId ultronId);

    void loadArticleRecommendations(String str);

    void loadCookbooks(UltronCallback<CookbookPage> ultronCallback);

    void loadFeaturedSearch();

    void loadFeaturedSearchById(String str);

    void loadFeedItemsInCookbookPage(String str, int i, UltronCallback<TilePage> ultronCallback);

    void loadFeedPage(boolean z);

    void loadFirstFeedItemsInCookbookPage(String str, UltronCallback<TilePage> ultronCallback);

    void loadFirstHowToPages(VideoTag... videoTagArr);

    void loadFirstLikesPage(UltronCallback<TilePage> ultronCallback);

    void loadLikeIds();

    void loadLikesPage(int i, UltronCallback<TilePage> ultronCallback);

    void loadNextFeedItemsInCookbookPage(String str, String str2, UltronCallback<TilePage> ultronCallback);

    void loadNextHowTosPage(String str);

    void loadNextLikesPage(String str, UltronCallback<TilePage> ultronCallback);

    void loadRecipeOfTheDay();

    void loadRecipeRecommendations(String str);

    void loadRecommendations(String str);

    void loadSearchSuggestions();

    void loadSingleArticleById(String str);

    void loadSingleArticleBySlug(String str);

    void loadSingleRecipeById(String str);

    void loadSingleRecipeBySlug(String str);

    void loadSingleVideoById(String str);

    void loadSingleVideoBySlug(String str);

    void moveFeedItemToCookbook(String str, BaseFeedItem baseFeedItem, Cookbook cookbook);

    void registerWithEmail(UserUploadData userUploadData);

    void reportAbuse(String str, String str2, String str3);

    void saveCookbook(CookbookUploadData cookbookUploadData);

    void saveFeedItemToCookbook(BaseFeedItem baseFeedItem, Cookbook cookbook);

    void saveLike(BaseFeedItem baseFeedItem, UltronCallback<Void> ultronCallback);

    void sendInstallationData(Installation installation);

    void updateCookbook(Cookbook cookbook);

    void updateFeedItemRating(String str, float f);

    void updateNewsletterOptIn(NewsletterOptInData newsletterOptInData);
}
