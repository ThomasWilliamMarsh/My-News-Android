package info.tommarsh.mynews.home.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.home.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    override fun onBackPressed() {
        if (!findNavController(R.id.nav_host_fragment).popBackStack()) {
            finish()
        }
    }
}
