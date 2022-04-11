package com.eneskayiklik.eventverse.core.component

import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Patterns
import android.widget.TextView
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.util.LinkifyCompat
import com.eneskayiklik.eventverse.R

@Composable
fun DefaultLinkifyText(modifier: Modifier = Modifier, textToSet: String) {
    val context = LocalContext.current
    val customLinkifyTextView = remember {
        TextView(context)
    }
    val primaryColor = MaterialTheme.colors.primary.toArgb()
    val textColor = MaterialTheme.colors.onSurface.toArgb()
    AndroidView(modifier = modifier, factory = { customLinkifyTextView }) { textView ->
        textView.apply {
            text = textToSet
            setLinkTextColor(primaryColor)
            setTextColor(textColor)
            typeface = ResourcesCompat.getFont(context, R.font.nunito_sans_bold)
        }
        LinkifyCompat.addLinks(textView, Linkify.ALL)
        Linkify.addLinks(
            textView, Patterns.WEB_URL, "http",
            Linkify.sUrlMatchFilter, null
        )
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}