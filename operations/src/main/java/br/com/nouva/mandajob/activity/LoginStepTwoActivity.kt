package br.com.nouva.mandajob.activity

import android.content.Intent
import android.graphics.drawable.PictureDrawable
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
import br.com.nouva.mandajob.R
import com.bumptech.glide.RequestBuilder

class LoginStepTwoActivity : AppCompatActivity(), View.OnClickListener {

    private val requestBuilder: RequestBuilder<PictureDrawable>? = null
    private lateinit var image: ImageView
    private lateinit var description: TextView
    private lateinit var arrow: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_step_two)
        init()
        spanableTextview()
        arrow.setOnClickListener(this)
    }

    private fun init() {
        description = findViewById(R.id.tv_label_step_two)
        arrow = findViewById(R.id.layout_arrow_next_two)
        image = findViewById(R.id.image_step_two)
    }

    private fun spanableTextview() {
        val spannable: Spannable = SpannableString("Oferte um serviço e consiga o seu valor.")
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.colorSecondary)),
                30, 34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        description.text = spannable
    }

    private fun nextStepLogin() {
        Handler().postDelayed({
            val intent = Intent(this@LoginStepTwoActivity, LoginStepThreeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_up, R.anim.fade_out_up)
        }, 0)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.layout_arrow_next_two) {
            nextStepLogin()
        }
    }
}