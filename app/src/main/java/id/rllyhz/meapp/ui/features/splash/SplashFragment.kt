package id.rllyhz.meapp.ui.features.splash

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.rllyhz.meapp.R
import id.rllyhz.meapp.databinding.FragmentSplashBinding
import id.rllyhz.meapp.ui.activities.landing.LandingActivity
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
            activity?.let { activity ->
                tvSplash.setOnClickListener {
                    if (!shouldRemove) {
                        activity.setUserPreferencesByStringKey(
                            PreferencesKeys.NAME,
                            getString(R.string.profile_name_dummy_data)
                        )
                        activity.setUserPreferencesByStringKey(
                            PreferencesKeys.JOB_NAME,
                            getString(R.string.profile_job_dummy_data)
                        )
                        activity.setUserPreferencesByBooleanKey(
                            PreferencesKeys.SHOW_ON_BOARDING,
                            true
                        )
                    } else {
                        activity.setUserPreferencesByStringKey(
                            PreferencesKeys.NAME,
                            ""
                        )
                        activity.setUserPreferencesByStringKey(
                            PreferencesKeys.JOB_NAME,
                            ""
                        )
                        activity.setUserPreferencesByBooleanKey(
                            PreferencesKeys.SHOW_ON_BOARDING,
                            false
                        )
                    }
                }

                activity.getAppPreferences().observe(requireActivity()) {
                    tvSplash.text =
                        "-> ${it.username} \n-> ${it.userJobName} \n-> ${it.shouldShowOnBoarding}"
                    shouldRemove = it.shouldShowOnBoarding
                }
            }
        }
    }
}