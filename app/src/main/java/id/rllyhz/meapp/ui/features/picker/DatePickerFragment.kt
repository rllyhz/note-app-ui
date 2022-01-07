package id.rllyhz.meapp.ui.features.picker

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var listener: DialogDateListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as DialogDateListener
    }

    override fun onDetach() {
        super.onDetach()

        if (listener != null)
            listener = null
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener?.onDialogDateSet(tag, year, month, dayOfMonth)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        Calendar.getInstance().let {
            val year = it.get(Calendar.YEAR)
            val month = it.get(Calendar.MONTH)
            val dayOfMonth = it.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(requireActivity(), this, year, month, dayOfMonth)
        }

    interface DialogDateListener {
        fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int)
    }
}