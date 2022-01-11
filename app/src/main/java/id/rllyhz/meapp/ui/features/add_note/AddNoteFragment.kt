package id.rllyhz.meapp.ui.features.add_note

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
import androidx.fragment.app.Fragment
import id.rllyhz.meapp.R
import id.rllyhz.meapp.databinding.FragmentAddOrUpdateNoteBinding
import id.rllyhz.meapp.ui.activities.adding_updating_item.AddOrUpdateItemActivity

class AddNoteFragment : Fragment() {
    private var _binding: FragmentAddOrUpdateNoteBinding? = null
    private val binding: FragmentAddOrUpdateNoteBinding get() = _binding!!

    private var titleTextWatcher: TextWatcher? = null
    private var contentTextWatcher: TextWatcher? = null

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

        contentTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(newContent: CharSequence?, p1: Int, p2: Int, p3: Int) {
                activity?.sharedViewModel?.setContentItem(newContent.toString())
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
        titleTextWatcher = null
        contentTextWatcher = null
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
        _binding = FragmentAddOrUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        with(binding) {
            etTitleAddingOrUpdatingNote.removeTextChangedListener(titleTextWatcher)
            etContentAddingOrUpdatingNote.removeTextChangedListener(contentTextWatcher)
            spinnerLevelOfImportanceAddingOrUpdatingNote.adapter = null
            spinnerLevelOfImportanceAddingOrUpdatingNote.onItemSelectedListener = null
        }
    }

    private fun setupUI() {
        binding.apply {
            etTitleAddingOrUpdatingNote.addTextChangedListener(titleTextWatcher)
            etContentAddingOrUpdatingNote.addTextChangedListener(contentTextWatcher)

            btnGoBackAddingOrUpdatingNote.setOnClickListener {
                requireActivity().finish()
            }

            btnSaveAddingOrUpdatingNote.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.successfully_added_note_message),
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().finish()
            }

            with(spinnerLevelOfImportanceAddingOrUpdatingNote) {
                onItemSelectedListener = spinnerItemSelectedListener
                adapter = spinnerAdapter
                setSelection(1)
            }

            activity?.sharedViewModel?.apply {
                etTitleAddingOrUpdatingNote.setText(titleText.value)
                etContentAddingOrUpdatingNote.setText(contentText.value)
            }
        }
    }
}