package com.sonusourav.pullr.presentation.utils

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peek(): T = content
}