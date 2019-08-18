package info.tommarsh.mynews.repository.util

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

internal class ConnectionManager @Inject constructor(private val context: Context) {

    val isConnected: Boolean
        get() {
            val connectivityManager: ConnectivityManager? =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager?.activeNetworkInfo
            return networkInfo?.isConnectedOrConnecting ?: false
        }
}