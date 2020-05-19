package test.com.androidtestmvvm.ui.login.view

import android.content.Context
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
        getCompositeDisposable()?.add(DemoServices.create().login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response != null ) {
                        if(response.result) {
                            //success
                        } else {
                            //error
                        }
                    }
                    else {
                        //error
                    }
                    //hideDialogProgress()
                },

                { error ->
                    //hideDialogProgress()
                    //show error
                }
            ))
    }

    fun onServerLoginClick() {
        getNavigator()?.login()
    }
}