package test.com.androidtestmvvm.ui.login.view

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import test.com.androidtestmvvm.BR
import test.com.androidtestmvvm.R
import test.com.androidtestmvvm.databinding.ActivityLoginBinding
import test.com.androidtestmvvm.ui.base.BaseActivity


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator{

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var activityLoginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = getViewDataBinding()
        loginViewModel.setNavigator(this)
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

    override fun handleError(throwable: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun login() {
        val email: String = activityLoginBinding.etEmail.text.toString()
        val password: String = activityLoginBinding.etPassword.text.toString()
        if (loginViewModel.isSignInDataValid(email, password)) {
            loginViewModel.login(email, password)
        } else {
            Toast.makeText(this, "getString(R.string.invalid_email_password)", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun openMainActivity() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
