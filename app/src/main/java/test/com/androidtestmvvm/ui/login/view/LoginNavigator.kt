package test.com.androidtestmvvm.ui.login.view

import android.os.Bundle

interface LoginNavigator {

    fun handleError(throwable: Throwable?)

    fun showFailedMessage(message: String)

    fun showLoading(isShow: Boolean)

    fun login()

    fun openMainActivity(bundle: Bundle)
}