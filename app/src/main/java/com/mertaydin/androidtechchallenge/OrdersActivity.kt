package com.mertaydin.androidtechchallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon

class OrdersActivity : AppCompatActivity() {

    private lateinit var queue: RequestQueue

    companion object {
        private var orders: List<Order> = ArrayList()
        private lateinit var recyclerView: RecyclerView

        fun scrollToBottom() {
            println("scrollToBottom")
            recyclerView.scrollToPosition(orders.size - 1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        recyclerView = findViewById(R.id.recycler_view)
        queue = Volley.newRequestQueue(this)

        if (orders.isEmpty())
            fetchData()

    }

    private fun fetchData() {
        val stringRequest = StringRequest(Request.Method.GET, getString(R.string.API_URL),
            Response.Listener<String> { response ->
                orders = Klaxon().parseArray(response)!!

                val recyclerViewAdapter = RecyclerViewAdapter(orders)
                recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                recyclerView.adapter = recyclerViewAdapter
            },
            Response.ErrorListener {
                println("Error")
            })

        queue.add(stringRequest)
    }

}
