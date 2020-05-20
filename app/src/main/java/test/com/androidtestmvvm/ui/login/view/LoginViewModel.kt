package test.com.androidtestmvvm.ui.login.view

import android.os.Bundle
import android.text.TextUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import test.com.androidtestmvvm.data.network.DemoServices
import test.com.androidtestmvvm.ui.base.BaseViewModel
import test.com.androidtestmvvm.utils.CommonUtils

class LoginViewModel : BaseViewModel<LoginNavigator>(){

    fun isSignInDataValid(email: String, password: String) : Boolean{
        if (TextUtils.isEmpty(email)) {
            return false
        }
        if (!CommonUtils().isEmailValid(email)) {
            return false
        }
        if (TextUtils.isEmpty(password)) {
            return false
        }
        return true
    }

    fun login(email: String, password: String) {
        getNavigator()?.showLoading(true)
        getCompositeDisposable()?.add(DemoServices.create().login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    getNavigator()?.showLoading(false)
                    if(response.result) {
                        val bundle = Bundle()
                        bundle.putString("user_name", response.data.name)
                        getNavigator()?.openMainActivity(bundle)
                    } else {
                        getNavigator()?.showFailedMessage(response.message)
                    }
                },

                { error ->
                    getNavigator()?.showLoading(false)
                    getNavigator()?.handleError(error)
                }
            ))
    }

    fun onServerLoginClick() {
        getNavigator()?.login()
    }
}