package test.com.androidtestmvvm.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference


abstract class BaseViewModel<N> : ViewModel() {
    private var compositeDisposable: CompositeDisposable? = null
    private var navigator: WeakReference<N>? = null

    init {
        compositeDisposable = CompositeDisposable()
    }

    override fun onCleared() {
        compositeDisposable?.dispose()
        super.onCleared()
    }

    fun getCompositeDisposable(): CompositeDisposable? {
        return compositeDisposable
    }

    open fun getNavigator(): N? {
        return navigator?.get()
    }

    open fun setNavigator(navigator: N) {
        this.navigator = WeakReference(navigator)
    }
}