package com.example.gph_kt_android_taks.ui.courses_log

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gph_kt_android_taks.R
import com.example.gph_kt_android_taks.data.entities.Course
import com.example.gph_kt_android_taks.data.local.AppSharedPreferences
import com.example.gph_kt_android_taks.ui.adapters.CoursesAdapter
import kotlinx.android.synthetic.main.courses_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class CoursesFragment : Fragment(), CoursesAdapter.Interaction , CoroutineScope {
    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    companion object {
        fun newInstance() = CoursesFragment()
    }

    private lateinit var viewModel: CoursesViewModel
    private lateinit var coursesAdapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.courses_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        mJob = Job()
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CoursesViewModel::class.java)
        val navController = Navigation.findNavController(requireView())

        initRecycleView()
        viewModel.getAllCourses(
            requireContext(),
            AppSharedPreferences.getLoggedUser(requireContext())!!.identity
        )!!.observe(viewLifecycleOwner,
            Observer {
                if (it.isNotEmpty()) tv_courses_empty.visibility = View.GONE
                coursesAdapter.submitList(it)
            })


        floatingAddButton.apply {
            setOnClickListener {
                navController.navigate(R.id.action_coursesFragment_to_addCourseFragment)
            }
        }
        back_btn.apply {
            setOnClickListener {
                navController.navigateUp()
            }
        }
    }

    // initializing the recycler
    private fun initRecycleView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            coursesAdapter = CoursesAdapter(this@CoursesFragment)
            adapter = coursesAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Course) {
        print(position)
    }

}