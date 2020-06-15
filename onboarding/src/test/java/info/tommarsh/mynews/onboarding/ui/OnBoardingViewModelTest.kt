package info.tommarsh.mynews.onboarding.ui

import com.google.android.play.core.splitinstall.SplitInstallManager
import com.nhaarman.mockitokotlin2.mock
import info.tommarsh.mynews.core.preferences.PreferencesRepository
import info.tommarsh.mynews.core.util.coroutines.DispatcherProvider
import info.tommarsh.mynews.onboarding.data.OnBoardingDataSource

class OnBoardingViewModelTest {

    private val dataSource = mock<OnBoardingDataSource>()
    private val dispatcher = mock<DispatcherProvider>()
    private val preferences = mock<PreferencesRepository>()
    private val dynamicFeatureManager = mock<SplitInstallManager>()

    private val viewModel = OnBoardingViewModel(
        dataSource,
        dispatcher,
        preferences,
        dynamicFeatureManager
    )

}