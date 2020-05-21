package test.com.androidtestmvvm.ui.base

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import test.com.androidtestmvvm.utils.SingleLiveData


abstract class BaseViewModel : ViewModel() {
    private var compositeDisposable: CompositeDisposable? = null
    var isLoadingDialog: ObservableField<Boolean>? = null
    var toastMessage: MutableLiveData<Any>? = null
    val uiEventLiveData = SingleLiveData<Pair<Bundle, Int>>()
    var isNetworkConnected = false


    init {
        compositeDisposable = CompositeDisposable()
        isLoadingDialog = ObservableField(false)
        toastMessage = MutableLiveData<Any>()
    }

    override fun onCleared() {
        compositeDisposable?.dispose()
        super.onCleared()
    }

    fun getCompositeDisposable(): CompositeDisposable? {
        return compositeDisposable
    }
}