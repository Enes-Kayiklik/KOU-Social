package com.eneskayiklik.eventverse.feature.image_detail

import android.graphics.drawable.Drawable
import android.util.Base64
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popExitTransition
import com.google.accompanist.navigation.animation.composable
import com.ortiz.touchview.TouchImageView
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
private fun ImageDetailScreen(
    imageUrl: String,
) {
    var drawable by remember { mutableStateOf<Drawable?>(null) }

    val painter = rememberAsyncImagePainter(imageUrl)

    // This going to get image as drawable when ready.
    // We need this because the only way to load image to Android Image View is this.
    drawable = (painter.state as? AsyncImagePainter.State.Success)?.result?.drawable

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        factory = { context ->
            FrameLayout(context).apply {
                addView(TouchImageView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                })
            }
        }, update = {
            if (it.childCount > 0)
                (it.getChildAt(0) as? TouchImageView)?.apply {
                    if (drawable != null)
                        setImageDrawable(drawable = drawable)
                }
        })

    // This is going to invoke painter to paint image. I didn't find better solution :(.
    // This composable won't paint on screen. It's just start loading image.
    Image(painter = painter, contentDescription = null, modifier = Modifier
        .fillMaxSize()
        .alpha(0F))

}

@ExperimentalAnimationApi
fun NavGraphBuilder.imageDetailComposable(
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.ImageDetail.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
        arguments = listOf(navArgument("imageUrl") { type = NavType.StringType })
    ) { backStack ->
        val imageUrl = backStack.arguments?.getString("imageUrl")
        if (imageUrl != null) {
            // We need to decode URL see: https://stackoverflow.com/questions/68950770/passing-url-as-a-parameter-to-jetpack-compose-navigation
            val decodedByte = URLDecoder.decode(imageUrl, StandardCharsets.UTF_8.toString())
            val decodedUrl = Base64.decode(decodedByte, Base64.DEFAULT)
            ImageDetailScreen(String(decodedUrl, StandardCharsets.UTF_8))
        } else clearBackStack()
    }
}