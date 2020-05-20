package test.com.androidtestmvvm.ui.login.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import test.com.androidtestmvvm.BR
import test.com.androidtestmvvm.ui.MainActivity
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

    override fun showFailedMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun handleError(throwable: Throwable?) {
        Log.e(javaClass.simpleName, "handleError() - ${throwable?.message}")
        Toast.makeText(this,
            if (throwable == null) getString(R.string.error_something_went_wrong) else throwable.localizedMessage,
            Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(isShow: Boolean) {
        if (isShow) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    override fun login() {
        val email: String = activityLoginBinding.etEmail.text.toString()
        val password: String = activityLoginBinding.etPassword.text.toString()
        if (loginViewModel.isSignInDataValid(email, password)) {
            if (isNetworkConnected()) {
                loginViewModel.login(email, password)
            } else {
                Toast.makeText(this, getString(R.string.error_no_connect_connection), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, getString(R.string.error_email_and_password_login), Toast.LENGTH_SHORT).show()
        }
    }

    override fun openMainActivity(bundle: Bundle) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user", bundle)
        startActivity(intent)
        finish()
    }
}
