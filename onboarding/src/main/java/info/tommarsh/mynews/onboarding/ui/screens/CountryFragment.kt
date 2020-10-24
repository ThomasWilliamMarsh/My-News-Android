package info.tommarsh.mynews.onboarding.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import info.tommarsh.mynews.core.util.makeGone
import info.tommarsh.mynews.core.util.makeVisible
import info.tommarsh.mynews.core.util.newTaskIntent
import info.tommarsh.mynews.onboarding.R
import info.tommarsh.mynews.onboarding.databinding.FragmentCountryBinding
import info.tommarsh.mynews.onboarding.model.Action
import info.tommarsh.mynews.onboarding.model.Event
import info.tommarsh.mynews.onboarding.ui.adapter.ChoiceAdapter
import info.tommarsh.mynews.onboarding.ui.onBoardingViewModel
import info.tommarsh.mynews.presentation.ui.ArticlesActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
internal class CountryFragment : Fragment() {

    private val viewModel by onBoardingViewModel(R.id.onboarding_nav_graph)

    private val adapter = ChoiceAdapter()

    private lateinit var binding: FragmentCountryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        setUpNextButton()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val key = arguments?.getString(EXTRA_KEY)!!

        lifecycleScope.launchWhenResumed {
            viewModel.events.collect { event ->
                binding.onboardingProgress.makeGone()
                when (event) {
                    is Event.Loading -> binding.onboardingProgress.makeVisible()
                    is Event.Error -> showErrorToast()
                    is Event.Fetched -> {
                        playAnimation()
                        adapter.submitList(event.choices)
                    }
                    is Event.Finished -> finishedOnBoarding()
                }
            }
        }

        viewModel.postAction(Action.FetchChoices(key))
    }

    private fun finishedOnBoarding() {
        startActivity(requireContext().newTaskIntent<ArticlesActivity>())
    }

    private fun playAnimation() {
        binding.onboardingAnimation.setAnimation(ANIMATION_FILE)
        binding.onboardingAnimation.playAnimation()
    }

    private fun setUpNextButton() {
        binding.onboardingNextButton.setOnClickListener {
            viewModel.postAction(Action.SelectedCountry(adapter.country))
        }
    }

    private fun setUpRecyclerView() {
        binding.onboardingRecyclerView.adapter = adapter
    }

    private fun showErrorToast() {
        Toast.makeText(requireContext(), "Error OnBoarding.", Toast.LENGTH_SHORT).show()
        finishedOnBoarding()
    }

    companion object {
        private const val EXTRA_KEY = "extra_key"
        private const val ANIMATION_FILE = "newspaper.json"
    }
}