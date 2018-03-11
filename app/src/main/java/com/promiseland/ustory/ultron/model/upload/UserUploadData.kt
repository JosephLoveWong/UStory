package com.promiseland.ustory.ultron.model.upload

/**
 * Created by joseph on 2018/3/11.
 */
class UserUploadData
(private val username: String?, private val email: String?, private val password: String?, private val installation_id: String?)
{

    constructor(username: String?, email: String?, password: String?) : this(username, email, password, null)

    constructor(username: String?, email: String?) : this(username, email, null)

    constructor(username: String?) : this(username, null)

    constructor() : this(null)

    fun getUsername(): String? {
        return this.username
    }

    fun getEmail(): String? {
        return this.email
    }

    fun getPassword(): String? {
        return this.password
    }

    fun getInstallation_id(): String? {
        return this.installation_id
    }
}