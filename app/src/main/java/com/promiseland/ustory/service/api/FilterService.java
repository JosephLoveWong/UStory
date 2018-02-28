package com.promiseland.ustory.service.api;

import com.ajnsnewmedia.kitchenstories.service.base.CustomService;
import com.ajnsnewmedia.kitchenstories.ultron.model.search.SearchFilterQuery;

public interface FilterService extends CustomService {
    void changeRecipeFilterValue(String str, boolean z);

    boolean isRecipeFilterActive(String str);

    boolean isRecipeFilterActive(boolean z);

    String loadFirstFilteredPage(SearchFilterQuery searchFilterQuery);

    String loadNextFilteredPage(SearchFilterQuery searchFilterQuery, int i);

    void resetRecipeFilter();
}
