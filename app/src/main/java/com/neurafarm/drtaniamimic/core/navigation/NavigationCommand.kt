package com.neurafarm.drtaniamimic.core.navigation

import android.content.Context
import androidx.navigation.NavController

typealias NavigationCommand = NavController.(Context) -> Unit
