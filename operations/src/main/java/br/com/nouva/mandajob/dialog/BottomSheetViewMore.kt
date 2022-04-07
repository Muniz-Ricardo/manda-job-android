package br.com.nouva.mandajob.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.nouva.mandajob.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetViewMore : BottomSheetDialogFragment() {

    private var bottomSheet: View? = null
    private var bottomSheetPeekHeight = 0

    override fun getTheme(): Int {
        return R.style.Theme_MaterialComponents_Light_BottomSheetDialog
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.modal_layout_send_offer,
                container, false)
        bottomSheet = view.findViewById(R.id.content_modal_send_offer)
        bottomSheetPeekHeight = resources
                .getDimensionPixelSize(R.dimen.bottom_sheet_default_peek_height_view_more)
        view.setBackgroundResource(R.color.colorDark)
        return view
    }

    override fun onResume() {
        super.onResume()
        setUpBottomSheet()
    }

    private fun setUpBottomSheet() {
        val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior
                .from(view!!.parent as View)
        bottomSheetBehavior.peekHeight = bottomSheetPeekHeight
        val childLayoutParams = bottomSheet!!.layoutParams
        bottomSheet!!.layoutParams = childLayoutParams
    }

    companion object {
        fun newInstance(): BottomSheetViewMore {
            return BottomSheetViewMore()
        }
    }
}