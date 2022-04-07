package br.com.nouva.mandajob.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.com.nouva.core.*
import br.com.nouva.mandajob.R
import br.com.nouva.mandajob.activity.InboxActivity
import br.com.nouva.mandajob.databinding.FragmentProfileBinding
import br.com.nouva.mandajob.dialog.BottomSheetProfile
import br.com.nouva.migrate.MainActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentProfileBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        initView()

        return binding.root
    }

    private fun initView() {
        var count = 0
        val arrayOf = arrayListOf(
            Pair(getString(R.string.url_image_man_blue_black_power), binding.ivProfileUser),
            Pair(getString(R.string.url_image_man_pink), binding.ivPrevInboxOne),
            Pair(getString(R.string.url_image_man_blue), binding.ivPrevInboxSec)
        )

        arrayOf.forEach {
            launchImage(it.first, if (count == 0) 250 else 100, requireContext()) { uri ->
                it.second.setImageBitmap(uri)
                fadeIn(it.second)
            }
            if (count == 0) matrixColorSaturation(it.second as ImageView)
            count++
        }

        binding.tvLabelProfile.setColouredSpan(
            "Olá, Carlos. clique e edite.",
            5, 11, lazy {
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorSecondary
                )
            }.value
        )

        binding.layMigrateButton.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            requireActivity().startActivity(intent)
        }

        binding.layEditProfile.setOnClickListener {
            openConfigEdit()
        }

        binding.layButtonInbox.setOnClickListener {
            val intent = Intent(requireContext(), InboxActivity::class.java)
            requireActivity().startActivity(intent)
        }

        isTooltip(true, binding.layTooltip)

    }

    private fun openConfigEdit() {
        val sheetFragment = BottomSheetProfile()
        sheetFragment.show(requireActivity()
            .supportFragmentManager, sheetFragment.tag)
    }

}