package com.neurafarm.drtaniamimic.feature.home

import android.os.Bundle
import android.provider.SyncStateContract
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neurafarm.drtaniamimic.R
import com.neurafarm.drtaniamimic.base.BaseFragment
import com.neurafarm.drtaniamimic.common.Constants
import com.neurafarm.drtaniamimic.databinding.FragmentHomeBinding
import com.neurafarm.drtaniamimic.feature.NavigationViewModel
import com.neurafarm.drtaniamimic.utils.ViewUtils
import com.neurafarm.drtaniamimic.utils.onClick

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModelNav by viewModels<NavigationViewModel>()

    companion object {
        fun newInstance(bundle: Bundle?): Fragment {
            val fragment = HomeFragment()
            if (bundle != null) {
                fragment.arguments = bundle
            }
            return HomeFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (appSharedPreferences.getBoolean(Constants.IS_LOGINED)) {
            inflater.inflate(R.menu.menu_app_bar, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                logout()
                viewModelNav.navigateBackToSplash()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        setupAppBar(binding.appBarLayout.toolbar, "Dr. Tania", false)
        if (appSharedPreferences.getBoolean(Constants.IS_LOGINED)) {
            binding.llNoticeMember.visibility = View.GONE
        } else {
            binding.llNoticeMember.visibility = View.VISIBLE
        }
        val listMenu = mutableListOf<MenuTopItem>()
        val icons = requireContext().resources.obtainTypedArray(R.array.menu_home_top_icon)
        val values = requireContext().resources.getStringArray(R.array.menu_home_top_string)
        for (position in values.indices) {
            listMenu.add(MenuTopItem(icons.getResourceId(position, 0), values[position]))
        }
        binding.rvGridMenu.adapter =
            MenuTopGridAdapter(listMenu, object : MenuTopGridAdapter.MenuTopGridListener {
                override fun onClick(item: MenuTopItem) {
                    showToast(item.value)
                }
            })
        binding.rvGridMenu.layoutManager =
            GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        binding.btnRegister.onClick {
            viewModelNav.navigateMainFragmentToRegisterGraph()
        }
        binding.btnLogin.onClick {
            viewModelNav.navigateMainFragmentToLoginFragment()
        }
    }
}