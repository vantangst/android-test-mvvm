package test.com.androidtestmvvm.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import test.com.androidtestmvvm.R

class CommonUtils {
    fun showLoadingDialog(context: Context) : Dialog {
        val dialog = Dialog(context)
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
        dialog.setContentView(inflate)
        dialog.setCancelable(false)
        dialog.window?.let {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return dialog
    }
}