package br.com.nouva.mandajob.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.nouva.mandajob.R
import br.com.nouva.mandajob.model.ModelMoreDiscover
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdapterViewMoreDiscover(
        private val items: List<ModelMoreDiscover>,
        private val context: Context,
) : RecyclerView.Adapter<AdapterViewMoreDiscover.ViewHolder>() {

    private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card_view_more, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        val fadeIn: Animation = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator() //add this
        fadeIn.duration = 1000

        val animation = AnimationSet(false) //change to false
        animation.addAnimation(fadeIn)

        val fadeInWall: Animation = AlphaAnimation(0f, 1f)
        fadeInWall.interpolator = DecelerateInterpolator() //add this
        fadeInWall.duration = 1000

        val animationWall = AnimationSet(false) //change to false
        animationWall.addAnimation(fadeInWall)

        val key = item.key
        var category = item.type
        var name = item.name

        if (category == "Development") category = "Dev"

        if (name.split("\\w+".toRegex()).toTypedArray().size > 2)
            name = name.substring(0, item.name.indexOf(' '))

        holder.name.text = name
        holder.category.text = category

        holder.wallpaper.setImageResource(R.drawable.shape_img_profile_responses)
        holder.itemView.clearAnimation()
        holder.profile.setImageBitmap(null)

        Glide.with(context)
                .asBitmap()
                .load(item.profile)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(BitmapTransitionOptions.withCrossFade(factory))
                .apply(RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .override(100, 100))
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                        holder.profile.setImageBitmap(resource)
                        holder.profile.animation = animation
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })

        Glide.with(context)
                .asBitmap()
                .load(item.wallpaper)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(BitmapTransitionOptions.withCrossFade(factory))
                .apply(RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .override(400, 400))
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                        GlobalScope.launch { networkCall(holder.progressWallpaper) }
                        holder.wallpaper.setImageBitmap(resource)
                        holder.wallpaper.animation = animationWall
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })

        holder.container.setOnClickListener { generateIntent(key) }

    }

    private fun generateIntent(key: String) {
       /* val intent = Intent(context, ProjectDiscoverDetailsActivity::class.java)
        intent.putExtra("from", key)
        context.startActivity(intent)*/
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView = itemView.findViewById(R.id.tv_name_item_more) as TextView
        var category: TextView = itemView.findViewById(R.id.tv_category_view_more) as TextView
        var wallpaper: ImageView = itemView.findViewById(R.id.iv_item_more_wallpaper) as ImageView
        var profile: ImageView = itemView.findViewById(R.id.iv_profile_image_more) as ImageView
        var progressWallpaper: ProgressBar = itemView.findViewById(R.id.progress_bar_wallpaper_more) as ProgressBar
        var container: CardView = itemView.findViewById(R.id.cv_project_more) as CardView

    }

    private suspend fun networkCall(progressBar: ProgressBar) {
        withContext(Dispatchers.Default) {
            for (i in 1..50000) {
                Log.v("Network call", "$i")
            }
            (context as Activity).runOnUiThread {
                progressBar.visibility = View.INVISIBLE
            }
        }
    }
}