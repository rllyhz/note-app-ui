package id.rllyhz.meapp.ui.feature.splash

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import id.rllyhz.meapp.databinding.FragmentSplashBinding
import id.rllyhz.meapp.ui.landing.LandingActivity
import id.rllyhz.meapp.utils.PreferencesKeys

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private var activity: LandingActivity? = null

    private var shouldRemove = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as LandingActivity
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            activity?.viewModel?.let { viewModel ->
                tvSplash.setOnClickListener {
                    if (!shouldRemove) {
                        viewModel.setUserPreferencesByKey(
                            requireContext(),
                            PreferencesKeys.NAME,
                            "Rully Ihza Mahendra"
                        )
                        viewModel.setUserPreferencesByKey(
                            requireContext(),
                            PreferencesKeys.JOB_NAME,
                            "Mobile Engineer"
                        )
                    } else {
                        viewModel.setUserPreferencesByKey(
                            requireContext(),
                            PreferencesKeys.NAME,
                            ""
                        )
                        viewModel.setUserPreferencesByKey(
                            requireContext(),
                            PreferencesKeys.JOB_NAME,
                            ""
                        )
                    }

                    shouldRemove = !shouldRemove
                }

                viewModel.getUserPreferences(requireContext()).asLiveData()
                    .observe(requireActivity()) {
                        tvSplash.text =
                            "-> ${it.username} \n-> ${it.userJobName} \n-> ${it.shouldShowOnBoarding}"
                    }
            }
        }
    }
}