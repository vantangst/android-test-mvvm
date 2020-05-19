package test.com.androidtestmvvm.ui.base

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.annotation.Nullable
import test.com.androidtestmvvm.utils.CommonUtils
import test.com.androidtestmvvm.utils.NetworkUtils


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity() {
    private var progressDialog: Dialog? = null
    private lateinit var viewDataBinding: T
    private var viewModel: V? = null

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

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    open fun getViewDataBinding(): T {
        return viewDataBinding
    }

    open fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView<T>(this, getLayoutId())
        this.viewModel = if (viewModel == null) getViewModel() else viewModel
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()
    }
}