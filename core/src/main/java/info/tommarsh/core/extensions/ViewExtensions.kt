package info.tommarsh.core.extensions

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.browser.customtabs.CustomTabsIntent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar


//region View
fun View.ArticleClickListener(url: String) = View.OnClickListener {
    val intent = CustomTabsIntent.Builder()
        .enableUrlBarHiding()
        .build()
    intent.launchUrl(context, Uri.parse(url))
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}
//endRegion

//region ViewGroup
fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)
//endregion

//region ImageView
fun AppCompatImageView.loadUrl(url: String) = Glide.with(context)
    .load(url)
    .apply(RequestOptions.centerCropTransform())
    .into(this)
//endregion

//region snackbar
fun View.snack(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}
//endregion