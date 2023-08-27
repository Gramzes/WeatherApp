package com.gramzin.weather.presentation.current_weather_screen

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.gramzin.weather.R

class PermissionDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getString(R.string.permission_is_required_title))
                .setMessage(getString(R.string.permission_required_message))
                .setPositiveButton(
                    getString(R.string.permission_diaolog_ok_button)
                ) { dialog, id ->
                    setFragmentResult(RESULT_KEY, bundleOf(RESULT_VALUE_KEY to true))
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object{
        const val RESULT_KEY = "PERMISSION_DIALOG_RESULT"
        const val RESULT_VALUE_KEY = "PERMISSION_DIALOG_RESULT_VALUE"
    }
}