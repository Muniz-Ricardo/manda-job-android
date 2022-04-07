package br.com.nouva.onboarding.ui

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.nouva.core.module.AppModule
import br.com.nouva.core.setColouredSpan
import br.com.nouva.core.tos
import br.com.nouva.core.utils.KoinUtilities
import br.com.nouva.core.viewmodel.MainViewModel
import br.com.nouva.onboarding.R
import br.com.nouva.onboarding.User
import br.com.nouva.onboarding.databinding.FragmentOnbLoginStepOneBinding
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named

class OnbLoginStepOneFragment : Fragment() {

    private val loadModules by lazy { KoinUtilities.loadModules(AppModule.eachModules()) }
    private fun injectModules() = loadModules
    private val mainViewModel: MainViewModel by sharedViewModel()

     private val secondary by lazy {
        ContextCompat.getColor(requireContext(),
            R.color.colorLightSecondary090
        )
    }

    private lateinit var binding: FragmentOnbLoginStepOneBinding

    private var auth: FirebaseAuth? = null

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        injectModules()
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentOnbLoginStepOneBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        singUp()
        initObservers()
        initView()
        termos()

        return binding.root

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
    //crearToken example using: clearToken(FirebaseAuth.getInstance().currientUser!!.uid)

    private fun clearToken(userId: String){
          FirebaseDatabase
              .getInstance()
              .getReference("tokens")
              .child(userId)
              .removeValue()
    }


    private fun singUp(){
        binding.layoutBtnCreateAcc.setOnClickListener {
            if (binding.checkboxAgreeTerms.isChecked) {
                  verifyCampos()
            }else{
                Toast.makeText(context,
                    "aceite os termos para continuar!",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun verifyCampos(){
        val name = binding.edtNameSingUp.text.tos()
        val email = binding.edtEmailSingUp.text.tos()
        val password = binding.edtPasswordSingUp.text.tos()
        if (name.isBlank() || email.isBlank() || password.isBlank()){
            Toast.makeText(context,
                "Campos obrigatórios ainda estão em branco!",
                Toast.LENGTH_LONG).show()
        }else{

            recupereDados(name, email, password)
        }

    }

    private fun recupereDados(name: String ,email: String, password: String){
        auth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task->
                if (task.isSuccessful) {
                    auth!!.currentUser?.let { addUserToDatabase(name, email, it.uid) }

                    retrieveAndStoreToken()

                    Toast.makeText(context, "User Create Success",
                        Toast.LENGTH_LONG).show()

                    val bundle = bundleOf(
                        "EMAIL" to email
                    )

                   findNavController().navigate(


                       R.id.onbLoginStepTwoFragment, bundle)

                } else {
                    val erro = task.exception.tos()
                    errosFirebse(erro)
                    Log.d("erroFF",task.exception.tos())

                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String ){

        dbRef = FirebaseDatabase.getInstance().getReference()

        dbRef.child("user").child(uid).setValue(User(name, email, uid))

    }

    private fun errosFirebse(erro: String){
        if (erro.contains("The email address is badly formatted.")){
            Toast.makeText(context,
                "Formato de email inválido!",
                Toast.LENGTH_LONG).show()

        }

        if (erro.contains("The email address is already in use by another account.")){
            Toast.makeText(context,
                "O endereço de e-mail já está sendo usado por outra conta!",
                Toast.LENGTH_LONG).show()

        }


        }
    private fun termos(){

        binding.tvCheckBoxTerms.setOnClickListener {
            val view = View.inflate(context, R.layout.termos , null)
            val builder = AlertDialog.Builder(context)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            val btn1 = view.findViewById<TextView>(R.id.btn_termo_aceito)
            btn1.setOnClickListener {
                dialog.dismiss()
            }

        }

    }



    private fun initView() {



        binding.tvCheckBoxTerms.apply {
            setColouredSpan(
                "Concordo com todos Termos de serviços",
                19, 37, secondary
            )
        }



        binding.tvHaveAccount.setOnClickListener {
            findNavController().navigate(
                R.id.onbSignInFragment
            )

        }

        binding.tvHaveAccount.apply {
            setColouredSpan(
                "Já tem conta? Entrar",
                14, 20, secondary
            )
        }


    }

    private fun initObservers() {
    auth = Firebase.auth
    }

    public override fun onStart() {
        super.onStart()

    }

}