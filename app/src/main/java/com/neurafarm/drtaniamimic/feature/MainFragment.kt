package com.neurafarm.drtaniamimic.feature

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.neurafarm.drtaniamimic.R
import com.neurafarm.drtaniamimic.base.BaseFragment
import com.neurafarm.drtaniamimic.databinding.FragmentMainBinding
import com.neurafarm.drtaniamimic.feature.home.HomeFragment

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val SELECTED_BOTTOM_NAV = "selected_bottom_nav"

    private lateinit var binding:FragmentMainBinding
    private var selectedNavId = R.id.navigation_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding.navView.setOnNavigationItemSelectedListener(onNavBottomSelected)
        binding.navView.selectedItemId = R.id.navigation_home
        if (savedInstanceState != null) {
            binding.navView.selectedItemId = savedInstanceState.getInt(SELECTED_BOTTOM_NAV)
        } else {
            binding.navView.selectedItemId = selectedNavId
            fragmentChange(selectedNavId)
        }
    }

    private val onNavBottomSelected =
        BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            if (selectedNavId != item.itemId) {
                selectedNavId = item.itemId
            }
            true
        }

    private fun fragmentChange(itemId: Int) {
        var fragment: Fragment? = null
        when (itemId) {
            R.id.navigation_home -> fragment = HomeFragment.newInstance(null)
        }
        if (fragment != null) {
            parentActivity.supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, fragment)
                .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_BOTTOM_NAV, binding.navView.selectedItemId)
    }

}