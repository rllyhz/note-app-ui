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
import id.rllyhz.meapp.databinding.FragmentAddOrUpdateReminderBinding
import id.rllyhz.meapp.ui.activities.adding_updating_item.AddOrUpdateItemActivity
import id.rllyhz.meapp.ui.features.picker.DatePickerFragment
import id.rllyhz.meapp.ui.features.picker.TimePickerFragment
import id.rllyhz.meapp.utils.getDateString
import id.rllyhz.meapp.utils.getTimeString
import id.rllyhz.meapp.utils.hide
import id.rllyhz.meapp.utils.show

class AddReminderFragment : Fragment() {
    private var _binding: FragmentAddOrUpdateReminderBinding? = null
    private val binding: FragmentAddOrUpdateReminderBinding get() = _binding!!

    private var titleTextWatcher: TextWatcher? = null
    private var descriptionTextWatcher: TextWatcher? = null

    private var activity: AddOrUpdateItemActivity? = null

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
        activity = context as AddOrUpdateItemActivity
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
        _binding = FragmentAddOrUpdateReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        with(binding) {
            etTitleAddingOrUpdatingReminder.removeTextChangedListener(titleTextWatcher)
            etDescriptionAddingOrUpdatingReminder.removeTextChangedListener(descriptionTextWatcher)
            spinnerLevelOfImportanceAddingOrUpdatingReminder.adapter = null
            spinnerLevelOfImportanceAddingOrUpdatingReminder.onItemSelectedListener = null
        }
    }

    private fun setupUI() {
        binding.apply {
            etTitleAddingOrUpdatingReminder.addTextChangedListener(titleTextWatcher)
            etDescriptionAddingOrUpdatingReminder.addTextChangedListener(descriptionTextWatcher)

            btnGoBackAddingOrUpdatingReminder.setOnClickListener {
                requireActivity().finish()
            }

            btnSaveAddingOrUpdatingReminder.setOnClickListener {
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
                    llPickATimeAddingOrUpdatingReminder.show()
                    tvScheduleOnLabelAddingOrUpdatingReminder.hide()
                    rlContainerScheduleOnAddingOrUpdatingReminder.hide()
                } else {
                    llPickATimeAddingOrUpdatingReminder.hide()
                    tvScheduleOnLabelAddingOrUpdatingReminder.show()
                    rlContainerScheduleOnAddingOrUpdatingReminder.show()
                }
            }

            llContainerDailyNotificationsAddingOrUpdatingReminder.setOnClickListener {
                switchDailyNotificationsAddingReminder.run {
                    isChecked = !isChecked
                }
            }

            llPickADateAddingOrUpdatingReminder.setOnClickListener {
                DatePickerFragment().show(requireActivity().supportFragmentManager, DATE_PICKER_TAG)
            }

            llPickATimeAddingOrUpdatingReminder.setOnClickListener {
                TimePickerFragment().show(requireActivity().supportFragmentManager, TIME_PICKER_TAG)
            }

            with(spinnerLevelOfImportanceAddingOrUpdatingReminder) {
                onItemSelectedListener = spinnerItemSelectedListener
                adapter = spinnerAdapter
                setSelection(1)
            }

            activity?.sharedViewModel?.apply {
                etTitleAddingOrUpdatingReminder.setText(titleText.value)
                etDescriptionAddingOrUpdatingReminder.setText(descriptionText.value)

                selectedTime.observe(requireActivity()) { date ->
                    date?.let {
                        if (llPickADateAddingOrUpdatingReminder.isVisible) {
                            tvPickADateLabelAddingOrUpdatingReminder.text = date.getDateString()
                        } else {
                            tvPickATimeLabelAddingOrUpdatingReminder.text = date.getTimeString()
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