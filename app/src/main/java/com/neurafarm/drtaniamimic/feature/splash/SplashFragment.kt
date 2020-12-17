package com.neurafarm.drtaniamimic.feature.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.neurafarm.drtaniamimic.R
import com.neurafarm.drtaniamimic.base.BaseFragment
import com.neurafarm.drtaniamimic.common.Constants
import com.neurafarm.drtaniamimic.databinding.FragmentSplashBinding
import com.neurafarm.drtaniamimic.feature.NavigationViewModel
import com.neurafarm.drtaniamimic.utils.ViewUtils

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private val viewModelNav by viewModels<NavigationViewModel>()
    private lateinit var binding:FragmentSplashBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)
        if (appSharedPreferences.getBoolean(Constants.ALREADY_ONBOARDING)){
            ViewUtils.invokeAfterDelay({
                viewModelNav.navigateSplashFragmentToMainFragment()
            }, 3000)
        }else{
            ViewUtils.invokeAfterDelay({
                viewModelNav.navigateSplashFragmentToOnBoardingFragment()
            }, 3000)
        }
    }
}