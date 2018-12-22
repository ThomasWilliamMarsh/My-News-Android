package info.tommarsh.core.extensions

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import info.tommarsh.core.R


//region View
fun View.ArticleClickListener(url: String) = View.OnClickListener {
    val intent = CustomTabsIntent.Builder()
        .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
        .enableUrlBarHiding()
        .build()
    intent.launchUrl(context, Uri.parse(url))
}
//endRegion

//region ViewGroup
fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)
//endregion

//region ImageView
fun ImageView.loadUrl(url: String) = Picasso.get()
    .load(url)
    .fit()
    .into(this)
//endregion

//region snackbar
fun View.snack(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}
//endregion