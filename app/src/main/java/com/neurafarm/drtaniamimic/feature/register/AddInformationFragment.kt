package com.neurafarm.drtaniamimic.feature.register

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.github.dhaval2404.imagepicker.ImagePicker
import com.neurafarm.drtaniamimic.R
import com.neurafarm.drtaniamimic.base.BaseFragment
import com.neurafarm.drtaniamimic.common.Constants
import com.neurafarm.drtaniamimic.data.models.AccountModel
import com.neurafarm.drtaniamimic.databinding.FragmentRegisterAddInformationBinding
import com.neurafarm.drtaniamimic.feature.NavigationViewModel
import com.neurafarm.drtaniamimic.utils.*

class AddInformationFragment : BaseFragment(R.layout.fragment_register_add_information) {

    private val viewModelNav by viewModels<NavigationViewModel>()
    private val viewModelRegister by hiltNavGraphViewModels<RegisterViewModel>(R.id.register_graph)
    private lateinit var binding: FragmentRegisterAddInformationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterAddInformationBinding.bind(view)
        with(binding) {
            setupAppBar(binding.appBarLayout.toolbar, "Add Information", true)
            etPassword.checkInput { v ->
                checkInputComplete(v)
            }
            etPasswordConfirm.checkInput { v ->
                checkInputComplete(v)
            }
            etCity.checkInput { v ->
                checkInputComplete(v)
            }
            etOccupation.checkInput { v ->
                checkInputComplete(v)
            }
            tvAddImage.onClick {
                ImagePicker.with(requireActivity())
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .maxResultSize(
                        512,
                        512
                    )    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start { resultCode, data ->
                        when (resultCode) {
                            Activity.RESULT_OK -> {
                                //Image Uri will not be null for RESULT_OK
                                val fileUri = data?.data
                                binding.ivImage.setImageURI(fileUri)
                            }
                            ImagePicker.RESULT_ERROR -> {
                                showToast(ImagePicker.getError(data))
                            }
                            else -> {
                                showToast("Pick Image canceled")
                            }
                        }
                    }
            }
            btnNext.onClick {
                val registerAccount = AccountModel(
                    email = viewModelRegister.email,
                    username = viewModelRegister.username,
                    name = viewModelRegister.name,
                    phoneNumber = viewModelRegister.phoneNumber,
                    password = PasswordUtils.encryptAndSavePassword(
                        etPassword.text.toString(),
                        appSharedPreferences
                    ),
                    occupation = etOccupation.text.toString(),
                    city = etCity.text.toString()
                )
                val list = listOf(registerAccount)
                appSharedPreferences.putList(Constants.REGISTERED_ACCOUNT, list)
                showToast("Register Success")
                ViewUtils.invokeAfterDelay({ viewModelNav.navigateRegisterToHomeFragment() })
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
            binding.etCity -> {
                if (binding.etCity.checkIfEmptyWithError(
                        "City ${
                            resources.getString(
                                R.string.error_empty
                            )
                        }"
                    )
                )
                    return
            }
            binding.etOccupation -> {
                if (binding.etOccupation.checkIfEmptyWithError(
                        "Occupation ${
                            resources.getString(
                                R.string.error_empty
                            )
                        }"
                    )
                )
                    return
            }
            binding.etPassword -> {
                if (binding.etPassword.checkIfEmptyWithError(
                        "Passwod ${
                            resources.getString(
                                R.string.error_empty
                            )
                        }"
                    )
                )
                    return
            }
            binding.etPasswordConfirm -> {
                if (binding.etPasswordConfirm.checkIfEmptyWithError(
                        "Passwod Confimation ${
                            resources.getString(
                                R.string.error_empty
                            )
                        }"
                    )
                ) {
                    return
                } else if (binding.etPasswordConfirm.text.toString() != binding.etPassword.toString()) {
                    "Password not match ${
                        resources.getString(
                            R.string.error_empty
                        )
                    }"
                    return
                }

            }
        }
        with(binding) {
            btnNext.isEnabled =
                etOccupation.checkIfNotEmpty() && etCity.checkIfNotEmpty() && etPassword.checkIfNotEmpty() && etPasswordConfirm.checkIfNotEmpty()
        }
    }
}