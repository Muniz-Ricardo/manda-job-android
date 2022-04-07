package br.com.nouva.mandajob

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import br.com.nouva.mandajob.databinding.ActivityMainOpBinding
import br.com.nouva.mandajob.dialog.DialogPulse
import br.com.nouva.mandajob.fragment.DiscoverFragment
import br.com.nouva.mandajob.fragment.OrdersFragment
import br.com.nouva.mandajob.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var mutableList: ArrayList<Fragment> = ArrayList()

    private var discover: DiscoverFragment? = null
    private var orders: OrdersFragment? = null
    private var profile: ProfileFragment? = null

    private lateinit var binding: ActivityMainOpBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_orders -> {
                    if (orders == null) {
                        orders = OrdersFragment()
                    }
                    displayFragment(orders)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_new_order -> {
                    if (discover == null) {
                        discover = DiscoverFragment()
                    }
                    displayFragment(discover)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_profile -> {
                    if (profile == null) {
                        profile = ProfileFragment()
                    }
                    displayFragment(profile)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main_op
        )

        initView()
    }

    private fun initView() {
        discover = DiscoverFragment()
        orders = OrdersFragment()
        profile = ProfileFragment()

        mutableList.add(discover!!)
        mutableList.add(orders!!)
        mutableList.add(profile!!)

        binding.bottomNavigationMenu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        binding.bottomNavigationMenu.selectedItemId = R.id.action_orders
    }

    private fun getFragmentIndex(fragment: Fragment?): Int {
        val index = -1
        for (i in mutableList.indices) {
            if (fragment.hashCode() == mutableList[i].hashCode()) {
                return i
            }
        }
        return index
    }

    private fun displayFragment(fragment: Fragment?) {
        val index = getFragmentIndex(fragment)
        val transaction = supportFragmentManager.beginTransaction()
        if (fragment!!.isAdded) {
            transaction.show(fragment)
        } else {
            transaction.add(R.id.container, fragment)
        }
        for (i in mutableList.indices) {
            if (mutableList[i].isAdded && i != index) {
                transaction.hide(mutableList[i])
            }
        }
        transaction.commit()
    }

    override fun onBackPressed() {
        finalized()
    }

    private fun finalized() {
        if (!orders!!.isAdded || orders!!.isHidden) {
            displayFragment(orders)
            binding.bottomNavigationMenu.selectedItemId = R.id.action_orders
        } else {
            super@MainActivity.finish()
        }
    }

    private fun dialogPulseOpen() {
        val pulse = DialogPulse(this)
        pulse.show()
    }
}