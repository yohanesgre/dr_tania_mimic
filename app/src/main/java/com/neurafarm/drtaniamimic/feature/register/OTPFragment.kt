package com.neurafarm.drtaniamimic.feature.register

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import com.mukesh.OnOtpCompletionListener
import com.neurafarm.drtaniamimic.R
import com.neurafarm.drtaniamimic.base.BaseFragment
import com.neurafarm.drtaniamimic.databinding.FragmentRegisterOtpBinding
import com.neurafarm.drtaniamimic.feature.NavigationViewModel
import com.neurafarm.drtaniamimic.utils.ViewUtils
import com.neurafarm.drtaniamimic.utils.onClick


class OTPFragment : BaseFragment(R.layout.fragment_register_otp) {

    private val viewModelNav by viewModels<NavigationViewModel>()

    private lateinit var binding: FragmentRegisterOtpBinding

    var otpCompleted = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterOtpBinding.bind(view)
        with(binding) {
            setupAppBar(appBarLayout.toolbar, "Phone Number", true)
            otpView.setOtpCompletionListener {
                if (it.length == 6) {
                    otpCompleted = true
                    btnNext.isEnabled = true
                }
            }
            btnNext.onClick {
                if (otpCompleted) {
                    viewModelNav.navigateOTPFragmentToAddInformationFragment()
                }
            }
            ViewUtils.invokeAfterDelay({ otpView.setText("123456") }, 2000)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                viewModelNav.navigateBack()
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
