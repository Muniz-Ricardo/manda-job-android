package br.com.nouva.chat.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import br.com.nouva.chat.R
import br.com.nouva.chat.databinding.ItemCardImageOverviewBinding
import br.com.nouva.core.data.QueryUris
import br.com.nouva.core.launchImage
import br.com.nouva.core.tos
import br.com.nouva.core.viewmodel.MainViewModel

class AdapterShotsUrl(
    private val context: Context,
    private val shots: Array<QueryUris?>?,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<AdapterShotsUrl.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding = ItemCardImageOverviewBinding.inflate(
            LayoutInflater.from(context), parent, false)
        return ViewHolder(itemBinding, context, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        shots?.get(position).let { holder.bind(it) }
    }

    override fun getItemCount(): Int = shots!!.size

    class ViewHolder(
        private val itemBinding: ItemCardImageOverviewBinding,
        private val context: Context,
        private val viewModel: MainViewModel
    ) : RecyclerView.ViewHolder(itemBinding.root) {
         @SuppressLint("NewApi")
         fun bind(shot: QueryUris?) {
             launchImage(shot?.uris.tos(), 400, context) {
                 itemBinding.ivImgShot.setImageBitmap(it)
                 if (shot?.uris.contentEquals(arrayListOf(shot)[0]?.uris)) {
                     viewModel.setLastImage(0)
                     changeImage(itemBinding, shot)
                 }
             }

             itemBinding.cvImgShot.setOnClickListener {
                 itemBinding.cvMasterShot[viewModel.last.value!!.toInt()].foreground = null
                 viewModel.setLastImage(adapterPosition)
                 viewModel.setUriPreview(shot?.uris?.get(adapterPosition).tos())
                 changeImage(itemBinding, shot)
             }
         }

        private fun changeImage(
            binding: ItemCardImageOverviewBinding,
            shot: QueryUris?
        ) {
            launchImage(shot?.uris.tos(), 400, context) {
                binding.ivImgShot.setImageBitmap(it)
                binding.cvMasterShot.foreground =
                    ContextCompat.getDrawable(
                        context, R.drawable.shape_label_asset_wallpaper
                    )
            }
        }
    }
}