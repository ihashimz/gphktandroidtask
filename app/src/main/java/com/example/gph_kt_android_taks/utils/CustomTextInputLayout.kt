package com.example.gph_kt_android_taks.utils


/**
 * Created By @HashimHabtur on 17/06/2020 AD
 */


import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Nullable
import com.google.android.material.textfield.TextInputLayout


class CustomTextInputLayout : TextInputLayout {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context!!, attrs, defStyleAttr) {
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        clearEditTextColorfilter()

    }



    override fun setError(@Nullable error: CharSequence?) {
        super.setError(error)
        clearEditTextColorfilter()

    }

    private fun clearEditTextColorfilter() {
        val editText = editText
        if (editText != null) {
            val background = editText.background
            background?.clearColorFilter()
        }
    }
}