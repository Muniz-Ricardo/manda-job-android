package br.com.nouva.mandajob.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.com.nouva.mandajob.MainActivity
import br.com.nouva.mandajob.R

class LoginStepThreeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var cardDev: LinearLayout
    private lateinit var cardDesign: LinearLayout
    private lateinit var cardEscritor: LinearLayout
    private lateinit var cardCoach: LinearLayout
    private lateinit var cardOther: LinearLayout
    private lateinit var arrow: LinearLayout

    private lateinit var tvDev: TextView
    private lateinit var tvDesign: TextView
    private lateinit var tvEscritor: TextView
    private lateinit var tvCoach: TextView
    private lateinit var tvOther: TextView
    private lateinit var description: TextView

    private lateinit var plus: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_step_three)
        init()
        spanableTextview()
        arrow.setOnClickListener(this)
        cardDev.setOnClickListener(this)
        cardDesign.setOnClickListener(this)
        cardEscritor.setOnClickListener(this)
        cardCoach.setOnClickListener(this)
        cardOther.setOnClickListener(this)
    }

    private fun init() {
        cardDev = findViewById(R.id.layout_card_dev_step_three)
        cardDesign = findViewById(R.id.layout_card_design_step_three)
        cardEscritor = findViewById(R.id.layout_card_escritor_step_three)
        cardCoach = findViewById(R.id.layout_card_coach_step_three)
        cardOther = findViewById(R.id.layout_card_other_step_three)
        arrow = findViewById(R.id.layout_arrow_next_three)

        tvDev = findViewById(R.id.tv_work_dev_step_three)
        tvDesign = findViewById(R.id.tv_work_design_step_three)
        tvEscritor = findViewById(R.id.tv_work_escritor_step_three)
        tvCoach = findViewById(R.id.tv_work_coach_step_three)
        tvOther = findViewById(R.id.tv_work_other_step_three)
        description = findViewById(R.id.tv_label_step_three)

        plus = findViewById(R.id.image_plus_work_step_three)
    }

    private fun nextStepLogin() {
        Handler().postDelayed({
            val intent = Intent(this@LoginStepThreeActivity, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_up, R.anim.fade_out_up)
        }, 0)
    }

    private fun spanableTextview() {
        val spannable: Spannable = SpannableString("Quais áreas você domina?")
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.colorSecondary)),
                6, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        description.text = spannable
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.layout_arrow_next_three -> nextStepLogin()
            R.id.layout_card_dev_step_three -> {
                cardDev.setBackgroundResource(R.drawable.shape_selected_login)
                tvDev.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
            }
            R.id.layout_card_design_step_three -> {
                cardDesign.setBackgroundResource(R.drawable.shape_selected_login)
                tvDesign.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
            }
            R.id.layout_card_escritor_step_three -> {
                cardEscritor.setBackgroundResource(R.drawable.shape_selected_login)
                tvEscritor.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
            }
            R.id.layout_card_coach_step_three -> {
                cardCoach.setBackgroundResource(R.drawable.shape_selected_login)
                tvCoach.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
            }
            R.id.layout_card_other_step_three -> {
                cardOther.setBackgroundResource(R.drawable.shape_selected_login)
                tvOther.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
                plus.setImageResource(R.drawable.plus_white)
            }
        }
    }

}


