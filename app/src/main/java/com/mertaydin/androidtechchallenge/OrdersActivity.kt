package com.mertaydin.androidtechchallenge

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.mertaydin.androidtechchallenge.LoginActivity.Companion.sharedPref

class OrdersActivity : AppCompatActivity() {

    private lateinit var queue: RequestQueue

    companion object {
        private var orders: List<Order> = ArrayList()
        private lateinit var recyclerView: RecyclerView

        fun scrollToBottom() {
            recyclerView.scrollToPosition(orders.size - 1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        queue = Volley.newRequestQueue(this)

        fetchData()

    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    private fun fetchData() {
        val stringRequest = StringRequest(Request.Method.GET, getString(R.string.API_URL),
            Response.Listener<String> { response ->
                orders = Klaxon().parseArray(response)!!

                val recyclerViewAdapter = RecyclerViewAdapter(orders)
                recyclerView.adapter = recyclerViewAdapter
                findViewById<RelativeLayout>(R.id.loadingPanel).visibility = View.GONE
            },
            Response.ErrorListener {
            })

        queue.add(stringRequest)
    }

    fun logout(view: View) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.logout))
            .setMessage(getString(R.string.logout_validation))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->

                with(sharedPref.edit()) {
                    putBoolean(getString(R.string.remember_me_key), false)
                    commit()
                }
                startActivity(Intent(view.context, LoginActivity::class.java))
                finish()
            }
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .show()
    }

}
