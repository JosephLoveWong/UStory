package com.promiseland.ustory.base.model.ui.user

import com.promiseland.ustory.base.event.ErrorEvent

/**
 * Created by joseph on 2018/3/11.
 */
class UserUiModelError(private val error: ErrorEvent) : UserUiModel {

    fun getError(): ErrorEvent {
        return this.error
    }
}