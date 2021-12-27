package id.rllyhz.meapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.rllyhz.meapp.data.models.Reminder
import id.rllyhz.meapp.databinding.ItemRemindersHorizontalBinding
import id.rllyhz.meapp.utils.ColorHelper
import id.rllyhz.meapp.utils.formattedNotifyingAt

class RemindersHorizontalAdapter :
    ListAdapter<Reminder, RemindersHorizontalAdapter.RemindersHorizontalViewHolder>(
        RemindersHorizontalComparator()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RemindersHorizontalViewHolder {
        val binding =
            ItemRemindersHorizontalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return RemindersHorizontalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RemindersHorizontalViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null)
            holder.bind(currentItem)
    }

    // view holder
    inner class RemindersHorizontalViewHolder(
        private val binding: ItemRemindersHorizontalBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(reminder: Reminder) {
            binding.apply {
                tvItemRemindersHorizontalTitle.text = reminder.title
                tvItemRemindersHorizontalDescription.text = reminder.description
                tvItemRemindersHorizontalNotifyingAt.text = reminder.formattedNotifyingAt()
                cvItemReminders.setCardBackgroundColor(ColorHelper.getRandomColor(itemView.context))

                itemView.setOnClickListener {
                    callback?.onReminderClick(reminder)
                }
            }
        }
    }

    // Diffutil
    class RemindersHorizontalComparator : DiffUtil.ItemCallback<Reminder>() {
        override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean =
            oldItem == newItem

    }

    // item click callback
    interface ReminderItemClickCallback {
        fun onReminderClick(reminder: Reminder)
    }

    private var callback: ReminderItemClickCallback? = null

    fun setOnItemClickListener(listener: ReminderItemClickCallback) {
        callback = listener
    }
}