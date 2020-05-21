package test.com.androidtestmvvm.ui.base

import test.com.androidtestmvvm.utils.ConnectionLiveData
import android.app.Dialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import test.com.androidtestmvvm.utils.CommonUtils


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {
    private var progressDialog: Dialog? = null
    private lateinit var viewDataBinding: T
    private var viewModel: V? = null
    private var connectionLiveData: ConnectionLiveData? = null

    fun hideLoading() {
        progressDialog?.let {
            if (it.isShowing) {
                it.cancel()
            }
        }
    }

    fun showLoading() {
        hideLoading()
        progressDialog = CommonUtils().showLoadingDialog(this)
        progressDialog?.show()
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
        observeNetwork()
    }

    private fun observeNetwork() {
        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData?.observe(this, Observer {
            viewModel?.isNetworkConnected = it
        })
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