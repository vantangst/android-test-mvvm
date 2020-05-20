package test.com.androidtestmvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import test.com.androidtestmvvm.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bundle = intent.getBundleExtra("user")
        bundle?.let {
            tvHello.text = String.format("Hello %s", it.getString("user_name", "There"))
        }
    }
}
