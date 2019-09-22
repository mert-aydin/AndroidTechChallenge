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

    companion object {
        lateinit var sharedPref: SharedPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getPreferences(Context.MODE_PRIVATE)

        // checks if "remember me" is checked and navigate to OrdersActivity if checked
        if (sharedPref.getBoolean(getString(R.string.remember_me_key), false)) {
            startActivity(Intent(this, OrdersActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_login)

        userNameET = findViewById(R.id.user_name_et)
        passwordET = findViewById(R.id.password_et)
        rememberMeSwitch = findViewById(R.id.remember_me_switch)

    }

    fun login(view: View) {
        // saves user's choice of "remember me"
        with(sharedPref.edit()) {
            putBoolean(getString(R.string.remember_me_key), rememberMeSwitch.isChecked)
            commit()
        }

        val userName = userNameET.text.toString()
        val password = passwordET.text.toString()

        // checks login credentials and do login if correct
        if (userName == getString(R.string.login_credentials_username) && password == getString(R.string.login_credentials_password)) {
            startActivity(Intent(this, OrdersActivity::class.java))
            finish()
        }

    }
}
