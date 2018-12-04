package info.tommarsh.cleanbase.ui.feature

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import info.tommarsh.cleanbase.R
import info.tommarsh.cleanbase.ui.base.BaseActivity

class FeatureActivity : BaseActivity() {

    private val viewModel: FeatureActivityViewModel by lazy {
        ViewModelProviders.of(this, factory).get(FeatureActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
