package com.promiseland.ustory.service.api;

import com.ajnsnewmedia.kitchenstories.base.model.sqlite.Ingredient;
import com.ajnsnewmedia.kitchenstories.service.base.CustomService;
import com.ajnsnewmedia.kitchenstories.ultron.model.recipe.FeedIngredient;

public interface UtilityService extends CustomService {
    String getAmountString(FeedIngredient feedIngredient, int i, float f);

    String getCalculatedLabelFor(Ingredient ingredient);

    String getCalculatedLabelFor(FeedIngredient feedIngredient, int i, float f);

    String getUnitString(FeedIngredient feedIngredient, int i, float f);

    void startTimer(long j);

    void stop();
}
