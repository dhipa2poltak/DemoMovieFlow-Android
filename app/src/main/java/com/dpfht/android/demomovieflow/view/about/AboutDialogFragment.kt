package com.dpfht.android.demomovieflow.view.about

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.dpfht.android.demomovieflow.BuildConfig
import com.dpfht.android.demomovieflow.R
import com.dpfht.android.demomovieflow.databinding.DialogAboutBinding

class AboutDialogFragment: DialogFragment() {

  private lateinit var binding: DialogAboutBinding

  companion object {
    fun newInstance(): AboutDialogFragment {
      return AboutDialogFragment()
    }
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    super.onCreateDialog(savedInstanceState)

    binding = DialogAboutBinding.inflate(requireActivity().layoutInflater, null, false)
    val layout = binding.root

    val b: AlertDialog.Builder = AlertDialog.Builder(requireContext())

    val sTitle = "${getString(R.string.app_name)}${getString(R.string.running_mode)}"
    binding.tvAppName.text = sTitle // getString(R.string.app_name)
    val str = "v${BuildConfig.VERSION_NAME}"
    binding.tvAppVersion.text = str

    b.context.theme.applyStyle(R.style.MyAlertDialog, true)
    b.setView(layout)

    binding.btnOk.setOnClickListener{
      this.dismiss()
    }

    return b.create()
  }

  override fun onResume() {
    super.onResume()

    val window = dialog?.window
    val params = window?.attributes
    params?.width = resources.getDimension(R.dimen.about_dialog_fragment_width).toInt()
    params?.height = resources.getDimension(R.dimen.about_dialog_fragment_height).toInt()
    window?.attributes = params
  }
}
