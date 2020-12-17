package com.neurafarm.drtaniamimic.feature.register

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.neurafarm.drtaniamimic.core.navigation.NavigationDispatcher

class RegisterViewModel @ViewModelInject constructor(
    private var navigationDispatcher: NavigationDispatcher
) : ViewModel() {

    var name = ""
    var email = ""
    var phoneNumber = ""
    var username = ""

}