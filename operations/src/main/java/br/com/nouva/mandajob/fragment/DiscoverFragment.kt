package br.com.nouva.mandajob.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.nouva.chat.MainChatActivity
import br.com.nouva.core.createExtra
import br.com.nouva.core.data.DiscoverMore
import br.com.nouva.core.data.KeyUser
import br.com.nouva.core.module.AppModule
import br.com.nouva.core.tos
import br.com.nouva.core.utils.KoinUtilities
import br.com.nouva.core.viewmodel.MainViewModel
import br.com.nouva.mandajob.activity.MoreProjectsActivity
import br.com.nouva.mandajob.adapter.AdapterEveryForYou
import br.com.nouva.mandajob.adapter.AdapterSearchForYou
import br.com.nouva.mandajob.databinding.FragmentDiscoverBinding
import br.com.nouva.mandajob.listener.OnClickListenerItem
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DiscoverFragment : Fragment() {

    private val loadModules by lazy { KoinUtilities.loadModules(AppModule.eachModules()) }
    private fun injectModules() = loadModules
    private val mainViewModel: MainViewModel by sharedViewModel()

    private lateinit var binding: FragmentDiscoverBinding

    private var adapter: AdapterSearchForYou? = null
    private var adapterEvery: AdapterEveryForYou? = null

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putBoolean("content", true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectModules()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {

        binding = FragmentDiscoverBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        initView()
        initObservers()
        toService()

        return binding.root
    }

    private fun initView() {
        mainViewModel.getDiscoverArea("")

        binding.buttonMoreForYou.setOnClickListener {
            intentExtra(1)
        }

        binding.buttonMoreGeneral.setOnClickListener {
            intentExtra(2)
        }
    }

    private fun initObservers() {
        mainViewModel.discover.observe(viewLifecycleOwner) {
            setAdapter(it)
        }
    }

    private fun intentExtra(position: Int) {
        var count = 0
        val extras =
            arrayListOf<Pair<String, Any>>(
                Pair("from", "All"),
                Pair("sub", "onload"),
                Pair("list", position)
            )
        extras.forEach {
            count++
            val intent = requireActivity().createExtra(it.first,
                (if (it.first == "list") it.second as Int else it.second).tos(),
                null, MoreProjectsActivity::class.java)
            if (count == 3) startActivity(intent)
        }
    }

    private fun toService() {
        binding.pgbMore.visibility = View.VISIBLE
        binding.layDiscover.visibility = View.GONE
    }

    private fun setAdapter(body: List<DiscoverMore?>?) {
        adapter = AdapterSearchForYou(
                requireContext(),
                body!!
            )

        adapterEvery = AdapterEveryForYou(
            requireContext(),
            body
        )

        adapter!!.onClickItem(object : OnClickListenerItem {
            override fun tokenUser(key: String) {
                mainViewModel.setKeyUser(KeyUser(key))
                startActivity(requireActivity().createExtra(
                    "key", key, null, MainChatActivity::class.java))
            }
        })

        adapterEvery!!.onClickItem(object : OnClickListenerItem {
            override fun tokenUser(key: String) {
                mainViewModel.setKeyUser(KeyUser(key))
                startActivity(requireActivity().createExtra(
                    "key", key, null, MainChatActivity::class.java))
            }
        })

        val managerFor = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
        binding.rvForYou.layoutManager = managerFor
        binding.rvForYou.adapter = adapter

        val managerAll = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
        managerAll.stackFromEnd = true
        managerAll.reverseLayout = true

        binding.rvGeneral.layoutManager = managerAll
        binding.rvGeneral.adapter = adapterEvery

        binding.rvForYou.isNestedScrollingEnabled = false
        binding.rvGeneral.isNestedScrollingEnabled = false

        binding.pgbMore.visibility = View.GONE
        binding.layDiscover.visibility = View.VISIBLE
    }

}