package br.com.nouva.mandajob.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.nouva.mandajob.R
import br.com.nouva.mandajob.databinding.LayoutModalProfileBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetProfile : BottomSheetDialogFragment() {

    private var bottomSheet: View? = null
    private var bottomSheetPeekHeight = 0

    private lateinit var binding: LayoutModalProfileBinding

    override fun getTheme(): Int {
        return R.style.Theme_MaterialComponents_Light_BottomSheetDialog
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {

        binding = LayoutModalProfileBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        bottomSheet = binding.contentModal
        bottomSheetPeekHeight = resources
                .getDimensionPixelSize(R.dimen.bottom_sheet_default_peek_height)

        view?.setBackgroundResource(R.color.colorDark)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setUpBottomSheet()
    }

    private fun setUpBottomSheet() {
        val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior
            .from(view?.parent as View)
        bottomSheetBehavior.peekHeight = bottomSheetPeekHeight
        val childLayoutParams = bottomSheet!!.layoutParams
        bottomSheet!!.layoutParams = childLayoutParams
    }
}