package com.appmilla.vslsocial.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amity.socialcloud.sdk.api.core.AmityCoreClient
import com.amity.socialcloud.sdk.core.session.AccessTokenRenewal
import com.amity.socialcloud.sdk.helper.core.coroutines.await
import com.amity.socialcloud.sdk.model.core.session.SessionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _loginStatus = MutableStateFlow<Boolean?>(null)
    val loginStatus = _loginStatus.asStateFlow()

    fun registerDevice(userId: String, displayName: String? = "") {
        viewModelScope.launch {
            try {
                AmityCoreClient.login(
                    userId,
                    object : SessionHandler {
                        override fun sessionWillRenewAccessToken(renewal: AccessTokenRenewal) {
                            renewal.renew()
                        }
                    },
                )
                    .apply {
                        if (!displayName.isNullOrEmpty()) {
                            displayName(displayName)
                        }
                    }
                    .build()
                    .submit()
                    .await()
                _loginStatus.value = true
                println("User registered")
            } catch (e: Exception) {
                _loginStatus.value = false
                println("Login error: ${e.message}")
            }
        }
    }

    fun initiateLogin() {
        val userId = AmityCoreClient.getUserId().ifEmpty { "Rich Android" }
        registerDevice(userId)
    }
}
