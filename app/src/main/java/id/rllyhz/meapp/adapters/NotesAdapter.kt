package id.rllyhz.meapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.rllyhz.meapp.R
import id.rllyhz.meapp.data.models.Note
import id.rllyhz.meapp.databinding.ItemNotesBinding
import id.rllyhz.meapp.utils.ColorHelper
import id.rllyhz.meapp.utils.formattedUpdatedAt

class NotesAdapter : ListAdapter<Note, NotesAdapter.NotesViewHolder>(NotesComparator()) {
    private var lastPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
            setAnimationToItemView(holder.itemView, position)
        }
    }

    override fun onViewDetachedFromWindow(holder: NotesViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.clearAnimation()
    }

    private fun setAnimationToItemView(itemView: View, position: Int) {
        if (position > lastPosition) {
            val animation =
                AnimationUtils.loadAnimation(
                    itemView.context.applicationContext,
                    R.anim.fade_in_and_slide_in_top
                )
            itemView.startAnimation(animation)
            lastPosition = position
        }
    }

    // viewholder
    inner class NotesViewHolder(
        private val binding: ItemNotesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.apply {
                tvItemNotesTitle.text = note.title
                tvItemNotesContent.text = note.content
                tvItemNotesCreatedAt.text = note.formattedUpdatedAt()
                cvItemNotes.setCardBackgroundColor(ColorHelper.getRandomColor(itemView.context))

                itemView.setOnClickListener {
                    callback?.onNoteClick(note)
                }
            }
        }

        fun clearAnimation() {
            itemView.clearAnimation()
        }
    }

    // Diffutil for handling comparison of items
    class NotesComparator : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem == newItem

    }

    // for item click listener
    interface NoteItemClickCallback {
        fun onNoteClick(note: Note)
    }

    private var callback: NoteItemClickCallback? = null

    fun setOnItemClickListener(listener: NoteItemClickCallback) {
        callback = listener
    }
}