package id.rllyhz.meapp.ui.feature.notes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.rllyhz.meapp.adapters.NotesAdapter
import id.rllyhz.meapp.data.models.Note
import id.rllyhz.meapp.databinding.FragmentNotesBinding
import id.rllyhz.meapp.ui.adding_item.AddItemActivity

class NotesFragment : Fragment(), NotesAdapter.NoteItemClickCallback {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModels()
    private var notesAdapter: NotesAdapter? = null

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
    }

    private fun setupUI() {
        with(binding) {
            deletingAlert = AlertDialog.Builder(requireContext())
                .setTitle("Deleting Note")
                .setMessage("Are you sure want to delete this item?")
                .setCancelable(true)
                .setNegativeButton("Cancel") { _, _ ->
                    Toast.makeText(requireContext(), "Cancelling dialog", Toast.LENGTH_SHORT).show()
                    deletingAlert?.cancel()
                }
                .setPositiveButton("Yes") { _, _ ->
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

            bottomSheetBehaviorNotes = BottomSheetBehavior.from(bottomSheetContainerNotes)
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

            fabNotesAdd.setOnClickListener {
                Intent(requireActivity(), AddItemActivity::class.java).also {
                    it.putExtra(AddItemActivity.DESTINATION_PAGE, AddItemActivity.ADDING_NOTES_PAGE)
                    startActivity(it)
                }
            }

            rvNotes.apply {
                setHasFixedSize(true)
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = notesAdapter
            }

            viewModel.getAllNotes().observe(requireActivity()) {
                notesAdapter?.submitList(it)
            }
        }
    }

    override fun onNoteClick(note: Note) {
        with(binding) {
            fabNotesAdd.animate().scaleX(0f).scaleY(0f)
                .setDuration(150).start()

            tvNoteTitleBottomSheetNotes.text = note.title
            tvNoteContentBottomSheetNotes.text = note.content
        }

        viewModel.setSelectedNote(note)
        bottomSheetBehaviorNotes?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private var bottomSheetCallback: BottomSheetBehavior.BottomSheetCallback? = null

    private var deletingAlert: AlertDialog? = null
}