package test.com.androidtestmvvm.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import test.com.androidtestmvvm.BR
import test.com.androidtestmvvm.ui.main.MainActivity
import test.com.androidtestmvvm.R
import test.com.androidtestmvvm.databinding.ActivityLoginBinding
import test.com.androidtestmvvm.ui.base.BaseActivity


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var activityLoginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = getViewDataBinding()
        setUpObserve()
    }

    private fun setUpObserve() {
        val isLoadingCallback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, i: Int) {
                val status: Boolean? = (observable as ObservableField<Boolean>).get()
                if (status != null && status) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }
        loginViewModel.isLoadingDialog?.addOnPropertyChangedCallback(isLoadingCallback)

        loginViewModel.toastMessage?.observe(this, Observer { msg ->
            when (msg) {
                is String -> {
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                }
                is Int -> {
                    Toast.makeText(this, getString(msg), Toast.LENGTH_SHORT).show()
                }
            }
        })

        loginViewModel.uiEventLiveData.observe(this, Observer {
            when (it?.second) {
                1 -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("user", it.first)
                    startActivity(intent)
                    finish()
                }
            }

        })

        loginViewModel.errorData?.observe(this, Observer { error ->
            Log.e(javaClass.simpleName, "handleError() - ${error?.message}")
            Toast.makeText(this,
                if (error == null) getString(R.string.error_something_went_wrong) else error.localizedMessage,
                Toast.LENGTH_SHORT).show()
        })
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun getViewModel(): LoginViewModel {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        return this.loginViewModel
    }
}
