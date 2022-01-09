package com.zinoview.translatorapp.data.core.cloud

import android.content.Context
import android.net.ConnectivityManager

interface NetworkConnection {

    fun isNotAvailable() : Boolean

    class Base(
        private val context: Context
    ) : NetworkConnection {

        override fun isNotAvailable(): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return (networkInfo != null && networkInfo.isConnected).not()
        }

    }

}