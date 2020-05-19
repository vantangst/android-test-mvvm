package test.com.androidtestmvvm.ui.login.view

interface LoginNavigator {
    fun handleError(throwable: Throwable?)

    fun login()

    fun openMainActivity()
}