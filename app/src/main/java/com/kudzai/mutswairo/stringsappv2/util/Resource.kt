package com.kudzai.mutswairo.stringsappv2.util

/**
 * Wrapper class for representing different states of a Resource.
 */
sealed class Resource<out T> {
    /**
     * Represents a successful state with the associated data.
     *
     * @param data The data associated with the resource.
     */
    data class Success<out T>(val data: T) : Resource<T>()

    /**
     * Represents an error state with the associated error message.
     *
     * @param message The error message associated with the resource.
     */
    data class Error(val message: String) : Resource<Nothing>()

    /**
     * Represents a loading state.
     */
    object Loading : Resource<Nothing>()
}