package com.appmilla.vslsocial.android

import android.app.Application
import com.amity.socialcloud.sdk.api.core.AmityCoreClient

class SocialApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AmityCoreClient.setup(
            apiKey = "YOUR_API_KEY",
            endpoint = com.amity.socialcloud.sdk.api.core.endpoint.AmityEndpoint.EU, // optional param, defaulted as SG region
        )
    }
}
