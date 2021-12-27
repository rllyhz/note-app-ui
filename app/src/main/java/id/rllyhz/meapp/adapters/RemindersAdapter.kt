package id.rllyhz.meapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.rllyhz.meapp.data.models.Reminder
import id.rllyhz.meapp.databinding.ItemRemindersBinding
import id.rllyhz.meapp.utils.ColorHelper
import id.rllyhz.meapp.utils.formattedNotifyingAt

class RemindersAdapter :
    ListAdapter<Reminder, RemindersAdapter.RemindersViewHolder>(RemindersComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemindersViewHolder {
        val binding =
            ItemRemindersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RemindersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RemindersViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null)
            holder.bind(currentItem)
    }

    // view holder
    inner class RemindersViewHolder(
        private val binding: ItemRemindersBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(reminder: Reminder) {
            binding.apply {
                tvItemRemindersTitle.text = reminder.title
                tvItemRemindersDescription.text = reminder.description
                tvItemRemindersNotifyingAt.text = reminder.formattedNotifyingAt()
                cvItemReminders.setCardBackgroundColor(ColorHelper.getRandomColor(itemView.context))

                itemView.setOnClickListener {
                    callback?.onReminderClick(reminder)
                }
            }
        }
    }

    // Diffutil
    class RemindersComparator : DiffUtil.ItemCallback<Reminder>() {
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