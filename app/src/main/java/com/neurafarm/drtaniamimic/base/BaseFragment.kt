package com.neurafarm.drtaniamimic.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.neurafarm.drtaniamimic.common.Constants
import com.neurafarm.drtaniamimic.data.sharedpreferences.AppSharedPreferencesImpl
import com.neurafarm.drtaniamimic.feature.MainActivity
import com.neurafarm.drtaniamimic.utils.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment constructor(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    @Inject
    protected lateinit var appSharedPreferences: AppSharedPreferencesImpl

    protected val parentActivity by lazy {
        requireActivity() as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    fun setTitleBar(title: String) {
        parentActivity.supportActionBar?.title = title
    }

    fun setupAppBar(toolbar: Toolbar, title: String, isBackEnable: Boolean) {
        parentActivity.setSupportActionBar(toolbar)
        parentActivity.supportActionBar?.title = title
        parentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(isBackEnable)
        parentActivity.supportActionBar?.setDisplayShowHomeEnabled(isBackEnable)
    }

    @SuppressLint("ResourceAsColor", "UseCompatLoadingForColorStateLists")
    fun showToast(text: String) {
        ViewUtils.makeToast(requireContext(), text).show()
    }

    protected fun logout(){
        appSharedPreferences.clear(Constants.LOGINED_ACCOUNT)
        appSharedPreferences.clear(Constants.IS_LOGINED)
    }
}