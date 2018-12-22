package info.tommarsh.core.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class ConnectionManager @Inject constructor(private val context: Application) {

    val isConnected: Boolean
        get() {
            val connectivityManager: ConnectivityManager? =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager?.activeNetworkInfo
            return networkInfo?.isConnectedOrConnecting ?: false
        }
}