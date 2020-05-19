package test.com.androidtestmvvm.ui.base

import android.app.Dialog
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import test.com.androidtestmvvm.utils.CommonUtils
import test.com.androidtestmvvm.utils.NetworkUtils


open class BaseActivity : AppCompatActivity() {
    private var progressDialog: Dialog? = null

    fun hideLoading() {
        progressDialog?.let {
            if (it.isShowing) {
                it.cancel()
            }
        }
    }

    fun isNetworkConnected(): Boolean {
        return NetworkUtils().isNetworkConnected(applicationContext)
    }

    fun showLoading() {
        hideLoading()
        progressDialog = CommonUtils().showLoadingDialog(this)
    }
}