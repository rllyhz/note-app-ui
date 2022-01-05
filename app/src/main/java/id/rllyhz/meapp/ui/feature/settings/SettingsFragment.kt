package id.rllyhz.meapp.ui.feature.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.rllyhz.meapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()

        with(binding) {
            cardBg1.clearAnimation()
            cardBg2.clearAnimation()
        }
    }

    private fun setupUI() {
        with(binding) {
            cardBg1.run {
                alpha = 0f

                animate()
                    .alphaBy(1f)
                    .setDuration(750)
                    .start()
            }

            cardBg2.run {
                alpha = 0f

                animate()
                    .alphaBy(1f)
                    .setDuration(750)
                    .setStartDelay(200)
                    .start()
            }
        }
    }
}