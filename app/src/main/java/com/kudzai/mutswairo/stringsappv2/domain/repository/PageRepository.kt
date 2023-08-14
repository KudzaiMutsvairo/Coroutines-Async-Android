package com.kudzai.mutswairo.stringsappv2.domain.repository

import com.kudzai.mutswairo.stringsappv2.util.Resource

/**
 * Repository interface for getting web page content from a url
 * This interface defines the contract for retrieving web page content from a specified URL.
 * Implementations of this interface are responsible for fetching the web page content and returning it as a string.
 */

interface PageRepository {
    /**
     * This method is responsible for fetching the web page content and returning it as a string.
     * @param url The url of the web page to fetch data from.
     * @return The web page content as a string.
     */
    suspend fun getWebPageFromUrl(): Resource<String>
}
