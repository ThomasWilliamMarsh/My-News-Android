package info.tommarsh.mynews.core.util

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil

//region context
fun Context.navigateToClass(className: String) {
    val intent = Intent(Intent.ACTION_VIEW).setClassName(packageName, className)
    startActivity(intent)
}


inline fun <reified T : AppCompatActivity> Context.newTaskIntent(): Intent {
    return Intent(this, T::class.java).apply {
        flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

    }
}

//endregion

//region listadapter
fun <T> getDiffUtilItemCallback(compare: (T, T) -> Boolean) =
    object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(old: T, new: T) = compare(old, new)

        override fun areContentsTheSame(old: T, new: T) = old?.equals(new) ?: false
    }
//endregion

//region menu
inline fun <reified T> Menu.getActionItem(item: Int): T = findItem(item).actionView as T
//endregion

//region Activity
inline fun <reified T> AppCompatActivity.service(type: String) = getSystemService(type) as T

fun AppCompatActivity.contentBehindStatusBar() = with(window) {
    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

    statusBarColor = ContextCompat.getColor(window.context, android.R.color.transparent)
}
//endregion

//region Fragment
fun Fragment.consume(block: () -> Unit): Boolean {
    block()
    return false
}
//endregion

inline fun <reified T : Any> createDiffItemCallback(crossinline contentsTheSame: (old: T, new: T) -> Boolean) =
    object : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem::class.java == newItem::class.java
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return contentsTheSame(oldItem, newItem)
        }
    }