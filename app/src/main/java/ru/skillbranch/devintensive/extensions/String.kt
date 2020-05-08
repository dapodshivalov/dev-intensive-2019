package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16): String {
    return this.slice(IntRange(0, length - 1)).trim().plus("...")
}

fun String.stripHtml(): String {
    return this
        .replace(Regex("<[^>]*>"),"")
        .replace(Regex("[\"&<>']"), "")
        .replace(Regex("\\s+")," ")
        .trim()
}