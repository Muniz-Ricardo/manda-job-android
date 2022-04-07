package br.com.nouva.migrate.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.nouva.core.setColouredSpan
import br.com.nouva.migrate.R
import br.com.nouva.migrate.databinding.FragmentMigrateBinding

class MigrateFragment : Fragment() {

    private lateinit var binding: FragmentMigrateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentMigrateBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        initView()
        initObservers()

        return binding.root
    }

    private fun initView() {
        binding.tvMigrateDescription.setColouredSpan(
            "Ande mais rápido e fique no topo de ofertas e perfis.",
            28, 33, lazy {
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorSecondary
                )
            }.value
        )

        binding.layoutButtonSign.setOnClickListener {
            findNavController()
                .navigate(R.id.action_migrateFragment_to_payFragment)
        }
    }

    private fun initObservers() {

    }
}