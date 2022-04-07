package br.com.nouva.mandajob.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.core.content.ContextCompat
import br.com.nouva.mandajob.R
import br.com.nouva.mandajob.databinding.ModalLayoutFilterBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFilter : BottomSheetDialogFragment() {

    private var bottomSheet: View? = null
    private var bottomSheetPeekHeight = 0

    private var last: View? = null
    private var label: TextView? = null

    private lateinit var binding: ModalLayoutFilterBinding

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogThemeFilter
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {

        binding = ModalLayoutFilterBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        initView()

        bottomSheetPeekHeight = resources
                .getDimensionPixelSize(R.dimen.bottom_sheet_default_peek_height_filter)
        bottomSheet = binding.laySheetFilter

        view?.setBackgroundResource(android.R.color.transparent)

        return binding.root
    }

    private fun initView() {
        selectTypeWork(1)

        binding.layoutButtonRemote.setOnClickListener {
            selectTypeWork(1)
        }

        binding.layoutButtonWorkplace.setOnClickListener {
            selectTypeWork(2)
        }

        binding.buttonSaveFilter.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun setUpBottomSheet() {
        val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior
            .from(view?.parent as View)
        bottomSheetBehavior.peekHeight = bottomSheetPeekHeight
        val childLayoutParams = bottomSheet!!.layoutParams
        bottomSheet!!.layoutParams = childLayoutParams
    }

    private fun selectTypeWork(pos: Int) {
        val fadeIn: Animation = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator()
        fadeIn.duration = 300

        val animation = AnimationSet(false)
        animation.addAnimation(fadeIn)

        when (pos) {
            1 -> {
                binding.layoutButtonRemote
                    .setBackgroundResource(R.drawable.shape_card_job_selected)
                binding.layoutButtonRemote.animation = animation

                binding.tvLabelRemote.setTextColor(ContextCompat.getColor(
                    requireContext(), R.color.colorWhite))

                if (last != null && label != null) {
                    last!!.setBackgroundResource(R.drawable.shape_card_office)
                    label!!.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.colorLightSecondary080))
                }

                last = binding.layoutButtonRemote
                label = binding.tvLabelRemote
            }
            2 -> {
                binding.layoutButtonWorkplace
                    .setBackgroundResource(R.drawable.shape_card_job_selected)
                binding.layoutButtonWorkplace.animation = animation
                binding.tvLabelWorkplace.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.colorWhite))

                if (last != null && label != null) {
                    last!!.setBackgroundResource(R.drawable.shape_card_office)
                    label!!.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.colorLightSecondary080))
                }

                last = binding.layoutButtonWorkplace
                label = binding.tvLabelWorkplace
            }

        }
    }

    override fun onResume() {
        super.onResume()
        setUpBottomSheet()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dialog.dismiss()
    }

}