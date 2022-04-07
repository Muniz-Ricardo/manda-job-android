package br.com.nouva.migrate.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.nouva.migrate.R
import br.com.nouva.migrate.databinding.LayoutModalPixBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetPix: BottomSheetDialogFragment() {

    private var bottomSheet: View? = null
    private var bottomSheetPeekHeight = 0

    private lateinit var binding: LayoutModalPixBinding

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogThemePix
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = LayoutModalPixBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        bottomSheetPeekHeight = resources
            .getDimensionPixelSize(R.dimen.bottom_sheet_default_peek_height_pix)
        bottomSheet = binding.laySheetPix

        view?.setBackgroundResource(android.R.color.transparent)

        return binding.root
    }

    private fun setUpBottomSheet() {
        val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior
            .from(view?.parent as View)
        bottomSheetBehavior.peekHeight = bottomSheetPeekHeight
        val childLayoutParams = bottomSheet!!.layoutParams
        bottomSheet!!.layoutParams = childLayoutParams
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