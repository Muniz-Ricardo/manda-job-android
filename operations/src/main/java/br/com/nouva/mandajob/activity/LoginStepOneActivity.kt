package br.com.nouva.mandajob.activity

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.com.nouva.mandajob.R
import com.bumptech.glide.RequestBuilder

class LoginStepOneActivity : AppCompatActivity(), View.OnClickListener {

    private val requestBuilder: RequestBuilder<PictureDrawable>? = null
    private lateinit var image: ImageView
    private lateinit var description: TextView
    private lateinit var arrow: LinearLayout
    private lateinit var layout: RelativeLayout
    private lateinit var background: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move)
        setContentView(R.layout.activity_login_step_one)
        init()
        spanableTextview()
        arrow.setOnClickListener(this)
    }

    private fun init() {
        background = findViewById(R.id.background)
        description = findViewById(R.id.tv_label_step_one)
        arrow = findViewById(R.id.layout_arrow_next)
        image = findViewById(R.id.image_step)
        layout = findViewById(R.id.scroll_step_one)
    }


    private fun circularRevealActivity(item: Int) {
        if (item == 1) {
            val cx = background.right - getDips(250)
            val cy = background.bottom - getDips(190)
            val finalRadius = Math.max(background.width, background.height).toFloat()
            val circularReveal = ViewAnimationUtils.createCircularReveal(
                    background,
                    cx,
                    cy, 0f,
                    finalRadius)
            circularReveal.duration = 700
            background.visibility = View.VISIBLE
            backgroundColorThread(item)
            circularReveal.start()
        } else {
            val cx = background.right - getDips(250)
            val cy = background.bottom - getDips(100)
            val finalRadius = Math.max(background.width, background.height).toFloat()
            val circularReveal = ViewAnimationUtils.createCircularReveal(
                    background,
                    cx,
                    cy, 0f,
                    finalRadius)
            circularReveal.duration = 700
            background.visibility = View.VISIBLE
            backgroundColorThread(item)
            circularReveal.start()
        }
    }

    private fun backgroundColorThread(item: Int) {
        if (item == 1) {
            layout.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorSecondary))
        } else {
            layout.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorGreen))
        }
        Handler().postDelayed({
            animBackground(layout, item)
        }, 300)
    }

    private fun animBackground(view: View?, item: Int) {
        val color: Int = if (item == 1) {
            0x8b8dcf
        } else {
            0x96cf8b
        }
        val backgroundColorAnimator = ObjectAnimator.ofObject(view,
                "backgroundColor",
                ArgbEvaluator(),
                color,
                0x272729)
        backgroundColorAnimator.duration = 1000
        backgroundColorAnimator.start()
    }

    private fun getDips(dps: Int): Int {
        val resources = resources
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dps.toFloat(),
                resources.displayMetrics).toInt()
    }

    private fun spanableTextview() {
        val spannable: Spannable = SpannableString("Unimos pessoas com habilidades que outras precisam.")
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.colorSecondary)),
                19, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        description.text = spannable
    }

    private fun nextStepLogin() {
        Handler().postDelayed({
            val intent = Intent(this@LoginStepOneActivity, LoginStepTwoActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_up, R.anim.fade_out_up)
        }, 0)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.layout_arrow_next) {
            nextStepLogin()
        }
    }
}