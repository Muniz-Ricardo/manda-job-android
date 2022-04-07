package br.com.nouva.mandajob.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.recyclerview.widget.RecyclerView
import br.com.nouva.core.data.DiscoverMore
import br.com.nouva.core.launchImage
import br.com.nouva.core.networkCall
import br.com.nouva.mandajob.R
import br.com.nouva.mandajob.databinding.ItemCardRecomendedBinding
import br.com.nouva.mandajob.listener.OnClickListenerItem

class AdapterEveryForYou(
    private val context: Context,
    private val mutableList: List<DiscoverMore?>,
) : RecyclerView.Adapter<AdapterEveryForYou.ViewHolder>() {

    private lateinit var listenerItem: OnClickListenerItem

    fun onClickItem(events: OnClickListenerItem) {
        listenerItem = events
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemCardRecomendedBinding.inflate(
            LayoutInflater.from(context), parent, false)
        return ViewHolder(itemBinding, listenerItem, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mutableList[position]!!)
    }

    override fun getItemCount(): Int = mutableList.size

    override fun getItemViewType(position: Int): Int = mutableList.size

    class ViewHolder(
        private val itemBinding: ItemCardRecomendedBinding,
        private val listenerItem: OnClickListenerItem,
        private val context: Context,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: DiscoverMore) {

            val fadeIn: Animation = AlphaAnimation(0f, 1f)
            fadeIn.interpolator = DecelerateInterpolator()
            fadeIn.duration = 1000

            val animation = AnimationSet(false)
            animation.addAnimation(fadeIn)

            val fadeInWall: Animation = AlphaAnimation(0f, 1f)
            fadeInWall.interpolator = DecelerateInterpolator()
            fadeInWall.duration = 1000

            val animationWall = AnimationSet(false)
            animationWall.addAnimation(fadeInWall)

            itemBinding.root.clearAnimation()

            var category = data.type
            var name = data.name

            if (category == "Development") category = "Dev"

            name = if (name?.split("\\w+".toRegex())?.toTypedArray()?.size!! > 2)
                name.substring(0, name.lastIndexOf(' ')) else data.name

            itemBinding.tvNameItemHome.text = name
            itemBinding.tvCategory.text = category

            itemBinding.ivItemHome.setImageBitmap(null)
            itemBinding.cvWallpaperHome.setCardBackgroundColor(
                getColorStateList(context, R.color.colorDark080)
            )

            itemBinding.cvProjectHome.setOnClickListener {
                listenerItem.tokenUser(data.key)
            }

            launchImage(data.profile, 100, context) {
                itemBinding.ivProfileImageHome.setImageBitmap(it)
                itemBinding.ivProfileImageHome.animation = animation
            }

            launchImage(data.wallpaper, 512, context) {
                suspend { networkCall(itemBinding.progressImageHome, context) }
                itemBinding.ivItemHome.setImageBitmap(it)
                itemBinding.ivItemHome.animation = animationWall
            }
        }
    }
}