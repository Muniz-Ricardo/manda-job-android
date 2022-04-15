package br.com.nouva.onboarding.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import br.com.nouva.core.setColouredSpan
import br.com.nouva.core.tos
import br.com.nouva.onboarding.R
import br.com.nouva.onboarding.databinding.FragmentOnbLoginStepTwoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class OnbLoginStepTwoFragment : Fragment() {

    private val secondary by lazy {
        ContextCompat.getColor(requireContext(),
            R.color.colorLightSecondary090
        )
    }

    private lateinit var binding: FragmentOnbLoginStepTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentOnbLoginStepTwoBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        initView()

        return binding.root
    }

    private fun clearToken(userId: String){
        FirebaseDatabase
            .getInstance()
            .getReference("tokens")
            .child(userId)
            .removeValue()
    }

    private fun initView() {
        //binding.layoutArrowNextTwo.setOnClickListener {
          //  findNavController().navigate(
            //    R.id.action_onbLoginStepTwoFragment_to_onbLoginStepThreeFragment)
        //}

        val email = requireArguments().getString("EMAIL")

        binding.tvEmailRecebido.text = email


        binding.tvReenviarCodigo.setOnClickListener {



              if ( FirebaseAuth.getInstance().currentUser?.let { it1 -> clearToken(it1.uid) } != null){

                  FirebaseAuth.getInstance().signOut()

              }
        }


        binding.tvReenviarCodigo.apply {
            setColouredSpan(
                "Reenviar código",
            1, 15, secondary
            )

        }

        binding.tvAlterar.apply {
            setColouredSpan(
                "(alterar)",
                1, 10, secondary
            )
        }
    }
}