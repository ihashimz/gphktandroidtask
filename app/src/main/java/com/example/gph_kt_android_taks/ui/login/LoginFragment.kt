package com.example.gph_kt_android_taks.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.gph_kt_android_taks.R
import com.example.gph_kt_android_taks.data.local.AppSharedPreferences
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginFragment : Fragment(), CoroutineScope {
    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mJob = Job()
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        val navController = Navigation.findNavController(requireView())

        button_login.apply {
            setOnClickListener {
                doLogin(navController)
            }
        }

        tv_register.apply {
            setOnClickListener {
                navController.navigate(R.id.action_loginFragment_to_signupFragment)
            }
        }
    }

    private fun doLogin(navController: NavController) {

        viewModel.getUser(requireContext(), ed_name.editText!!.text.toString(),ed_password.editText!!.text.toString())!!
            .observe(viewLifecycleOwner,
                Observer {
                    it?.let {
                        AppSharedPreferences.storeLoggedUser(requireContext(),it)
                        navController.navigate(R.id.action_loginFragment_to_navigation_home)
                        it.identity
                        viewModel.user = MutableLiveData()
                    }
                    if (it==null) Toast.makeText(requireContext(),"تاكد من صحة البيانات المدخلة", Toast.LENGTH_SHORT).show()
                })
    }

}