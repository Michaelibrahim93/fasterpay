package com.test.fasterpay.ui.fragments.base

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.test.basemodule.base.model.UiError
import com.test.basemodule.base.model.VMNotification
import com.test.basemodule.base.view.fragment.BaseFragment
import com.test.fasterpay.R
import com.test.fasterpay.ui.fragments.splash.SplashFragment

abstract class FasterPayBaseFragment<ViewModel : FasterPayBaseViewModel> :
    BaseFragment<ViewModel>() {
    override fun provideViewModel(viewModelClass: Class<ViewModel>): ViewModel {
        return ViewModelProvider(this).get(viewModelClass)
    }

    override fun showUiError(uiError: UiError?) {
        if (uiError == null) return
        context?.let {
            AlertDialog.Builder(it)
                .setMessage(uiError.errorText)
                .setPositiveButton(R.string.ok, null)
                .show()
        }
    }

    override fun doAction(vmNotification: VMNotification) {
        super.doAction(vmNotification)

        when(vmNotification.getAction()) {
            ACTION_TO_HOME -> navigateToHome()
        }
    }

    private fun navigateToHome() {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.nav_default_enter_anim)
            .setExitAnim(R.anim.nav_default_exit_anim)
            .setPopUpTo(R.id.main_nav_graph, true)
            .build()
        view?.findNavController()?.navigate(R.id.homeFragment, null, navOptions)
    }

    fun comingSoon() {
        Toast.makeText(context, getString(R.string.coming_soon), Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "FasterPayBaseFragment"
        const val ACTION_TO_HOME = "ACTION_TO_HOME"
    }
}