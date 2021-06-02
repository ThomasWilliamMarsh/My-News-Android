package info.tommarsh.mynews.onboarding.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import info.tommarsh.mynews.onboarding.R
import info.tommarsh.mynews.onboarding.databinding.FragmentIntroductionBinding
import info.tommarsh.mynews.onboarding.model.Action
import info.tommarsh.mynews.onboarding.model.Event
import info.tommarsh.mynews.onboarding.ui.onBoardingViewModel
import kotlinx.coroutines.flow.collect

internal class IntroductionFragment : Fragment() {

    private val viewModel by onBoardingViewModel()

    private lateinit var binding: FragmentIntroductionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroductionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingStartButton.setOnClickListener {
            findNavController().navigate(R.id.action_introductionFragment_to_countryFragment)
        }
        binding.onboardingSkipButton.setOnClickListener {
            viewModel.postAction(Action.IntroductionSkipped)
        }

        lifecycleScope.launchWhenResumed {
            viewModel.events.collect { event ->
                if (event is Event.Finished) {
                    finishedOnBoarding()
                }
            }
        }
    }

    private fun finishedOnBoarding() {
        findNavController().navigate(R.id.action_introductionFragment_to_home_graph)
    }
}