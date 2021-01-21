package com.example.gph_kt_android_taks.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.gph_kt_android_taks.R
import com.example.gph_kt_android_taks.data.entities.User
import com.example.gph_kt_android_taks.data.local.AppSharedPreferences
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var user : User

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val navController = Navigation.findNavController(requireView())
        user = AppSharedPreferences.getLoggedUser(requireContext())!!

        initProfileView(user)

        button_courses.apply {
            setOnClickListener {
                navController.navigate(R.id.action_navigation_home_to_coursesFragment)
            }
        }

        button_logout.apply {
            setOnClickListener {
                navController.navigateUp()
                AppSharedPreferences.clearLoggedUser(requireContext())
            }
        }
    }
    private fun initProfileView (user: User){

        tv_name.text = user.name
        tv_id.text = user.identity
        tv_phone.text = user.phone
        tv_email.text = user.email

    }
}