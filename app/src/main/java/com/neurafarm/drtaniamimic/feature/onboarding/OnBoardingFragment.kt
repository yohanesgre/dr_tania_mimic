package com.neurafarm.drtaniamimic.feature.onboarding

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.neurafarm.drtaniamimic.R
import com.neurafarm.drtaniamimic.base.BaseFragment
import com.neurafarm.drtaniamimic.common.Constants
import com.neurafarm.drtaniamimic.databinding.FragmentOnboardingBinding
import com.neurafarm.drtaniamimic.feature.NavigationViewModel
import com.neurafarm.drtaniamimic.utils.onClick
import timber.log.Timber

class OnBoardingFragment : BaseFragment(R.layout.fragment_onboarding) {

    private val viewModelNav by viewModels<NavigationViewModel>()

    private lateinit var binding: FragmentOnboardingBinding

    private val adapterSlider by lazy {
        OnBoardingSliderAdapter(requireContext())
    }

    private var mCurrentPage = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOnboardingBinding.bind(view)
        binding.vpSlide.adapter = adapterSlider
        binding.dotsIndicator.setViewPager(binding.vpSlide)
        binding.vpSlide.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                mCurrentPage = position
                if (position == 0) {
                    binding.tvBack.visibility = View.INVISIBLE
                    binding.tvBack.isEnabled = false
                } else {
                    binding.tvBack.visibility = View.VISIBLE
                    binding.tvBack.isEnabled = true
                }
                if (position == adapterSlider.count - 1){
                    binding.tvNext.text = "Finish"
                }else{
                    binding.tvNext.text = "Next"
                }
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
        binding.tvBack.onClick {
            if (mCurrentPage > 0) {
                binding.vpSlide.currentItem = mCurrentPage - 1
            }
        }
        binding.tvNext.onClick {
            if (mCurrentPage < adapterSlider.count) {
                binding.vpSlide.currentItem = mCurrentPage + 1
            }
            if (mCurrentPage == adapterSlider.count - 1){
                appSharedPreferences.putBoolean(Constants.ALREADY_ONBOARDING, true)
                viewModelNav.navigateOnBoardingToMainFragment()
            }
        }
    }

}