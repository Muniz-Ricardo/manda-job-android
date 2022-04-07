package br.com.nouva.mandajob.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.nouva.core.animFloat
import br.com.nouva.core.data.BodyQuery
import br.com.nouva.core.data.KeyUser
import br.com.nouva.core.data.ResponseMoreQuery
import br.com.nouva.core.module.AppModule
import br.com.nouva.core.utils.KoinUtilities
import br.com.nouva.core.viewmodel.MainViewModel
import br.com.nouva.mandajob.R
import br.com.nouva.mandajob.adapter.AdapterOrder
import br.com.nouva.mandajob.controler.ControlerOrders
import br.com.nouva.mandajob.databinding.FragmentOrdersBinding
import br.com.nouva.mandajob.dialog.BottomSheetFilter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.internal.addHeaderLenient
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class OrdersFragment : Fragment() {

    private val loadModules by lazy { KoinUtilities.loadModules(AppModule.eachModules()) }
    private fun injectModules() = loadModules
    private val mainViewModel: MainViewModel by sharedViewModel()

    private var adapter: AdapterOrder? = null
    private lateinit var binding: FragmentOrdersBinding

    private var controler: ControlerOrders? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        injectModules()
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putBoolean("content", true)
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        onRestoreInstanceState(savedInstanceState)

        binding = FragmentOrdersBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        sharedPreferences = requireContext()
            .getSharedPreferences(STORE_FILE_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        controler = ControlerOrders(requireContext())

        initView()
        initObservers()

        return binding.root
    }

    private fun initView() {
        mainViewModel.getOrdersByQuery("")

        binding.recyclerviewOrder.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                when (recyclerView.scrollState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        animFloat(
                            binding.layFloat,
                            binding.tvTextButton, false)
                    }
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        Handler(Looper.getMainLooper()).postDelayed({
                            animFloat(
                                binding.layFloat,
                                binding.tvTextButton, true)
                        }, 1000)
                    }
                }
            }
        })

        Handler(Looper.getMainLooper()).postDelayed({
            animFloat(
                binding.layFloat,
                binding.tvTextButton, true)
        }, 4000)

        binding.layoutOpenFilter.setOnClickListener {
            showMenu()
        }
    }

    private fun initObservers() {
        mainViewModel.orders.observe(viewLifecycleOwner) {
            if (array != null) generateDataList(array, false)
            generateDataList(it, true)
        }
    }

    private fun <T> setReceived(list: List<T>?) {
        val gson = Gson()
        val json = gson.toJson(list)
        Companion[PREFS] = json
    }

    private val array: List<ResponseMoreQuery>?
        get() {
            var items: List<ResponseMoreQuery>? = null
            val serializedObject = sharedPreferences!!.getString(PREFS, null)
            if (serializedObject != null) {
                val gson = Gson()
                val type = object : TypeToken<List<ResponseMoreQuery>>() {}.type
                items = gson.fromJson(serializedObject, type)
            }
            return items
        }

    private fun generateDataList(
        body: List<ResponseMoreQuery>?, isUpdate: Boolean) {
        if (!isUpdate) {
            updateAnimUi()
            val manager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            adapter = AdapterOrder(requireContext(), body!!)
            binding.recyclerviewOrder.layoutManager = manager
            binding.recyclerviewOrder.adapter = adapter
            binding.recyclerviewOrder.setHasFixedSize(true)
            adapter!!.notifyItemRangeChanged(0, body.size)

        } else {
            update(body)
        }
        setReceived(body)
    }

    private fun update(body: List<ResponseMoreQuery>?) {
        startAnimFirstTime()
        val manager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
        adapter = AdapterOrder(requireContext(), body!!)
        binding.recyclerviewOrder.layoutManager = manager
        binding.recyclerviewOrder.adapter = adapter
        binding.recyclerviewOrder.setHasFixedSize(true)
        adapter!!.notifyItemRangeChanged(0, body.size)
    }

    private fun startAnimFirstTime() {
        val resId = R.anim.layout_animation_fall_down
        val animation = AnimationUtils.loadLayoutAnimation(requireContext(), resId)
        binding.recyclerviewOrder.layoutAnimation = animation
        ANIMATE_ON = true
    }

    private fun updateAnimUi() {
        val resId = R.anim.layout_animation_fall_down
        val animation = AnimationUtils.loadLayoutAnimation(requireContext(), resId)
        binding.recyclerviewOrder.layoutAnimation = animation
        ANIMATE_ON = true
    }

    private fun ordersRefine() {
        val sheetFragment = BottomSheetFilter()
        sheetFragment.show(requireActivity()
            .supportFragmentManager, sheetFragment.tag)
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val some = savedInstanceState.getBoolean("content")
        }
    }

    private fun showMenu() {
        val wrapper: ContextWrapper = ContextThemeWrapper(context, R.style.PopupMenu)
        val popupMenu = PopupMenu(wrapper, binding.makerPopup)

        popupMenu.menuInflater.inflate(R.menu.menu_orders_more, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.action_filter -> { ordersRefine() }
                R.id.action_new_order -> {
                    Toast.makeText(context, "Create new order", Toast.LENGTH_SHORT).show()
                    return@OnMenuItemClickListener true
                }
            }
            false
        })
        popupMenu.show()
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val STORE_FILE_NAME = "LIST_ORDER"
        private const val PREFS = "LIST"
        private var ANIMATE_ON = false
        private var sharedPreferences: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null


        fun newInstance(param1: String?, param2: String?): OrdersFragment {
            val fragment = OrdersFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }

        operator fun set(key: String?, value: String?) {
            editor!!.putString(key, value)
            editor!!.commit()
        }
    }
}