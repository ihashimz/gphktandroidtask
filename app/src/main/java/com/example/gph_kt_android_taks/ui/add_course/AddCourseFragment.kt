package com.example.gph_kt_android_taks.ui.add_course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.afollestad.date.dayOfMonth
import com.afollestad.date.month
import com.afollestad.date.year
import com.afollestad.vvalidator.form
import com.example.gph_kt_android_taks.R
import com.example.gph_kt_android_taks.data.entities.Course
import com.example.gph_kt_android_taks.data.local.AppSharedPreferences
import com.example.gph_kt_android_taks.ui.courses_log.CoursesViewModel
import kotlinx.android.synthetic.main.add_course_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext


class AddCourseFragment : Fragment(), CoroutineScope {
    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main


    companion object {
        fun newInstance() = AddCourseFragment()
    }

    private lateinit var viewModel: CoursesViewModel
    private lateinit var navController: NavController
    private lateinit var date: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_course_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        mJob = Job()
        super.onActivityCreated(savedInstanceState)
        navController = Navigation.findNavController(requireView())
        viewModel = ViewModelProvider(requireActivity()).get(CoursesViewModel::class.java)

        initSpinner()
        initForms()

        back_btn.apply {
            setOnClickListener {
                navController.navigateUp()
            }
        }

    }

    private fun initSpinner() {
        val cities = arrayOf(
            "مكة المكرمة",
            "جدة",
            "ينبع",
            "المدينة المنورة",
            "الطائف"
        )
        val spin = requireView().findViewById(R.id.select_city) as Spinner
        val adapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, cities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = adapter
    }

    private fun initForms() {
        form {
            inputLayout(R.id.ed_course_name, "name") {
                isNotEmpty().description(getString(R.string.required_field))
            }
            spinner(R.id.select_city, "city") {}

            inputLayout(R.id.ed_provider_name, "provider") {
                isNotEmpty().description(getString(R.string.required_field))
            }

            inputLayout(R.id.ed_course_duration, "duration") {
                isNotEmpty().description(getString(R.string.required_field))
            }

            inputLayout(R.id.ed_course_rate, "rate") {
                isNotEmpty().description(getString(R.string.required_field))
            }
            submitWith(R.id.button_save) { result ->

                val course = Course(
                    UUID.randomUUID().leastSignificantBits,
                    AppSharedPreferences.getLoggedUser(requireContext())!!.identity,
                    result["name"]!!.value.toString(),
                    result["provider"]!!.value.toString(),
                    6,
                    result["rate"]!!.value.toString(),
                    result["city"]!!.value.toString(),
                    "${datePicker.getDate()!!.dayOfMonth}/${datePicker.getDate()!!.month + 1}/${datePicker.getDate()!!.year}"

                )

                launch {
                    viewModel.insertCourse(requireContext(), course)
                    navController.navigateUp()

                }
            }
        }

    }
}