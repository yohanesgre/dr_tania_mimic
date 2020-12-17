package com.neurafarm.drtaniamimic.feature.register

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.hbb20.CountryCodePicker.PhoneNumberValidityChangeListener
import com.neurafarm.drtaniamimic.R
import com.neurafarm.drtaniamimic.base.BaseFragment
import com.neurafarm.drtaniamimic.databinding.FragmentRegisterBinding
import com.neurafarm.drtaniamimic.feature.NavigationViewModel
import com.neurafarm.drtaniamimic.utils.*


class RegisterFragment : BaseFragment(R.layout.fragment_register) {

    private val viewModelNav by viewModels<NavigationViewModel>()
    private val viewModelRegister by hiltNavGraphViewModels<RegisterViewModel>(R.id.register_graph)

    private lateinit var binding: FragmentRegisterBinding

    private var validNumber = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        setupAppBar(binding.appBarLayout.toolbar, "", true)
        with(binding) {
            etName.checkInput { v ->
                checkInputComplete(v)
            }
            etEmail.checkInput { v ->
                checkInputComplete(v)
            }
            etPhoneNumber.checkInput { v ->
                checkInputComplete(v)
            }
            etUsername.checkInput { v ->
                checkInputComplete(v)
            }
            ccp.registerCarrierNumberEditText(binding.etPhoneNumber)
            ccp.setPhoneNumberValidityChangeListener {
                validNumber = it
            }
            btnNext.onClick {
                with(binding) {
                    viewModelRegister.email = etEmail.text.toString()
                    viewModelRegister.username = etUsername.text.toString()
                    viewModelRegister.phoneNumber = etPhoneNumber.text.toString()
                    viewModelRegister.name = etName.text.toString()
                }
                viewModelNav.navigateRegisterFragmentToOTPFragment()
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
            binding.etEmail -> {
                if (binding.etEmail.checkIfEmptyWithError(
                        "Email ${
                            resources.getString(
                                R.string.error_empty
                            )
                        }"
                    )
                )
                    return
                else if (binding.etEmail.checkIfEmailValidWithError("Please fill email with appropriate format."))
                    return
            }
            binding.etUsername -> {
                if (binding.etUsername.checkIfEmptyWithError(
                        "Username ${
                            resources.getString(
                                R.string.error_empty
                            )
                        }"
                    )
                )
                    return
            }
            binding.etName -> {
                if (binding.etUsername.checkIfEmptyWithError(
                        "Name ${
                            resources.getString(
                                R.string.error_empty
                            )
                        }"
                    )
                )
                    return
            }
            binding.etPhoneNumber -> {
                if (binding.etPhoneNumber.checkIfEmptyWithError("Phone ${resources.getString(R.string.error_empty)}"))
                    if (!validNumber)
                        return
            }
        }
        with(binding) {
            btnNext.isEnabled =
                etName.checkIfNotEmpty() && etUsername.checkIfNotEmpty() && etPhoneNumber.checkIfNotEmpty() && etEmail.checkIfNotEmpty()
        }
    }
}