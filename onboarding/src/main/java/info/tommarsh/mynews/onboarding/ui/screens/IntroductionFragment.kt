package info.tommarsh.mynews.onboarding.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.mynews.onboarding.R
import info.tommarsh.mynews.onboarding.databinding.FragmentIntroductionBinding
import info.tommarsh.mynews.onboarding.model.Action
import info.tommarsh.mynews.onboarding.model.Event
import info.tommarsh.mynews.onboarding.ui.OnBoardingViewModel
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
internal class IntroductionFragment : Fragment() {

    private val viewModel by navGraphViewModels<OnBoardingViewModel>(R.id.onboarding_nav_graph)

    private lateinit var binding: FragmentIntroductionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroductionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingStartButton.setOnClickListener {
            findNavController().navigate(R.id.action_introductionFragment_to_sourcesFragment)
        }
        binding.onboardingSkipButton.setOnClickListener {
            viewModel.postAction(Action.IntroductionSkipped)
        }

        lifecycleScope.launchWhenResumed {
            viewModel.events.collect { event ->
                when (event) {
                    is Event.Finished -> findNavController().navigate(R.id.action_introductionFragment_to_main_nav_graph)
                }
            }
        }
    }

}