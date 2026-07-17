package com.example.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittracker.databinding.FragmentEditHabitBinding
import com.example.habittracker.viewmodel.EditHabitViewModel

class EditHabitFragment : Fragment() {

    private lateinit var binding: FragmentEditHabitBinding
    private lateinit var viewModel: EditHabitViewModel

    private val iconNames = listOf("Fitness", "Study", "Nutrition", "Wellness")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[EditHabitViewModel::class.java]

        val habitId = arguments?.getInt("habitId") ?: -1

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            iconNames
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerIcon.adapter = adapter

        if (habitId != -1) {
            viewModel.loadHabit(habitId)
        }

        viewModel.habitLD.observe(viewLifecycleOwner) { habit ->
            binding.habit = habit
        }

        viewModel.selectedIconIndex.observe(viewLifecycleOwner) { index ->
            binding.spinnerIcon.setSelection(index)
        }

        viewModel.updateSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                findNavController().popBackStack()
            }
        }

        binding.btnSubmit.setOnClickListener {
            val goalInput = binding.txtGoal.text.toString().toIntOrNull() ?: 0
            viewModel.habitLD.value?.goal = goalInput
            viewModel.updateHabit(binding.spinnerIcon.selectedItemPosition)
        }
    }
}