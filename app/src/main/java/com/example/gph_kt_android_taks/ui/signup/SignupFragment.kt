package com.example.gph_kt_android_taks.ui.signup

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.afollestad.vvalidator.form
import com.example.gph_kt_android_taks.R
import com.example.gph_kt_android_taks.common.ImageByteArray
import com.example.gph_kt_android_taks.data.entities.User
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.signup_fragment.*
import kotlinx.coroutines.*
import java.io.File
import java.io.FileNotFoundException
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class SignupFragment : Fragment() , CoroutineScope , PermissionListener {
    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
    get() = mJob + Dispatchers.Main

    companion object {
        fun newInstance() = SignupFragment()
    }

    private lateinit var viewModel: SignupViewModel
    private var profile_image: File ? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signup_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mJob = Job()
        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        val navController = Navigation.findNavController(requireView())


        // Form handling
        initForm()

        viewModel.signUpError.observe(viewLifecycleOwner, Observer {
            if (it=="ERROR"){
                Toast.makeText(requireContext(),"تم تسجيلك مسبقاً",Toast.LENGTH_SHORT).show()
            } else {
                navController.navigateUp()
                viewModel.signUpError = MutableLiveData()
            }
        })


    }

    fun initForm() {
        form {
            inputLayout(R.id.ed_id, "identity") {
                isNotEmpty().description(getString(R.string.required_field))
            }
            inputLayout(R.id.ed_name, "name") {
                isNotEmpty().description(getString(R.string.required_field))
            }

            inputLayout(R.id.ed_phone, "phone") {
                isNotEmpty().description(getString(R.string.required_field))
                matches("(05)[0-9]{8}").description(getString(R.string.phone_error))
            }

            inputLayout(R.id.ed_email, "email") {
                isEmail().description(getString(R.string.invalid_input))
                isNotEmpty().description(getString(R.string.required_field))
            }

            inputLayout(R.id.ed_password, "password") {
                isNotEmpty().description(getString(R.string.required_field))

            }

            inputLayout(R.id.ed_conf_password, "re_password") {
                isNotEmpty().description(getString(R.string.required_field))
            }

            submitWith(R.id.button_signup){
                formResult ->
                // check on passwords
                if( formResult["password"]?.value.toString()== formResult["re_password"]?.value.toString()) {
                    val newUser = User(
                            email = formResult["email"]?.value.toString(),
                            password = formResult["password"]?.value.toString(),
                            name = formResult["name"]?.value.toString(),
                            identity = formResult["identity"]?.value.toString(),
                            phone = formResult["phone"]?.value.toString(),
                            image = ImageByteArray.fullyReadFileToBytes(profile_image)
                    )

                    launch {
                        try {
                            insertUser(newUser)
                        } catch (e:Exception){
                            print(e.toString())
                        }
                    }
                } else ed_conf_password.error = ("كلمة المرور غير متطابقة")
                



            }
            useRealTimeValidation(500)
        }

    }

    // Add new user
    suspend fun insertUser(user:User){
        viewModel.insertUser(requireContext(),user)
    }

    // Images handler
    private fun getReceiptImg() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) if (resultCode == Activity.RESULT_OK) {
            val selectedImage: Uri = data!!.data!!
            val filePath = getRealPathFromURI(selectedImage)
            val file_extn = filePath!!.substring(filePath.lastIndexOf(".") + 1)
            try {
                if (file_extn == "img" || file_extn == "jpg" || file_extn == "jpeg" || file_extn == "gif" || file_extn == "png") {
                    //FINE
                    profile_image = File(filePath)

                } else { //NOT IN REQUIRED FORMAT

                    Toast.makeText(this.context,"يرجي اختيار صيغية صحيحة",Toast.LENGTH_LONG).show()
                }
            } catch (e: FileNotFoundException) { // TODO Auto-generated catch block
                e.printStackTrace()
                Toast.makeText(this.context,e.printStackTrace().toString(),Toast.LENGTH_LONG).show()

            }
        }
    }


    fun getRealPathFromURI(uri: Uri): String? {
        val cursor: Cursor? = requireContext().getContentResolver().query(uri, null, null, null, null)
        cursor!!.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        getReceiptImg()
    }

    override fun onPermissionRationaleShouldBeShown(
            permission: PermissionRequest?,
            token: PermissionToken?
    ) {
        Toast.makeText(requireContext(),"يجب اعطاء الصلاحيات للتطبيق لإرفاق صورة الملف الشخصي",Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(requireContext(),"يجب اعطاء الصلاحيات للتطبيق لإرفاق صورة الملف الشخصي",Toast.LENGTH_SHORT).show()

    }
}