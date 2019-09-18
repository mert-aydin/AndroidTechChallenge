package com.mertaydin.androidtechchallenge

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var userNameET: TextInputEditText
    private lateinit var passwordET: TextInputEditText
    private lateinit var rememberMeSwitch: Switch
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getPreferences(Context.MODE_PRIVATE)

        if (sharedPref.getBoolean(getString(R.string.remember_me_key), false)) {
            finish()
            startActivity(Intent(this, OrdersActivity::class.java))
        }

        setContentView(R.layout.activity_login)

        userNameET = findViewById(R.id.user_name_et)
        passwordET = findViewById(R.id.password_et)
        rememberMeSwitch = findViewById(R.id.remember_me_switch)

    }

    fun login(view: View) {

        with(sharedPref.edit()) {
            putBoolean(getString(R.string.remember_me_key), rememberMeSwitch.isChecked)
            commit()
        }

        val userName = userNameET.text.toString()
        val password = passwordET.text.toString()

        if (userName == "kariyer" && password == "2019ADev")

            startActivity(Intent(this, OrdersActivity::class.java))

    }
}
