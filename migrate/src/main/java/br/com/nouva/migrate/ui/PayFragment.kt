package br.com.nouva.migrate.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.nouva.core.animSelectPlan
import br.com.nouva.core.animUnSelectPlan
import br.com.nouva.migrate.R
import br.com.nouva.migrate.databinding.FragmentPayBinding

class PayFragment : Fragment() {

    private lateinit var binding: FragmentPayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentPayBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        initView()

        return binding.root
    }

    private fun initView() {
        binding.cvButtonPlanGross.setOnClickListener {
            animUnSelectPlan(binding.cvCardPlanPro, binding.layoutPlanPro)
            animSelectPlan(binding.cvCardPlanGross, binding.layoutPlanGross)
        }

        binding.cvButtonPlanPro.setOnClickListener {
            animUnSelectPlan(binding.cvCardPlanGross, binding.layoutPlanGross)
            animSelectPlan(binding.cvCardPlanPro, binding.layoutPlanPro)
        }

        binding.cvBtnSubmitCard.setOnClickListener {
            findNavController()
                .navigate(R.id.action_payFragment_to_walletFragment)
        }
    }

}