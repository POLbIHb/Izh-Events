package com.gmail.illobikol.vk_api_playbill.presenters

import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gmail.illobikol.vk_api_playbill.views.LoginView
import com.gmail.illobikol.vk_api_playbill.R
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.exceptions.VKAuthException

@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>(){


    fun loginVk(requestCode: Int, resultCode: Int, data: Intent?):Boolean {
        if (!VK.onActivityResult(requestCode = requestCode, resultCode = resultCode, data = data, callback = object : VKAuthCallback {

                override fun onLogin(token: VKAccessToken) {
                    println(token.toString())
                    viewState.openEvents()
                }
                override fun onLoginFailed(authException: VKAuthException) {
                    viewState.showError(textResource = R.string.login_error)
                }
            })){
            return false
        }
        return true
    }

}
