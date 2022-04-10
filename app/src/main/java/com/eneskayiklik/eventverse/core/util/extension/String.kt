package com.eneskayiklik.eventverse.core.util.extension

import android.util.Patterns

fun String.isValidEmail(): Boolean =
    isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword(): Boolean =
    length >= 6

fun String.isValidFullName(): Boolean =
    length >= 3

fun String.isValidInstagramUsername(): Boolean = matches(Regex("^([A-Za-z0-9_](?:(?:[A-Za-z0-9_]|(?:\\.(?!\\.))){0,28}(?:[A-Za-z0-9_]))?)\$")) || isEmpty()
fun String.isValidTwitterUsername(): Boolean = matches(Regex("^@?(\\w){1,15}\$")) || isEmpty()
fun String.isValidGitHubUsername(): Boolean = matches(Regex("^[a-zA-Z0-9]*[-]?[a-zA-Z0-9]*\$")) || isEmpty()

fun String.toGithubLink() = "https://github.com/$this"
fun String.toInstagramLink() = "https://www.instagram.com/$this"
fun String.toLinkedInLink() = "https://www.linkedin.com/in/$this"
fun String.toTwitterLink() = "https://twitter.com/$this"