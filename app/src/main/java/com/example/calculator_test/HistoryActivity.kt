package com.example.calculator_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        //Adds a back button to the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //Gets data from MainActivity
        val history = mutableListOf<Equation>()
        val data = intent.getStringExtra("EXTRA_HISTORY")
        history.addAll(receiveHistory(data))

        //Places data to Recycler View
        val rvHistory = findViewById<RecyclerView>(R.id.rvHistory)

        val adapter = HistoryAdapter(history)
        rvHistory.adapter = adapter
        rvHistory.layoutManager = LinearLayoutManager(this)
    }

    //Aforementioned actionbar functionality
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun receiveHistory(data: String?): List<Equation> {
        return if (data != "") {
            val type = object : TypeToken<MutableList<Equation>>() {}.type
            val list = Gson().fromJson<List<Equation>>(data, type)
            Log.d("Receieved Data: ", list.toString())
            return list
        } else {
            mutableListOf<Equation>()
        }
    }
}