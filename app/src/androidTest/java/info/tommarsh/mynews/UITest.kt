package info.tommarsh.mynews

import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations

abstract class UITest {

    @get:Rule
    val rule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this);
    }
}