package com.neurafarm.drtaniamimic.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.neurafarm.drtaniamimic.R


object ViewUtils {
    @SuppressLint("ResourceAsColor")
    fun makeToast(context: Context, text: String): Toast =
        Toast.makeText(context, text, Toast.LENGTH_SHORT).apply {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                view?.backgroundTintList = context.resources.getColorStateList(R.color.secondaryColor)
                val textView = view?.findViewById<TextView>(android.R.id.message)
                if (textView != null){
                    textView.setTextColor(R.color.secondaryTextColor)
                    textView.gravity = Gravity.CENTER
                }
            }
        }

    fun invokeAfterDelay(function: () -> Unit, delay: Long = 1000) {
        Handler(Looper.getMainLooper()).postDelayed(function, delay)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getImage(c: Context, ImageName: String?): Drawable? {
        return c.resources.getDrawable(
            c.resources.getIdentifier(
                ImageName,
                "drawable",
                c.packageName
            )
        )
    }
}

fun View.onClick(clickListener: (View) -> Unit) {
    setOnClickListener(clickListener)
}

fun View.onLongClick(longClickListener: (View) -> Boolean) {
    setOnLongClickListener(longClickListener)
}

fun String.isEmailValid(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun EditText.checkInput(validator: (EditText) -> Unit) {
    onTextChanged { _, v ->
        validator(v)
    }
    onFocusChanged {
        validator(it)
    }
}

inline fun EditText.onTextChanged(crossinline textChangeListener: (String, EditText) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            textChangeListener(editable.toString(), this@onTextChanged)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}

inline fun EditText.onFocusChanged(crossinline focusChangeListener: (EditText) -> Unit) {
    onFocusChangeListener = object : View.OnFocusChangeListener {
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            focusChangeListener(v as EditText)
        }

    }
}

fun EditText.setErrorLayout(errorMsg: String) {
    val parent = (parent as FrameLayout).parent as TextInputLayout
    if (parent.error.isNullOrEmpty())
        parent.error = errorMsg
}

fun EditText.checkIfEmptyWithError(errorMsg: String): Boolean =
    if (text.toString().isEmpty()) {
        val parent = (parent as FrameLayout).parent as TextInputLayout
        if (parent.error.isNullOrEmpty())
            parent.error = errorMsg
        true
    } else {
        resetErrorMessage()
        false
    }

fun TextView.checkIfEmptyWithError(errorMsg: String): Boolean =
    if (text.isEmpty()) {
        ViewUtils.makeToast(context, errorMsg)
        true
    } else {
        false
    }

fun EditText.checkIfEmailValidWithError(errorMsg: String): Boolean =
    if (!text.toString().isEmailValid()) {
        val parent = (parent as FrameLayout).parent as TextInputLayout
        if (parent.error.isNullOrEmpty())
            parent.error = errorMsg
        false
    } else {
        resetErrorMessage()
        true
    }

fun EditText.checkIfNotEmpty(): Boolean =
    text.toString().isNotEmpty()

fun EditText.checkIfEmpty(): Boolean =
    text.toString().isEmpty()

fun EditText.checkIfEmailValid(): Boolean =
    text.toString().isEmailValid()

fun EditText.resetErrorMessage() {
    val parent = (parent as FrameLayout).parent as TextInputLayout
    parent.error = null
}

fun EditText.setReadOnly(value: Boolean = true, inputType: Int = InputType.TYPE_NULL) {
    isFocusable = !value
    isFocusableInTouchMode = !value
    this.inputType = inputType
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

inline fun <T : View> T.showIf(condition: (T) -> Boolean): T {
    if (condition(this)) {
        show()
    } else {
        hide()
    }

    return this
}

inline fun <T : View> T.hideIf(condition: (T) -> Boolean): T {
    if (condition(this)) {
        hide()
    } else {
        show()
    }

    return this
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

@SuppressLint("ServiceCast")
fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}