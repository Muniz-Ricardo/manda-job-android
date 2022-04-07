package br.com.nouva.onboarding.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import br.com.nouva.core.setColouredSpan
import br.com.nouva.onboarding.R
import br.com.nouva.onboarding.databinding.FragmentOnbLoginStepThreeBinding

class OnbLoginStepThreeFragment : Fragment() {

     private val secondary by lazy {
        ContextCompat.getColor(requireContext(),
            R.color.colorLightSecondary090
        )
    }

    private lateinit var binding: FragmentOnbLoginStepThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentOnbLoginStepThreeBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        initView()

        return binding.root
    }

    private fun initView() {


        binding.tvLabelStepThree.apply {
            setColouredSpan(
                "Quais áreas você domina?",
                6, 12, secondary
            )
        }
    }
}