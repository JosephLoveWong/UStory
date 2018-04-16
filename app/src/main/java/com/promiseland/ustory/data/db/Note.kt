package com.promiseland.ustory.data.db

import java.util.*

/**
 * Created by Administrator on 2018/2/24.
 */
//@Entity
data class Note(
//        @Id var id: Long = 0,
        var text: String? = null,
        var comment: String? = null,
        var date: Date? = null
)