package com.promiseland.ustory.service.api;

import com.ajnsnewmedia.kitchenstories.base.model.shopping.MiniUnifiedShoppingList;
import com.ajnsnewmedia.kitchenstories.base.model.sqlite.Ingredient;
import com.ajnsnewmedia.kitchenstories.service.base.CustomService;
import com.ajnsnewmedia.kitchenstories.ultron.model.recipe.Recipe;
import java.util.List;

public interface ShoppingListService extends CustomService {
    void addShoppingListItem(Recipe recipe, float f);

    void deleteShoppingList(String str);

    void loadAggregatedShoppingList();

    void loadShoppingList(String str);

    List<MiniUnifiedShoppingList> loadShoppingLists();

    void updateIngredientBoughtState(Ingredient ingredient, boolean z);
}
