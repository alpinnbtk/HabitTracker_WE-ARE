package com.example.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittracker.databinding.FragmentNewHabitBinding
import com.example.habittracker.viewmodel.HabitViewModel
import com.example.habittracker.R
import com.example.habittracker.model.Habit

class NewHabitFragment : Fragment() {

    private lateinit var binding: FragmentNewHabitBinding
    private lateinit var viewModel: HabitViewModel

    private val iconNames = listOf("Fitness", "Study", "Nutrition", "Wellness")

    private val iconResources = listOf(
        R.drawable.muscle,
        R.drawable.book,
        R.drawable.food,
        R.drawable.wellness
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HabitViewModel::class.java]

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            iconNames
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerIcon.adapter = adapter

        binding.btnCreate.setOnClickListener {

            val name = binding.txtName.text.toString()
            val desc = binding.txtDesc.text.toString()
            val goal = binding.txtGoal.text.toString()
            val unit = binding.txtUnit.text.toString()
            val icon = iconResources[binding.spinnerIcon.selectedItemPosition]


            val habit = Habit(
                name,
                desc,
                goal.toInt(),
                unit,
                0,
                icon,
            )

            viewModel.addHabit(habit)

            findNavController().popBackStack()
        }
    }
}