package br.com.nouva.mandajob.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.nouva.mandajob.R
import br.com.nouva.mandajob.model.ModelInbox
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.request.transition.Transition
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class AdapterInbox(var context: Context, private val inbox: List<ModelInbox?>?) : RecyclerView.Adapter<AdapterInbox.ViewHolder>() {

    private var factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.item_card_inbox, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val received = inbox!![position]
        holder.offer.text = getRandomOffer()
        holder.amount.text = getRandomAmount().toString()
        holder.time.text = getRandomTime()
        holder.name.text = received!!.name
        Glide.with(context)
                .asBitmap()
                .load(received.profile)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(BitmapTransitionOptions.withCrossFade(factory))
                .apply(RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888))
                .into(object : CustomTarget<Bitmap?>(100, 100) {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                        holder.progress.visibility = View.GONE
                        holder.profile.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })

    }

    private fun getRandomTime(): String {
        val min = 0
        val max = 4
        var result = ""
        when (Random().nextInt((max - min + 1) - 1)) {
            0 -> result = "now"
            1 -> result = "1d"
            2 -> result = "2d"
            3 -> result = "3d"
            4 -> result = "6d"
        }
        return result
    }

    private fun getRandomOffer(): String {
        val min = 0
        val max = 1
        var type = ""
        when (Random().nextInt((max - min + 1) - min)) {
            0 -> type = "Offer"
            1 -> type = "Chance"
        }
        return type
    }

    private fun getRandomAmount(): Int {
        val min = 100
        val max = 1000
        return Random().nextInt((max - min) + min)
    }

    override fun getItemCount(): Int {
        return inbox!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var container: CardView = itemView.findViewById(R.id.cv_item_offers_inbox) as CardView
        var amount: TextView = itemView.findViewById(R.id.tv_amount_offer_inbox) as TextView
        var name: TextView = itemView.findViewById(R.id.tv_name_user_inbox) as TextView
        var offer: TextView = itemView.findViewById(R.id.tv_type_offer_inbox) as TextView
        var time: TextView = itemView.findViewById(R.id.tv_time_inbox) as TextView
        var profile: CircleImageView = itemView.findViewById(R.id.iv_profile_user_inbox) as CircleImageView
        var progress: ProgressBar = itemView.findViewById(R.id.progress_bar_profile_inbox) as ProgressBar

    }
}