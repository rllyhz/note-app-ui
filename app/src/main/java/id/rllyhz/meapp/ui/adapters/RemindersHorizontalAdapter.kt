package id.rllyhz.meapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.rllyhz.meapp.R
import id.rllyhz.meapp.data.models.Reminder
import id.rllyhz.meapp.databinding.ItemRemindersHorizontalBinding
import id.rllyhz.meapp.utils.ResourcesHelper
import id.rllyhz.meapp.utils.capitalize
import id.rllyhz.meapp.utils.formattedNotifyingAt

class RemindersHorizontalAdapter :
    ListAdapter<Reminder, RemindersHorizontalAdapter.RemindersHorizontalViewHolder>(
        RemindersHorizontalComparator()
    ) {

    private var lastPosition: Int = -1

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
        getItem(position)?.let {
            holder.bind(it)
            setAnimationToItemView(holder.itemView, position)
        }
    }

    override fun onViewDetachedFromWindow(holder: RemindersHorizontalViewHolder) {
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
    inner class RemindersHorizontalViewHolder(
        private val binding: ItemRemindersHorizontalBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(reminder: Reminder) {
            binding.apply {
                tvItemRemindersHorizontalTitle.text = reminder.title.capitalize()
                tvItemRemindersHorizontalDescription.text = reminder.description.capitalize()
                tvItemRemindersHorizontalNotifyingAt.text = reminder.formattedNotifyingAt()
                cvItemReminders.setCardBackgroundColor(ResourcesHelper.getRandomColor(itemView.context))

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