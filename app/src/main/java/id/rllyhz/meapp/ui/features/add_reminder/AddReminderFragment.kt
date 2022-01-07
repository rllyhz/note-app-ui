package id.rllyhz.meapp.ui.features.add_reminder

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import id.rllyhz.meapp.R
import id.rllyhz.meapp.databinding.FragmentAddReminderBinding
import id.rllyhz.meapp.ui.activities.adding_item.AddItemActivity
import id.rllyhz.meapp.ui.features.picker.DatePickerFragment
import id.rllyhz.meapp.ui.features.picker.TimePickerFragment
import id.rllyhz.meapp.utils.hide
import id.rllyhz.meapp.utils.show
import id.rllyhz.meapp.utils.getDateString
import id.rllyhz.meapp.utils.getTimeString

class AddReminderFragment : Fragment() {
    private var _binding: FragmentAddReminderBinding? = null
    private val binding: FragmentAddReminderBinding get() = _binding!!

    private var titleTextWatcher: TextWatcher? = null
    private var descriptionTextWatcher: TextWatcher? = null

    private var activity: AddItemActivity? = null

    private var spinnerAdapter: ArrayAdapter<String>? = null
    private var spinnerItemSelectedListener: AdapterView.OnItemSelectedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        titleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(newTitle: CharSequence?, p1: Int, p2: Int, p3: Int) {
                activity?.sharedViewModel?.setTitleItem(newTitle.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}
        }

        descriptionTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(newDescription: CharSequence?, p1: Int, p2: Int, p3: Int) {
                activity?.sharedViewModel?.setDescriptionItem(newDescription.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}
        }

        spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.level_of_importance_keys)
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinnerItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                //
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        titleTextWatcher = null
        descriptionTextWatcher = null
        spinnerAdapter = null
        spinnerItemSelectedListener = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AddItemActivity
    }

    override fun onDetach() {
        super.onDetach()
        if (activity != null)
            activity = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        with(binding) {
            etTitleAddingReminder.removeTextChangedListener(titleTextWatcher)
            etDescriptionAddingReminder.removeTextChangedListener(descriptionTextWatcher)
            spinnerLevelOfImportanceAddingReminder.adapter = null
            spinnerLevelOfImportanceAddingReminder.onItemSelectedListener = null
        }
    }

    private fun setupUI() {
        binding.apply {
            etTitleAddingReminder.addTextChangedListener(titleTextWatcher)
            etDescriptionAddingReminder.addTextChangedListener(descriptionTextWatcher)

            btnGoBackAddingReminder.setOnClickListener {
                requireActivity().finish()
            }

            btnSaveAddingReminder.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.successfully_scheduled_reminder_message),
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().finish()
            }

            switchDailyNotificationsAddingReminder.setOnCheckedChangeListener { _, isChecked ->
                activity?.sharedViewModel?.isDailyNotifications(isChecked)

                if (isChecked) {
                    llPickATimeAddingReminder.show()
                    tvScheduleOnLabelAddingReminder.hide()
                    rlContainerScheduleOnAddingReminder.hide()
                } else {
                    llPickATimeAddingReminder.hide()
                    tvScheduleOnLabelAddingReminder.show()
                    rlContainerScheduleOnAddingReminder.show()
                }
            }

            llContainerDailyNotificationsAddingReminder.setOnClickListener {
                switchDailyNotificationsAddingReminder.run {
                    isChecked = !isChecked
                }
            }

            llPickADateAddingReminder.setOnClickListener {
                DatePickerFragment().show(requireActivity().supportFragmentManager, DATE_PICKER_TAG)
            }

            llPickATimeAddingReminder.setOnClickListener {
                TimePickerFragment().show(requireActivity().supportFragmentManager, TIME_PICKER_TAG)
            }

            with(spinnerLevelOfImportanceAddingReminder) {
                onItemSelectedListener = spinnerItemSelectedListener
                adapter = spinnerAdapter
                setSelection(1)
            }

            activity?.sharedViewModel?.apply {
                etTitleAddingReminder.setText(titleText.value)
                etDescriptionAddingReminder.setText(descriptionText.value)

                selectedTime.observe(requireActivity()) { date ->
                    date?.let {
                        if (llPickADateAddingReminder.isVisible) {
                            tvPickADateLabelAddingReminder.text = date.getDateString()
                        } else {
                            tvPickATimeLabelAddingReminder.text = date.getTimeString()
                        }
                    }
                }

                isDailyNotifications.observe(requireActivity()) {
                    switchDailyNotificationsAddingReminder.isChecked = it
                }
            }
        }
    }

    companion object {
        private const val DATE_PICKER_TAG = "DatePickerOnce"
        private const val TIME_PICKER_TAG = "TimePickerOnce"
    }
}