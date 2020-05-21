package test.com.androidtestmvvm.ui.login

import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import test.com.androidtestmvvm.R
import test.com.androidtestmvvm.data.model.api.User
import test.com.androidtestmvvm.data.network.DemoServices
import test.com.androidtestmvvm.ui.base.BaseViewModel
import test.com.androidtestmvvm.utils.CommonUtils


class LoginViewModel : BaseViewModel() {

    var email: ObservableField<String>? = null
    var password: ObservableField<String>? = null
    var loginUserData: MutableLiveData<User>? = null
    var errorData: MutableLiveData<Throwable>? = null

    init {
        email = ObservableField("")
        password = ObservableField("")
        loginUserData = MutableLiveData<User>()
        errorData = MutableLiveData<Throwable>()
    }

    fun isSignInDataValid(email: String?, password: String?) : Boolean {
        if (TextUtils.isEmpty(email)) {
            return false
        }
        if (!CommonUtils().isEmailValid(email!!)) {
            return false
        }
        if (TextUtils.isEmpty(password)) {
            return false
        }
        return true
    }

    fun login(email: String, password: String) {
        isLoadingDialog?.set(true)
        getCompositeDisposable()?.add(DemoServices.create().login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    isLoadingDialog?.set(false)
                    loginUserData?.value = response.data
                    if(response.result) {
                        val bundle = Bundle()
                        bundle.putString("user_name", response.data.name)
                        uiEventLiveData.value = bundle to 1
                    } else {
                        toastMessage?.value = response.message
                    }
                },

                { error ->
                    isLoadingDialog?.set(false)
                    errorData?.value = error
                }
            ))
    }

    fun onServerLoginClick() {
        if (isSignInDataValid(email?.get(), password?.get())) {
            if (isNetworkConnected) {
                login(email?.get()!!, password?.get()!!)
            } else {
                toastMessage?.value = R.string.error_no_connect_connection
            }
        } else {
            toastMessage?.value = R.string.error_email_and_password_login
        }
    }
}