package com.blackholecode.saudedigital.login.data

import com.blackholecode.saudedigital.common.base.RequestCallback
import com.blackholecode.saudedigital.common.model.User

class LoginRepository(private val dataSourceFactory: LoginDataSourceFactory) {

    fun login(email: String, password: String, callback: RequestCallback<User>) {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        val remoteDataSource = dataSourceFactory.createRemoteDataSource()

        remoteDataSource.login(email, password, object : RequestCallback<User> {
            override fun onSuccess(data: User?) {
                data?.let { localDataSource.putCache(it) }
                callback.onSuccess(data)
            }

            override fun onFailure(message: String?) {
                callback.onFailure(message)
            }

            override fun onComplete() {
                callback.onComplete()
            }
        })
    }

}