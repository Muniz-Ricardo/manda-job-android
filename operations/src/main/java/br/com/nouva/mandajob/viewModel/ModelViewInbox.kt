package br.com.nouva.mandajob.viewModel

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.nouva.mandajob.R
import br.com.nouva.mandajob.adapter.AdapterInbox
import br.com.nouva.mandajob.body.BodySearch
import br.com.nouva.mandajob.helper.ApiGetService
import br.com.nouva.mandajob.model.ModelInbox
import br.com.nouva.mandajob.service.Service
import retrofit2.Call
import retrofit2.Response

class ModelViewInbox(var view: View, var recyclerView: RecyclerView, var context: Context) {

    private var adapter: AdapterInbox? = null
    private val toolbar = view.findViewById(R.id.top_bar_inbox) as RelativeLayout
    private val progress = view.findViewById(R.id.progress_bar_inbox) as ProgressBar

    fun setConnectionServerInbox() {
        toolbar.visibility = View.GONE
        progress.visibility = View.VISIBLE

        val service = ApiGetService.getRetrofitInstance().create(Service::class.java)
        val bodySearch = BodySearch("All", "onload")

        service.seeInboxUser(bodySearch)!!.enqueue(object : retrofit2.Callback<List<ModelInbox?>?> {
            override fun onResponse(call: Call<List<ModelInbox?>?>, response: Response<List<ModelInbox?>?>) {
                setGenerateListInbox(response.body())
            }

            override fun onFailure(call: Call<List<ModelInbox?>?>, t: Throwable) {

            }

        })
    }

    private fun setGenerateListInbox(body: List<ModelInbox?>?) {
        adapter = AdapterInbox(view.context, body)
        val layoutManager = LinearLayoutManager(view.context,
                LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        recyclerView.setHasFixedSize(true)
        //adapter!!.notifyItemRangeChanged(0, body!!.size)

        progress.visibility = View.GONE
        toolbar.visibility = View.VISIBLE

    }

}