package br.com.nouva.mandajob.activity

import android.content.Intent
import android.os.Bundle
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
import br.com.nouva.core.MainActivity
import br.com.nouva.mandajob.R

class SignInUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var ivBrand: ImageView
    private lateinit var tvForgotPassword: TextView
    private lateinit var layButtonSign: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        init()
        spanableTextview()
        layButtonSign.setOnClickListener(this)
    }

    private fun init() {
        ivBrand = findViewById(R.id.image_sigin_login)
        tvForgotPassword = findViewById(R.id.tv_forgot_password)
        layButtonSign = findViewById(R.id.layout_button_sign)
    }

    private fun spanableTextview() {
        val spannable: Spannable = SpannableString("Esqueceu sua senha? mude aqui.")
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.colorGreen)),
                20, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvForgotPassword.text = spannable
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.layout_button_sign -> {
                val intent = Intent(this@SignInUserActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}