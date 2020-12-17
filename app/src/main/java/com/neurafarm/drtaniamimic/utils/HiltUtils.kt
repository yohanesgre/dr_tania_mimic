package com.neurafarm.drtaniamimic.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

typealias HiltAssisted = androidx.hilt.Assisted

inline fun <reified T : ViewModel> Fragment.hiltNavGraphViewModels(@IdRes navGraphIdRes: Int) =
        viewModels<T>(
                ownerProducer = { findNavController().getBackStackEntry(navGraphIdRes) },
                factoryProducer = { defaultViewModelProviderFactory }
        )

inline fun <reified T : ViewModel> Fragment.getGraphInStack(@IdRes navGraphIdRes: Int): T? =
        try {
                val backStack = findNavController().getBackStackEntry(navGraphIdRes)
                ViewModelProvider(backStack, defaultViewModelProviderFactory).get(T::class.java)
        } catch (e: Exception) {
                null
        }