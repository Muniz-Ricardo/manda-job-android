package br.com.nouva.mandajob.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import br.com.nouva.mandajob.R

class DialogPulse(context: Context?) : AppCompatDialog(context) {

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_pulse_learn)

        val builder = AlertDialog.Builder(context)
        val view = window!!.decorView

        view.setBackgroundResource(android.R.color.transparent)
        builder.create()
    }
}