package br.com.nouva.onboarding.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.nouva.core.setColouredSpan
import br.com.nouva.onboarding.R
import br.com.nouva.onboarding.databinding.FragmentOnbWelcomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class OnbWelcomeFragment : Fragment() {

    private lateinit var binding: FragmentOnbWelcomeBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentOnbWelcomeBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        initView()
        auth = Firebase.auth

        return binding.root
    }

    private fun initView() {
        binding.tvCheckBoxTerms.setOnClickListener {
            binding.checkboxAgreeTerms.isChecked = !binding.checkboxAgreeTerms.isChecked
        }

        binding.cvSinUp.setOnClickListener {
            findNavController().navigate(
                R.id.action_onbWelcomeFragment_to_onbLoginStepOneFragment
            )
        }

        binding.cvPreLogin.setOnClickListener {
            findNavController().navigate(
                R.id.action_onbWelcomeFragment_to_onbSignInFragment)
        }

        binding.tvCheckBoxTerms.setColouredSpan(
            "I agree all Terms of service",
            12, 28, lazy {
                ContextCompat.getColor(requireContext(),
                    R.color.colorLightSecondary090
                )
            }.value
        )
    }
}