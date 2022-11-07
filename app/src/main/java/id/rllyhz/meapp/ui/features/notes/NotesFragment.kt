package id.rllyhz.meapp.ui.features.notes

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.rllyhz.meapp.R
import id.rllyhz.meapp.data.models.Note
import id.rllyhz.meapp.databinding.FragmentNotesBinding
import id.rllyhz.meapp.ui.activities.adding_updating_item.AddOrUpdateItemActivity
import id.rllyhz.meapp.ui.adapters.NotesAdapter
import id.rllyhz.meapp.utils.*

class NotesFragment : Fragment(), NotesAdapter.NoteItemClickCallback {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModels()
    private var notesAdapter: NotesAdapter? = null
    private lateinit var activeNote: Note

    private var bottomSheetBehaviorNotes: BottomSheetBehavior<ConstraintLayout>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notesAdapter = NotesAdapter()
        notesAdapter?.setOnItemClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        notesAdapter = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomSheetBehaviorNotes = null
        bottomSheetCallback = null
        deletingAlert = null
        searchTextWatcher = null

        with(binding) {
            svNotes.clearAnimation()
            rvNotes.clearAnimation()
        }
    }

    private fun setupUI() {
        with(binding) {

            deletingAlert = AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.dialog_title_deleting_notes))
                .setMessage(getString(R.string.dialog_message_deleting_notes))
                .setCancelable(true)
                .setNegativeButton(getString(R.string.negative_button_label_deleting_notes)) { _, _ ->
                    Toast.makeText(requireContext(), "Cancelling dialog", Toast.LENGTH_SHORT).show()
                    deletingAlert?.cancel()
                }
                .setPositiveButton(getString(R.string.positive_button_label_deleting_notes)) { _, _ ->
                    viewModel.selectedNote.value?.let {
                        Toast.makeText(
                            requireContext(),
                            "Deleted item ${it.id}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    deletingAlert?.cancel()
                }
                .create()

            searchTextWatcher = object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(newQuery: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.setSearchQuery(newQuery.toString())
                }

                override fun afterTextChanged(p0: Editable?) {}
            }

            bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            //
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            fabNotesAdd.animate().scaleX(1f).scaleY(1f)
                                .setDuration(150).start()
                        }
                        else -> Unit
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            }

            with(bottomSheetNotes) {
                bottomSheetBehaviorNotes =
                    BottomSheetBehavior.from(bottomSheetContainerNotes)
                bottomSheetCallback?.let {
                    bottomSheetBehaviorNotes?.addBottomSheetCallback(it)
                }

                btnCloseBottomSheetNotes.setOnClickListener {
                    bottomSheetBehaviorNotes?.state = BottomSheetBehavior.STATE_COLLAPSED
                }
                btnDeleteBottomSheetNotes.setOnClickListener {
                    deletingAlert?.show()
                    bottomSheetBehaviorNotes?.state = BottomSheetBehavior.STATE_COLLAPSED
                }
                btnEditBottomSheetNotes.setOnClickListener {
                    Intent(requireActivity(), AddOrUpdateItemActivity::class.java).also {
                        it.putExtra(
                            AddOrUpdateItemActivity.DESTINATION_PAGE,
                            AddOrUpdateItemActivity.UPDATING_NOTES_PAGE
                        )
                        it.putExtra(AddOrUpdateItemActivity.DATA_KEY, activeNote)
                        startActivity(it)
                    }
                }
            }

            svNotes.startAnimation(
                AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.fade_in_and_scale_up
                )
            )
            svNotes.addTextChangedListener(searchTextWatcher)
            svNotes.setOnEditorActionListener { _, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (keyEvent.action == KeyEvent.ACTION_DOWN && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) {
                    with(viewModel) {
                        performSearchingNotes()
                        setSearchQuery("")
                        svNotes.text.clear()
                    }
                }

                true
            }

            fabNotesAdd.setOnClickListener {
                Intent(requireActivity(), AddOrUpdateItemActivity::class.java).also {
                    it.putExtra(
                        AddOrUpdateItemActivity.DESTINATION_PAGE,
                        AddOrUpdateItemActivity.ADDING_NOTES_PAGE
                    )
                    startActivity(it)
                }
            }

            rvNotes.apply {
                setHasFixedSize(true)
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = notesAdapter
            }
            rvNotes.startAnimation(
                AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.fade_in_and_scale_up
                )
            )

            getBgBadge()?.let {
                it.setTint(ContextCompat.getColor(requireContext(), R.color.grey_3))
                bottomSheetNotes.tvNoteUpdatedAtBottomSheetNote.background = it
            }

            with(viewModel) {
                selectedNote.observe(requireActivity()) {
                    activeNote = it
                }

                allNotes.observe(requireActivity()) {
                    notesAdapter?.submitList(it)
                }

                selectedNote.observe(requireActivity()) { note ->
                    bottomSheetNotes.tvNoteTitleBottomSheetNotes.text = note.title.capitalize()
                    bottomSheetNotes.tvNoteContentBottomSheetNotes.text = note.content.capitalize()
                    bottomSheetNotes.tvNoteLevelOfImportanceBottomSheetNote.background =
                        getBackgroundForLevelOfImportance(note.levelOfImportance)
                    bottomSheetNotes.tvNoteLevelOfImportanceBottomSheetNote.text =
                        getDescriptionForLevelOfImportanceIndex(note.levelOfImportance)
                    bottomSheetNotes.tvNoteUpdatedAtBottomSheetNote.text = note.formattedUpdatedAt()
                }

                shouldLoadingState.asLiveData().observe(requireActivity()) {
                    showLoading(it)
                }
            }
        }
    }

    private fun getBgBadge(): Drawable? =
        ResourcesHelper.getBackgroundBadge(requireContext())

    private fun getBackgroundForLevelOfImportance(levelOfImportance: Int): Drawable? =
        ResourcesHelper.getBackgroundBadgeForLevelOfImportance(requireContext(), levelOfImportance)

    private fun getDescriptionForLevelOfImportanceIndex(levelOfImportance: Int): String =
        ResourcesHelper.getDescriptionForLevelOfImportanceIndex(requireContext(), levelOfImportance)

    private fun showLoading(state: Boolean) {
        with(binding) {
            if (state) {
                progressbarNotes.show()
                rvNotes.hide()
            } else {
                progressbarNotes.hide()
                rvNotes.show()
            }
        }
    }

    override fun onNoteClick(note: Note) {
        binding.fabNotesAdd.animate().scaleX(0f).scaleY(0f)
            .setDuration(150).start()

        viewModel.setSelectedNote(note)
        bottomSheetBehaviorNotes?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private var bottomSheetCallback: BottomSheetBehavior.BottomSheetCallback? = null

    private var deletingAlert: AlertDialog? = null

    private var searchTextWatcher: TextWatcher? = null
}