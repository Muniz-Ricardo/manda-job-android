package br.com.nouva.chat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import br.com.nouva.chat.databinding.ActivityMainChatBinding

class MainChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainChatBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main_chat
        )

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerViewChat) as NavHostFragment
        navController = navHostFragment.navController

        initView()
    }

    private fun initView() {
        supportFragmentManager.fragments
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

}