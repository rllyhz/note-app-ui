package id.rllyhz.meapp.ui.features.landing

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.rllyhz.meapp.databinding.FragmentSecondLandingBinding
import id.rllyhz.meapp.ui.activities.landing.LandingActivity

class SecondLandingFragment : Fragment() {
    private var _binding: FragmentSecondLandingBinding? = null
    private val binding get() = _binding!!

    private var _activity: LandingActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondLandingBinding.inflate(inflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _activity = context as LandingActivity
    }

    override fun onDetach() {
        super.onDetach()
        _activity = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnNextSecondLanding.setOnClickListener {
                _activity?.next()
            }
        }
    }
}