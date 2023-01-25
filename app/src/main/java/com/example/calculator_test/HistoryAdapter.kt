package com.example.calculator_test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
//NOTE: to use this import, I put a 'buildFeatures' in 'build.gradle' under 'android'
import com.example.calculator_test.databinding.ItemHistoryBinding

class HistoryAdapter (private var equations: List<Equation>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    //Essentially a constructor?
    inner class HistoryViewHolder (val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    //puts the views on recyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(layoutInflater, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.binding.apply {
            tvEquation.text = equations[position].equation
            tvAnswer.text = equations[position].answer.toString()
        }
    }

    override fun getItemCount(): Int {
        return equations.size
    }
}