package com.neurafarm.drtaniamimic.feature.onboarding

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.neurafarm.drtaniamimic.R
import com.neurafarm.drtaniamimic.databinding.LayoutOnboardingSlideBinding

class OnBoardingSliderAdapter(private val context: Context) : PagerAdapter() {

    override fun getCount(): Int = context.resources.getStringArray(R.array.onboarding_list_image).size

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view == (o as RelativeLayout)
    }

    @SuppressLint("Recycle")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val binding = LayoutOnboardingSlideBinding.inflate(layoutInflater, container, false)
        val images = context.resources.obtainTypedArray(R.array.onboarding_list_image)
        val headings = context.resources.getStringArray(R.array.onboarding_list_heading)
        val bodies = context.resources.getStringArray(R.array.onboarding_list_body)
        binding.ivSlideImage.setImageResource(images.getResourceId(position, 0))
        binding.tvSlideHeading.text = headings[position]
        binding.tvSlideBody.text = bodies[position]
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
        container.removeView((o as RelativeLayout))
    }
}