package com.neurafarm.drtaniamimic.feature

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.neurafarm.drtaniamimic.R
import com.neurafarm.drtaniamimic.RegisterGraphDirections
import com.neurafarm.drtaniamimic.core.navigation.NavigationDispatcher

class NavigationViewModel @ViewModelInject constructor(
    private val navigationDispatcher: NavigationDispatcher
) : ViewModel() {
    fun navigateOnBoardingToMainFragment() {
        navigationDispatcher.emit {
            navigate(R.id.onBoardingFragment_to_mainFragment)
        }
    }

    fun navigateMainFragmentToRegisterGraph() {
        navigationDispatcher.emit {
            navigate(R.id.mainFragment_to_registerGraph)
        }
    }

    fun navigateRegisterFragmentToOTPFragment(){
        navigationDispatcher.emit {
            navigate(R.id.registerFragment_to_otpFragment)
        }
    }

    fun navigateOTPFragmentToAddInformationFragment(){
        navigationDispatcher.emit {
            navigate(R.id.otpFragment_to_addInformationFragment)
        }
    }

    fun navigateSplashFragmentToOnBoardingFragment(){
        navigationDispatcher.emit {
            navigate(R.id.splashFragment_to_onBoardingFragment)
        }
    }

    fun navigateSplashFragmentToMainFragment(){
        navigationDispatcher.emit {
            navigate(R.id.splashFragment_to_mainFragment)
        }
    }

    fun navigateMainFragmentToLoginFragment(){
        navigationDispatcher.emit {
            navigate(R.id.mainFragment_to_loginFragment)
        }
    }

    fun navigateLoginFragmentToRegisterFragment(){
        navigationDispatcher.emit {
            navigate(R.id.loginFragment_to_register_graph)
        }
    }

    fun navigateBack(){
        navigationDispatcher.emit {
            currentBackStackEntry?.destination?.id?.let { it1 -> popBackStack(it1, true) }
        }
    }

    fun navigateBackToSplash(){
        navigationDispatcher.emit {
            navigate(MainFragmentDirections.mainFragmentToSplashFragment())
        }
    }

    fun navigateRegisterToHomeFragment(){
        navigationDispatcher.emit {
            navigate(RegisterGraphDirections.registerGraphToMainFragment())
        }
    }
}