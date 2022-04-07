package br.com.nouva.mandajob.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.nouva.mandajob.R
import br.com.nouva.mandajob.viewModel.ModelViewInbox

class InboxActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var model: ModelViewInbox? = null
    private val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)
        init()
    }

    private fun init() {
        recyclerView = findViewById(R.id.recyclerview_inbox)
        model = ModelViewInbox(window.decorView.rootView, recyclerView, context)
        model!!.setConnectionServerInbox()
    }

}