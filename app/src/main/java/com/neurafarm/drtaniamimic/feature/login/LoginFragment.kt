package com.neurafarm.drtaniamimic.feature.login

import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.neurafarm.drtaniamimic.R
import com.neurafarm.drtaniamimic.base.BaseFragment
import com.neurafarm.drtaniamimic.common.Constants
import com.neurafarm.drtaniamimic.data.models.AccountModel
import com.neurafarm.drtaniamimic.databinding.FragmentLoginBinding
import com.neurafarm.drtaniamimic.feature.NavigationViewModel
import com.neurafarm.drtaniamimic.utils.*

class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private val viewModelNav by viewModels<NavigationViewModel>()

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        setupAppBar(binding.appBarLayout.toolbar, "", true)
        binding.tvRegister.text = Html.fromHtml("Not Registered? <b>Register Here</b>")
        binding.tvRegister.onClick {
            viewModelNav.navigateLoginFragmentToRegisterFragment()
        }
        with(binding){
            etUsernameOrEmail.checkInput { v ->
                checkInputComplete(v)
            }
            etPassword.checkInput {
                checkInputComplete(it)
            }
            btnLogin.onClick {
                val registered = appSharedPreferences.getList(
                    Constants.REGISTERED_ACCOUNT,
                    Array<AccountModel>::class.java
                ).toList()
                registered.forEach { acc ->
                    with(binding) {
                        if (etUsernameOrEmail.text.toString().isEmailValid()) {
                            if (etUsernameOrEmail.text.toString() == acc.email && etPassword.text.toString() == PasswordUtils.getDecryptedPassword(
                                    appSharedPreferences,
                                    acc.password
                                )
                            ) {
                                appSharedPreferences.putBoolean(Constants.IS_LOGINED, true)
                                viewModelNav.navigateBack()
                            }else{
                                showToast("Email or Password is invalid")
                            }
                        } else {
                            if (etUsernameOrEmail.text.toString() == acc.username && etPassword.text.toString() == PasswordUtils.getDecryptedPassword(
                                    appSharedPreferences,
                                    acc.password
                                )
                            ) {
                                appSharedPreferences.putBoolean(Constants.IS_LOGINED, true)
                                viewModelNav.navigateBack()
                            }else{
                                showToast("Username or Password is invalid")
                            }
                        }
                    }
                }
            }
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

    private fun checkInputComplete(view: EditText) {
        when (view) {
            binding.etUsernameOrEmail -> {
                if (binding.etUsernameOrEmail.text.toString().isEmailValid()) {
                    if (binding.etUsernameOrEmail.checkIfEmptyWithError(
                            "Email ${
                                resources.getString(
                                    R.string.error_empty
                                )
                            }"
                        )
                    )
                        return
                    else if (binding.etUsernameOrEmail.checkIfEmailValidWithError("Please fill email with appropriate format."))
                        return
                } else {
                    if (binding.etUsernameOrEmail.checkIfEmptyWithError(
                            "Username ${
                                resources.getString(
                                    R.string.error_empty
                                )
                            }"
                        )
                    )
                        return
                }
            }
            binding.etPassword -> {
                if (binding.etPassword.checkIfEmptyWithError("Password ${resources.getString(R.string.error_empty)}"))
                    return
            }
        }
        with(binding) {
            btnLogin.isEnabled = etUsernameOrEmail.checkIfNotEmpty() && etPassword.checkIfNotEmpty()
        }
    }
}