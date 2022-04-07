package br.com.nouva.mandajob.adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.nouva.core.TranslateCard
import br.com.nouva.core.data.ResponseMoreQuery
import br.com.nouva.core.launchImage
import br.com.nouva.core.networkCall
import br.com.nouva.mandajob.databinding.ItemCardOrdesBinding
import java.util.*

class AdapterOrder(
    private val context: Context,
    private val mutableList: List<ResponseMoreQuery>,
) : RecyclerView.Adapter<AdapterOrder.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding = ItemCardOrdesBinding.inflate(
            LayoutInflater.from(context), parent, false)
        return ViewHolder(itemBinding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mutableList[position], position)
    }

    override fun getItemCount(): Int = mutableList.size

    class ViewHolder(
        private val itemBinding: ItemCardOrdesBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        var angle = 0
        fun bind(data: ResponseMoreQuery, position: Int) {

            val fadeIn: Animation = AlphaAnimation(0f, 1f)
            fadeIn.interpolator = DecelerateInterpolator()
            fadeIn.duration = 1000

            val animation = AnimationSet(false)
            animation.addAnimation(fadeIn)

            val start = data.name?.indexOf(' ')
            val firstName = if (start!! >= 0) data.name!!.substring(0, start) else ""

            itemBinding.tvNameUserOrder.text = firstName
            itemBinding.tvContentCardOrder.text = data.content
            itemBinding.tvTitleOrder.text = data.title
            itemBinding.tvAmountOrder.text = String.format("%dK", getRandomNumber())

            launchImage(data.profile, 200, context) {
                itemBinding.progressOrder.visibility = View.INVISIBLE
                itemBinding.ivProfileOrder.setImageBitmap(it)
                itemBinding.ivProfileOrder.animation = animation
                suspend { networkCall(itemBinding.progressOrder, context) }
            }

            itemBinding.cvShowTextOrder.setOnClickListener {
                translateTextCard(
                    position, itemBinding.tvContentCardOrder,
                    itemBinding.cvContentOrder, itemBinding.ivIconShowOrder
                )
            }

        }

        private fun getRandomNumber(): Int {
            val min = 100
            val max = 1000
            return Random().nextInt((max - min) + min)
        }

        private fun translateTextCard(
            position: Int,
            textView: TextView,
            cardView: CardView,
            icon: ImageView,
        ) {
            showContent(position, textView, cardView, icon)
        }

        private fun showContent(
            position: Int, content: TextView,
            card: CardView, arrow: ImageView
        ) {

            TransitionManager.beginDelayedTransition(card,
                AutoTransition().setDuration(800))

            val metadata =
                if (!SHOW) {
                    TranslateCard(false, 20, 800, position)
                } else if (position == TEXT_SHOW_ID) {
                    TranslateCard(true, 1, 600, position)
                } else {
                    TranslateCard()
                }

            val animation = ObjectAnimator.ofInt(
                content, "maxLines", metadata.max!!).setDuration(metadata.duration!!)

            if (!metadata.isShow!!) {
                animation.start()
                TEXT_SHOW_ID = position
                SHOW = true
            } else {
                animation.start()
                SHOW = false
            }
            animation(arrow)
        }

        private fun animation(arrow: ImageView) {
            val anim = ObjectAnimator.ofFloat(
                arrow, "rotation",
                angle.toFloat(), (angle + 180).toFloat())
            anim.duration = 500
            anim.start()
            angle += 180
            angle %= 360
        }
    }

    companion object {
        private var TEXT_SHOW_ID = 0
        private var SHOW = false
    }
}