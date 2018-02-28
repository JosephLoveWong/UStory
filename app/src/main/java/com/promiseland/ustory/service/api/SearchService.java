package com.promiseland.ustory.service.api;

import com.algolia.search.saas.Index;

public interface SearchService {
    Index getAlgoliaContentIndex(String str);

    void searchInAlgoliaContent(String str, int i);

    void searchInAlgoliaThirdParty(String str);

    void searchInAlgoliaVideos(String str, int i);
}
