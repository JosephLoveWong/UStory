package com.promiseland.ustory.module;

import dagger.Subcomponent;

@BaseActivityScoped
@Subcomponent(modules = BaseActivityModule.class)
public interface BaseActivityComponent {
//    void inject(CommentDetailPresenter commentDetailPresenter);

    BaseFragmentComponent plus(BaseFragmentModule baseFragmentModule);
}
