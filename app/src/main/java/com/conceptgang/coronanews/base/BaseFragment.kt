package com.conceptgang.coronanews.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.BaseMvRxFragment
import com.conceptgang.coronanews.MainActivity


abstract class BaseFragment : BaseMvRxFragment() {

    protected val mainActivity by lazy { requireActivity() as MainActivity }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}


/**
 * https://stackoverflow.com/a/49147787/6307259
 * If no window token is found, keyboard is checked using
 * reflection to know if keyboard visibility toggle is needed
 *
 * @param useReflection - whether to use reflection in case
 * of no window token or not
 */
fun Fragment.hideKeyboard(context: Context = requireContext(), useReflection: Boolean = true) {
    val windowToken = view?.rootView?.windowToken
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    windowToken?.let {
        imm.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    } ?: run {
        if (useReflection) {
            try {
                if (getKeyboardHeight(imm) > 0) {
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
                }
            } catch (exception: Exception) {

            }
        }
    }
}

/*
*
* https://stackoverflow.com/a/55401335/6307259
* */

fun Fragment.showKeyboard(view: View) {
    if (view.requestFocus()) {
        val imm =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        //imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }
}

fun getKeyboardHeight(imm: InputMethodManager): Int =
    InputMethodManager::class
        .java
        .getMethod("getInputMethodWindowVisibleHeight")
        .invoke(imm) as Int