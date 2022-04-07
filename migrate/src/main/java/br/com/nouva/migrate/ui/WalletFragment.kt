package br.com.nouva.migrate.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.nouva.migrate.R
import br.com.nouva.migrate.databinding.FragmentWalletBinding

class WalletFragment : Fragment() {

    private lateinit var binding: FragmentWalletBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentWalletBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

}