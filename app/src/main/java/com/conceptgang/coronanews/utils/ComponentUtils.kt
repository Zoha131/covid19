package com.conceptgang.coronanews.utils

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.util.TypedValue
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import androidx.core.graphics.ColorUtils
import androidx.core.widget.doAfterTextChanged
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

inline val @receiver:ColorInt Int.shade200
    @ColorInt
    get() = ColorUtils.blendARGB(this, Color.WHITE, 0.43f)


inline val @receiver:ColorInt Int.shade900
    @ColorInt
    get() = ColorUtils.blendARGB(this, Color.BLACK, 0.43f)

inline val @receiver:ColorInt Int.shade100
    @ColorInt
    get() = ColorUtils.blendARGB(this, Color.WHITE, 0.90f)


//if you want to use shade for background then the ratio would be 95
inline val @receiver:ColorInt Int.transParentBG
    @ColorInt
    get() = Color.argb(30, Color.red(this), Color.green(this), Color.blue(this))


//val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val <T> T.exhaustive: T
    get() = this


fun Context.getColorFromStyleAttr(attr: Int): Int {
    val typedValue = TypedValue()

    theme.resolveAttribute(attr, typedValue, true)
    val colorRes = typedValue.resourceId

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        resources.getColor(colorRes, theme)
    } else {
        resources.getColor(colorRes)
    }
}

fun Context.getDimenFromStyleAttr(attr: Int): Int {
    val typedValue = TypedValue()

    theme.resolveAttribute(attr, typedValue, true)
    val dimenRes = typedValue.resourceId

    return resources.getDimensionPixelSize(dimenRes)

}

fun ImageView.changeImageColor(@ColorInt colorInt: Int) {
    val porterDuffColorFilter = PorterDuffColorFilter(colorInt, PorterDuff.Mode.SRC_ATOP)
    this.colorFilter = porterDuffColorFilter
}

@ExperimentalCoroutinesApi
fun EditText.asFlow(): Flow<CharSequence> = callbackFlow {
    val watcher = this@asFlow.doAfterTextChanged {
        offer(it.toString())
    }
    awaitClose { this@asFlow.removeTextChangedListener(watcher) }
}

fun Uri.getName(contentResolver: ContentResolver): String {


    // The query, because it only applies to a single document, returns only
    // one row. There's no need to filter, sort, or select fields,
    // because we want all fields for one document.
    val cursor: Cursor? = contentResolver.query(
        this, null, null, null, null, null
    )

    var displayName:String = "unknown"

    try {
        cursor?.use {
            // moveToFirst() returns false if the cursor has 0 rows. Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (it.moveToFirst()) {

                // Note it's called "Display Name". This is
                // provider-specific, and might not necessarily be the file name.
                displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
    } catch (ex: Exception){

    }

    return displayName

}

fun Uri.getSize(contentResolver: ContentResolver): String {


    // The query, because it only applies to a single document, returns only
    // one row. There's no need to filter, sort, or select fields,
    // because we want all fields for one document.
    val cursor: Cursor? = contentResolver.query(
        this, null, null, null, null, null
    )

    var displaySize:String = "0"

    try {
        cursor?.use {
            // moveToFirst() returns false if the cursor has 0 rows. Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (it.moveToFirst()) {

                val sizeIndex: Int = it.getColumnIndex(OpenableColumns.SIZE)
                // If the size is unknown, the value stored is null. But because an
                // int can't be null, the behavior is implementation-specific,
                // and unpredictable. So as
                // a rule, check if it's null before assigning to an int. This will
                // happen often: The storage API allows for remote files, whose
                // size might not be locally known.
                displaySize = if (!it.isNull(sizeIndex)) {
                    // Technically the column stores an int, but cursor.getString()
                    // will do the conversion automatically.
                    it.getString(sizeIndex)
                } else { "0" }
            }
        }
    } catch (ex: Exception){

    }

    return displaySize

}

fun Uri.getType(contentResolver: ContentResolver): String {
    val mime = MimeTypeMap.getSingleton()
    var type:String = "unknown"

    try {
        mime.getExtensionFromMimeType(contentResolver.getType(this))?.let { type = it }
    } catch (ex: Exception){

    }

    return type
}

fun Set<Uri>.getTotalSize(contentResolver: ContentResolver): Long = this.map { it.getSize(contentResolver).toLong() }.sum()

fun TextView.zhTextAppearance(@StyleRes textAppearance: Int){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextAppearance(textAppearance)
    } else{
        setTextAppearance(context, textAppearance)
    }
}
