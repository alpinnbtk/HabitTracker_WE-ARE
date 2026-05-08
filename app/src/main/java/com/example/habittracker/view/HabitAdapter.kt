package com.example.project1_weare.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.databinding.ItemHabitBinding
import com.example.habittracker.model.Habit
import com.example.habittracker.R
import com.example.habittracker.viewmodel.HabitViewModel

class HabitAdapter(val habitList: ArrayList<Habit>, private val viewModel: HabitViewModel)
    : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]

        holder.binding.txtName.text = habit.name
        holder.binding.txtDesc.text = habit.description
        holder.binding.txtProgress.text = "${habit.progress} / ${habit.goal}"
        holder.binding.txtUnit.text = habit.unit

        holder.binding.imgIcon.setImageResource(habit.icon)

        holder.binding.progressBar.max = habit.goal
        holder.binding.progressBar.progress = habit.progress


        if (habit.progress >= habit.goal) {
            holder.binding.txtStatus.text = "Completed"
            holder.binding.txtStatus.setBackgroundResource(R.drawable.bg_status_done)
            holder.binding.btnPlus.isEnabled = false
            holder.binding.btnMin.isEnabled = false
        }
        else
        {
            holder.binding.txtStatus.text = "In Progress"
            holder.binding.txtStatus.setBackgroundResource(R.drawable.bg_status)
        }

        holder.binding.btnPlus.setOnClickListener {
            if (habit.progress < habit.goal) {
                habit.progress++
                notifyItemChanged(position)
                viewModel.saveToFile()
            }
        }

        holder.binding.btnMin.setOnClickListener {
            if (habit.progress > 0) {
                habit.progress--
                notifyItemChanged(position)
                viewModel.saveToFile()
            }
        }
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    fun updateHabitList(newHabitList: ArrayList<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }

    class HabitViewHolder(var binding: ItemHabitBinding)
        : RecyclerView.ViewHolder(binding.root)
}
