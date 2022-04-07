package br.com.nouva.migrate.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.nouva.migrate.databinding.FragmentAddPayBinding
import br.com.nouva.migrate.dialog.BottomSheetPix

class AddPayFragment : Fragment() {

    private lateinit var binding: FragmentAddPayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddPayBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        initView()

        return binding.root
    }

    private fun initView() {
        pixSheet()
    }

    private fun pixSheet() {
        val sheetFragment = BottomSheetPix()
        sheetFragment.show(requireActivity()
            .supportFragmentManager, sheetFragment.tag)
    }
}