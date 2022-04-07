package br.com.nouva.core

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.transition.AutoTransition
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.*
import java.io.*
import java.util.*
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.math.sqrt

fun Any?.launchImage(
    uri: String?, size: Int,
    context: Context,
    callback: (bitmap: Bitmap) -> Unit,
) = GlobalScope.launch {
    withContext(Dispatchers.Main) {
        this@launchImage.tos()
        (context as Activity).runOnUiThread {
            Glide.with(context)
                .asBitmap()
                .load(uri)
                .placeholder(null)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions().format(
                    DecodeFormat.PREFER_ARGB_8888))
                .into(object : CustomTarget<Bitmap?>(size, size) {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?,
                    ) {
                        callback(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}

                })

        }
    }
}

fun Fragment?.matrixColorSaturation(image: ImageView) {
    if (this != null) {
        val matrix = ColorMatrix()
        matrix.setSaturation(0f)
        val filter = ColorMatrixColorFilter(matrix)
        image.colorFilter = filter
    }
}

fun TextView.setColouredSpan(
    word: String,
    start: Int, end: Int,
    color: Int,
) {
    this.text = word
    val spannableString = SpannableString(text)
    try {
        spannableString.setSpan(
            ForegroundColorSpan(color),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text = spannableString
    } catch (e: IndexOutOfBoundsException) {
        println("$word was not not found in TextView text")
    }
}

fun Fragment?.fadeIn(view: View, duration: Long = 1000) {
    if (this != null) {
        val fade: Animation = AlphaAnimation(0f, 1f)
        fade.interpolator = DecelerateInterpolator()
        fade.duration = duration

        val animation = AnimationSet(false)
        animation.addAnimation(fade)
        view.animation = animation
    }
}

fun Fragment.animSelectPlan(card: View, container: ViewGroup) {
    val animation = AnimationUtils
        .loadAnimation(requireContext(), R.anim.fade_in)
    container.background = ContextCompat
        .getDrawable(requireContext(), R.drawable.shape_card_plan_selected)
    card.backgroundTintList = ContextCompat
        .getColorStateList(requireContext(), R.color.colorSecondary)

    container.startAnimation(animation)
    card.startAnimation(animation)
}

fun Fragment.animUnSelectPlan(card: View, container: ViewGroup) {
    container.background = ContextCompat.getDrawable(
        requireContext(), R.drawable.shape_card_plan_unselected)
    card.backgroundTintList = ContextCompat.getColorStateList(
        requireContext(), R.color.colorDarkBlue)
}

suspend fun Any?.networkCall(
    progress: ProgressBar,
    context: Context,
) = GlobalScope.launch {

    this@networkCall.tos()
    withContext(Dispatchers.Default) {
        for (i in 1..50000) {
            Log.v("network call - ", "$i")
        }
        (context as Activity).runOnUiThread {
            progress.visibility = View.INVISIBLE
        }
    }
}

fun Fragment?.animFloat(
    viewGroup: ViewGroup,
    textView: TextView, isShow: Boolean,
) {
    if (this != null) {
        if (!isShow) {
            val transition = TransitionSet().addTransition(ChangeBounds()).setDuration(200)
            TransitionManager.beginDelayedTransition(
                viewGroup, transition
            )

            textView.visibility = View.GONE
            textView.animate().alpha(0F).duration = 50

            val params: ViewGroup.LayoutParams = viewGroup.layoutParams
            params.width = viewGroup.dpToPx(50)
            viewGroup.layoutParams = params
        } else {
            val transition = TransitionSet().addTransition(ChangeBounds()).setDuration(200)
            TransitionManager.beginDelayedTransition(
                viewGroup, transition
            )

            textView.visibility = View.VISIBLE
            textView.animate().alpha(1F).duration = 400

            val params: ViewGroup.LayoutParams = viewGroup.layoutParams
            params.width = viewGroup.dpToPx(170)
            viewGroup.layoutParams = params
        }
    }
}

fun ViewGroup.dpToPx(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}

fun Any?.tos(): String {
    if (this != null) {
        return this.toString()
    }
    return toString()
}

fun TextView.spanAndSplit(
    word: String,
    start: Int, end: Int,
    color: Int,
) {
    if (word.split("\\w+").toTypedArray().size > 1)
        word.substring(0, word.lastIndexOf(' '))

    this.text = word
    val spannableString = SpannableString(text)
    try {
        spannableString.setSpan(
            ForegroundColorSpan(color),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text = spannableString
    } catch (e: IndexOutOfBoundsException) {
        println("$word was not not found in TextView text")
    }
}

fun String?.separator(): String = String.format("%s —", this)

fun String?.lowerString(): String? {
    return this?.lowercase(Locale.getDefault())?.replace(" ", "")
}

fun Activity.createExtra(
    name: String, extra: String?, values: ArrayList<Any>? = null,
    activity: Class<*>,
): Intent {
    val intent = Intent(this, activity)
    return intent.putExtra(name, extra)
}

fun Activity.getIntentExtras(name: String): Any? {
    val intent = intent
    val extra: String? = intent.getStringExtra(name)
    return if (extra?.isNotEmpty() == true) extra else null
}

fun Any?.animRotation(icon: View, viewGroup: ViewGroup) {
    this.tos()
    var angle = 0
    val anim: ObjectAnimator = ObjectAnimator.ofFloat(
        icon,
        "rotation",
        angle.toFloat(),
        (angle + 180).toFloat()
    )

    anim.duration = 500
    anim.start()
    anim.end()

    angle += 180
    angle %= 360

    TransitionManager.beginDelayedTransition(viewGroup,
        AutoTransition().setDuration(500))

    viewGroup.visibility = when {
        viewGroup.isVisible -> View.GONE
        else -> View.VISIBLE
    }
}

fun TextView.setColorCustom(color: Int) =
    this.setTextColor(ContextCompat.getColor((context as Activity), color))

fun ImageView.setImageDraw(draw: Int) =
    this.setImageDrawable(ContextCompat.getDrawable((context as Activity), draw))

fun Any?.showContent(
    textView: TextView,
    image: ImageView,
    resource: ArrayList<Int>,
    callback: (success: Boolean) -> Unit,
) = GlobalScope.launch {
    this@showContent.tos()
    withContext(Dispatchers.Main) {
        textView.setColorCustom(resource[0])
        image.setImageDraw(resource[1])
        callback(true)
    }
}

val Any?.asActivity by lazy {
    fun(context: Context): Activity {
        return (context as Activity)
    }
}

fun Fragment.runDelay(callback: (done: Boolean) -> Unit) =
    GlobalScope.launch {
        withContext(Dispatchers.Main) {
            asActivity(requireContext()).runOnUiThread {
                callback(true)
            }
        }
    }

fun Any?.createAsFile(
    context: Context, uri: String?,
    callback: (done: Boolean) -> Unit,
) {
    try {
        this.tos()
        val stream: InputStream? =
            context.contentResolver.openInputStream(Uri.fromFile(File(uri.tos())))
        var bitmap: Bitmap? = BitmapFactory.decodeStream(stream)

        reduceBitmapSize(bitmap, 100000) {
            bitmap = it
        }

        val dir = File(
            "${
                context.externalMediaDirs[0].path
            }/files/"
        )

        dir.mkdirs()

        val outStream = FileOutputStream(File(dir, "image.png"))
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outStream)

        try {
            val buffer = ByteArray(2000)
            var bytesRead: Int

            while ((stream!!.read(buffer).also { bytesRead = it }) >= 0) {
                outStream.write(buffer, 0, bytesRead)
            }
        } finally {
            outStream.flush()
            try {
                outStream.fd.sync()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            outStream.close()
            callback(true)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun Fragment.fromUri(uri: Uri): String {
    val image = arrayOf(MediaStore.Audio.Media.DATA)
    val cursor: Cursor = requireContext().contentResolver
        .query(uri, image, null, null, null)!!
    val column: Int = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
    cursor.moveToFirst()
    val index = cursor.getString(column)
    cursor.close()
    return index
}

val Any?.reduceBitmapSize by lazy {
    fun(bitmap: Bitmap?, max: Int, callback: (Bitmap) -> Unit) {
        val ratioSquare: Double
        val bitmapHeight: Int = bitmap!!.height
        val bitmapWidth: Int = bitmap.width
        ratioSquare = (bitmapHeight * bitmapWidth / max).toDouble()
        if (ratioSquare <= 1) callback(bitmap)
        val ratio = sqrt(ratioSquare)

        val requiredHeight = (bitmapHeight / ratio).roundToInt()
        val requiredWidth = (bitmapWidth / ratio).roundToInt()
        callback(Bitmap.createScaledBitmap(bitmap, requiredWidth, requiredHeight, true))
    }
}

fun ImageView?.fadeIn(callback: (done: Boolean) -> Unit) {
    this?.alpha = 0f
    this?.setImageBitmap(null)
    this?.animate()?.alpha(1f)
        ?.setDuration(200)?.withEndAction {
            callback(true)
        }?.start()
}

fun Any?.revealAnimLoad(
    layout: View,
    view: View,
    context: Context
) {

    this.tos()
    val cx = (layout.x + layout.width) / 2
    val cy = (layout.y + layout.height) / 2

    val location = IntArray(2)
    view.getLocationOnScreen(location)

    view.setBackgroundColor(
        ContextCompat.getColor(
            context,
            R.color.colorDark070
        )
    )

    val radius = max(view.width, view.height) / 2

    val anim = ViewAnimationUtils.createCircularReveal(
        view,
        cx.toInt(),
        cy.toInt(),
        0f,
        radius.toFloat()
    )

    view.alpha = 0f
    view.animate().alpha(1f).duration = 600
    view.visibility = View.VISIBLE

    anim.duration = 400
    anim.start()

    anim.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {

        }

        override fun onAnimationEnd(animation: Animator?) {
            view.animate().alpha(0f).setDuration(300).withEndAction {
                //reveal.visibility = View.INVISIBLE
            }.start()
        }

        override fun onAnimationCancel(animation: Animator?) {

        }

        override fun onAnimationRepeat(animation: Animator?) {

        }
    })
}

fun Any?.isTooltip(isShow: Boolean, view: ViewGroup?) {
    this.tos()
    view?.animate()?.alpha(1f)?.withEndAction {
        view.visibility = if (isShow) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }?.start()
}