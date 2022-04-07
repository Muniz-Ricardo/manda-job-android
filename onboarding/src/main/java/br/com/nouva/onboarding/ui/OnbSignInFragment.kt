package br.com.nouva.onboarding.ui

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDoneException
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.nouva.core.setColouredSpan
import br.com.nouva.core.tos
import br.com.nouva.onboarding.R
import br.com.nouva.onboarding.User
import br.com.nouva.onboarding.databinding.FragmentOnbSiginBinding
import br.com.nouva.onboarding.databinding.SuccessEmailForgetBinding
import com.bumptech.glide.util.Util
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging


class OnbSignInFragment : Fragment() {

    private val RC_SIGN_IN: Int = 0
    private lateinit var binding: FragmentOnbSiginBinding

    private var auth: FirebaseAuth? = null

    private lateinit var gso: GoogleSignInOptions

    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentOnbSiginBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        Login()

        initView()
        initObservers()

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this.requireContext(), gso)

        binding.tvForgotPassword.setOnClickListener {
            alerta1RRecuperacaoSenha()
        }

        binding.btnGoogleSingIn.setOnClickListener {
            signInGoogle()
        }


        return binding.root
    }
    private fun alerta1RRecuperacaoSenha(){
        val view = View.inflate(context, R.layout.dialg_forget_password, null)
        val username = view.findViewById<EditText>(R.id.et_username)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val closeDialog = view.findViewById<LinearLayout>(R.id.close_dialog)

        closeDialog.setOnClickListener {
            dialog.dismiss()
        }

        val btn = view.findViewById<Button>(R.id.btn_enviar_email)
        btn.setOnClickListener {
            if (username.text.tos().isBlank()){

                username.error = "Campo obrigatório em branco!"

            }
          recuperePassword(username)


        }


    }


    private fun alertaReecuperacaoSenha(){
        val view = View.inflate(context, R.layout.success_email_forget, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btn1 = view.findViewById<Button>(R.id.btn_email_enviado)

        btn1.setOnClickListener {
            dialog.dismiss()
        }

    }
    private fun recuperePassword(username : EditText){

        if (username.text.tos().isEmpty()){
            username.error = "Campo obrigatório em branco!"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.tos()).matches()){
            return
        }

        auth?.sendPasswordResetEmail(username.text.tos())
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful){
                    alertaReecuperacaoSenha()
                }else{
                    val erro = task.exception.tos()
                    errosFirebse(erro)

                    Log.d("erroF",task.exception.tos())

                }

            }


    }

    private fun errosFirebse(erro: String){

        if (erro.contains("There is no user record corresponding to this identifier")){
            Toast.makeText(context,"Esse e-mail é inválido!",Toast.LENGTH_LONG).show()

        }


    }


    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    retrieveAndStoreToken()

                    Log.d(TAG, "signInWithCredential:success")
                    Toast.makeText(context, "Authentication sucess",
                        Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(context, "Authentication failed",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun retrieveAndStoreToken(){
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    val token = task.result

                    val userId = FirebaseAuth.getInstance().currentUser!!.uid

                    FirebaseDatabase.getInstance()
                        .getReference("tokens")
                        .child(userId)
                        .setValue(token)

                }
            }

    }



    private fun Login(){

        binding.layoutButtonSign.setOnClickListener {

            val email = binding.edtEmailStepLogin.text.tos()
            val password = binding.edtPasswordStepLogin.text.tos()

            if(email.isBlank() || password.isBlank()){
                Toast.makeText(context,
                    "Algum texto obrigatório está em branco!",
                    Toast.LENGTH_LONG).show()
            }else{
                login(email, password)
            }
            
        }
    }

    private fun login(email: String, password: String ){
        auth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    retrieveAndStoreToken()
                    val bundle = bundleOf(
                        "EMAIL" to email
                    )
                    findNavController().navigate(


                        R.id.onbLoginStepTwoFragment, bundle)


                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(context, "Authentication sucess",
                        Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }


    }

    private fun initObservers() {
        auth = Firebase.auth
    }

    private fun initView() {
        binding.tvForgotPassword.setColouredSpan(
            "Esqueceu sua senha? mude aqui.",
            20, 30, lazy {
                ContextCompat.getColor(
                    requireContext(),
                    br.com.nouva.migrate.R.color.colorSecondary
                )
            }.value
        )
    }
}