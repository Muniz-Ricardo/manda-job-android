package br.com.nouva.migrate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import br.com.nouva.migrate.databinding.ActivityMainMigrateBinding

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainMigrateBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main_migrate
        )

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragContainerViewMigrate) as NavHostFragment
        navController = navHostFragment.navController
    }

}