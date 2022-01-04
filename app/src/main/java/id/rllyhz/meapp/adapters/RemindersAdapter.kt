package id.rllyhz.meapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.rllyhz.meapp.R
import id.rllyhz.meapp.data.models.Reminder
import id.rllyhz.meapp.databinding.ItemRemindersBinding
import id.rllyhz.meapp.utils.ColorHelper
import id.rllyhz.meapp.utils.formattedNotifyingAt

class RemindersAdapter :
    ListAdapter<Reminder, RemindersAdapter.RemindersViewHolder>(RemindersComparator()) {

    private var lastPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemindersViewHolder {
        val binding =
            ItemRemindersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RemindersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RemindersViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
            setAnimationToItemView(holder.itemView, position)
        }
    }

    override fun onViewDetachedFromWindow(holder: RemindersViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.clearAnimation()
    }

    private fun setAnimationToItemView(itemView: View, position: Int) {
        if (position > lastPosition) {
            val animation =
                AnimationUtils.loadAnimation(
                    itemView.context.applicationContext,
                    R.anim.fade_in_and_scale_up
                )
            itemView.startAnimation(animation)
            lastPosition = position
        }
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

        fun clearAnimation() {
            itemView.clearAnimation()
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