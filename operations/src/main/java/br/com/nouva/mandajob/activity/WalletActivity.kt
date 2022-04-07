package br.com.nouva.mandajob.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import br.com.nouva.mandajob.R

class WalletActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var tvButtonAddNewCard: TextView
    private lateinit var layoutReturnAddNewMethod: LinearLayout
    private lateinit var cvButtonMenuCard: CardView

    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payments_methods)
        openIDS()

        //tv_button_add_new_card.setOnClickListener(this);
        //layout_return_add_new_method.setOnClickListener(this);
        cvButtonMenuCard.setOnClickListener(this)
    }

    private fun openIDS() {
        tvButtonAddNewCard = findViewById(R.id.tv_button_add_new_card)
        layoutReturnAddNewMethod = findViewById(R.id.layout_return_add_new_method)
        cvButtonMenuCard = findViewById(R.id.cv_button_menu_card)
    }

    private fun showPopUpMenu() {
        val popupMenu = PopupMenu(this@WalletActivity, cvButtonMenuCard)
        popupMenu.menuInflater.inflate(R.menu.menu_card_pop, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_edit_action -> {
                    Toast.makeText(context, "Edit action", Toast.LENGTH_SHORT).show()
                    return@OnMenuItemClickListener true
                }
                R.id.menu_create_new_action -> {
                    Toast.makeText(context, "Create new action", Toast.LENGTH_SHORT).show()
                    return@OnMenuItemClickListener true
                }
            }
            false
        })
        popupMenu.show()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tv_button_add_new_card -> {
                val intent = Intent(this@WalletActivity, NewPaymentActivity::class.java)
                startActivity(intent)
            }
            R.id.layout_return_add_new_method -> {
                val returnPage = Intent(this@WalletActivity, MigrateActivity::class.java)
                startActivity(returnPage)
                super@WalletActivity.finish()
            }
            R.id.cv_button_menu_card -> showPopUpMenu()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        super@WalletActivity.finish()
    }
}