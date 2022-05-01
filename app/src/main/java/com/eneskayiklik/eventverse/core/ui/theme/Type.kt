package com.eneskayiklik.eventverse.core.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R

private val NunitoSansBold = FontFamily(Font(R.font.nunito_sans_bold))
private val NunitoSansLight = FontFamily(Font(R.font.nunito_sans_light))
private val NunitoSansSemiBold = FontFamily(Font(R.font.nunito_sans_semi_bold))

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = NunitoSansBold,
        letterSpacing = 0.sp,
        fontSize = 18.sp
    ),
    h2 = TextStyle(
        fontFamily = NunitoSansBold,
        letterSpacing = 0.15.sp,
        fontSize = 14.sp
    ),
    h3 = TextStyle(
        fontFamily = NunitoSansBold,
        letterSpacing = 0.15.sp,
        fontSize = 14.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = NunitoSansLight,
        letterSpacing = 0.sp,
        fontSize = 16.sp
    ),
    body1 = TextStyle(
        fontFamily = NunitoSansLight,
        letterSpacing = 0.sp,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontFamily = NunitoSansLight,
        letterSpacing = 0.sp,
        fontSize = 12.sp
    ),
    button = TextStyle(
        fontFamily = NunitoSansSemiBold,
        letterSpacing = 0.sp,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = NunitoSansSemiBold,
        letterSpacing = 0.sp,
        fontSize = 12.sp
    ),

)