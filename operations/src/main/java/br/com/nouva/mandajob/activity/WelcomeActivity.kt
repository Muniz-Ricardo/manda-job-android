package br.com.nouva.mandajob.activity

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import br.com.nouva.mandajob.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.request.transition.Transition


class WelcomeActivity : AppCompatActivity(), View.OnClickListener {

    private val context: Context = this
    private val factory = DrawableCrossFadeFactory.Builder()
            .setCrossFadeEnabled(true).build()

    private lateinit var tvCheckBoxTerms: TextView
    private lateinit var cardPreLoginFreelancer: CardView
    private lateinit var cardPreLoginContractor: CardView
    private lateinit var cvButtonLoginUser: CardView
    private lateinit var checkBox: CheckBox
    private lateinit var ivBackgroundPreLogin: ImageView
    private lateinit var background: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_register)
        init()
        spanableTextviewTerms()
        cardPreLoginFreelancer.setOnClickListener(this)
        cvButtonLoginUser.setOnClickListener(this)
        cardPreLoginContractor.setOnClickListener(this)
        tvCheckBoxTerms.setOnClickListener(this)
    }

    private fun init() {
        //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        tvCheckBoxTerms = findViewById(R.id.tv_check_box_terms)
        cvButtonLoginUser = findViewById(R.id.cv_button_login_user)
        checkBox = findViewById(R.id.checkbox_agree_terms)
        cardPreLoginFreelancer = findViewById(R.id.card_pre_login_freelancer)
        cardPreLoginContractor = findViewById(R.id.card_pre_login_contractor)
        ivBackgroundPreLogin = findViewById(R.id.iv_background_pre_login)
        background = findViewById(R.id.background)
    }

    private fun spanableTextviewTerms() {
        val spannable: Spannable = SpannableString("I agree all Terms of service")
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.colorSecondary)),
                12, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvCheckBoxTerms.text = spannable
    }

    private fun setImageBackgroundLayout() {
        val drawable = "background_pre_register_019"
        Glide.with(context)
                .asBitmap()
                .load(drawable.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(BitmapTransitionOptions.withCrossFade(factory))
                .dontTransform()
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                        ivBackgroundPreLogin.setImageBitmap(resource)
                    }
                })
    }

    private fun String?.getImage(): Int {
        return this@WelcomeActivity.resources.getIdentifier(this,
                "drawable", this@WelcomeActivity.packageName)
    }

    private fun circularRevealActivity() {
        val cx = this.background.right - getDips(250)
        val cy = this.background.bottom - getDips(190)
        val finalRadius = Math.max(this.background.width, this.background.height).toFloat()
        val circularReveal = ViewAnimationUtils.createCircularReveal(
                this.background,
                cx,
                cy, 0f,
                finalRadius)
        circularReveal.duration = 700
        this.background.visibility = View.VISIBLE
        backgroundColorThread()
        circularReveal.start()
    }

    private fun backgroundColorThread() {
        this.background.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorSecondary))
        Handler().postDelayed({
            animBackground(this.background)
            val intentAccountExist = Intent(this@WelcomeActivity, SignInUserActivity::class.java)
            startActivity(intentAccountExist)
        }, 300)
    }

    private fun animBackground(view: View?) {
        val color = 0x8b8dcf
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

    override fun onBackPressed() {
        super.onBackPressed()
        super@WelcomeActivity.finish()
    }

    @SuppressLint("NonConstantResourceId")
    override fun onClick(view: View) {
        when (view.id) {
            R.id.cv_button_login_user -> {
                val intent = Intent(this@WelcomeActivity, SignInUserActivity::class.java)
                startActivity(intent)
            }
            R.id.card_pre_login_freelancer -> {
                val intent = Intent(this@WelcomeActivity, LoginStepOneActivity::class.java)
                intent.putExtra("item", 1)
                startActivity(intent)
            }
            R.id.card_pre_login_contractor -> {
                val intentContractor = Intent(this@WelcomeActivity, LoginStepOneActivity::class.java)
                intentContractor.putExtra("item", 2)
                startActivity(intentContractor)
            }
            R.id.tv_check_box_terms -> {
                checkBox.isChecked = !checkBox.isChecked
            }
        }
    }
}