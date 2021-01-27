package info.tommarsh.mynews.core.util

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat.CONSUMED
import androidx.core.view.WindowInsetsCompat.Type.navigationBars
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import androidx.recyclerview.widget.DiffUtil

//region context
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

//region Activity
inline fun <reified T> AppCompatActivity.service(type: String) = getSystemService(type) as T

fun AppCompatActivity.contentBehindStatusBar() = with(window) {
    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

    statusBarColor = ContextCompat.getColor(window.context, android.R.color.transparent)
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

fun View.doOnInsets(block: (systemBarInsets: Insets, navigationBarInsets: Insets) -> Unit) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        val systemBarInsets = insets.getInsets(systemBars())
        val navigationInsets = insets.getInsets(navigationBars())
        block(systemBarInsets, navigationInsets)
        CONSUMED
    }
}