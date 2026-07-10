package com.example.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.databinding.FragmentDashboardBinding
import com.example.habittracker.viewmodel.HabitViewModel
import androidx.navigation.fragment.findNavController
import com.example.habittracker.view.HabitAdapter

class DashboardFragment : Fragment() {

    private lateinit var viewModel: HabitViewModel
    private lateinit var habitAdapter: HabitAdapter
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(requireActivity())[HabitViewModel::class.java]

//        viewModel.loadFromFile()
        viewModel.loadHabits()

        habitAdapter = HabitAdapter(arrayListOf(), viewModel)

        binding.recyclerHabits.layoutManager = LinearLayoutManager(context)
        binding.recyclerHabits.adapter = habitAdapter


        observeViewModel()


        binding.fabAdd.setOnClickListener {
            findNavController().navigate(
                com.example.habittracker.R.id.actionNewHabit
            )
        }
    }

    private fun observeViewModel() {
        viewModel.habits.observe(viewLifecycleOwner, Observer {
            habitAdapter.updateHabitList(ArrayList(it))
        })


    }
}
