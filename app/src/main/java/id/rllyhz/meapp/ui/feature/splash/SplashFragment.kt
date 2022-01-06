package id.rllyhz.meapp.ui.feature.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import id.rllyhz.meapp.databinding.FragmentSplashBinding
import id.rllyhz.meapp.ui.landing.LandingActivity
import id.rllyhz.meapp.ui.main.MainActivity
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

        activity?.let { activity ->
            activity.viewModel.getUserPreferences(requireContext())
                .asLiveData().value?.let { userPref ->
                    if (userPref.shouldShowOnBoarding) {
                        with(Intent(activity, MainActivity::class.java)) {
                            activity.startActivity(this)
                            activity.finish()
                        }
                    }
                }
        }
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = Intent(requireContext(), MainActivity::class.java)
        requireActivity().startActivity(intent)

        with(binding) {
            activity?.viewModel?.let { viewModel ->
                tvSplash.setOnClickListener {
                    if (!shouldRemove) {
                        viewModel.setUserPreferencesByStringKey(
                            requireContext(),
                            PreferencesKeys.NAME,
                            "Rully Ihza Mahendra"
                        )
                        viewModel.setUserPreferencesByStringKey(
                            requireContext(),
                            PreferencesKeys.JOB_NAME,
                            "Mobile Engineer"
                        )
                        viewModel.setUserPreferencesByBooleanKey(
                            requireContext(),
                            PreferencesKeys.SHOW_ON_BOARDING,
                            true
                        )
                    } else {
                        viewModel.setUserPreferencesByStringKey(
                            requireContext(),
                            PreferencesKeys.NAME,
                            ""
                        )
                        viewModel.setUserPreferencesByStringKey(
                            requireContext(),
                            PreferencesKeys.JOB_NAME,
                            ""
                        )
                        viewModel.setUserPreferencesByBooleanKey(
                            requireContext(),
                            PreferencesKeys.SHOW_ON_BOARDING,
                            false
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