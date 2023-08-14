package com.kudzai.mutswairo.stringsappv2.data.repository

import com.kudzai.mutswairo.stringsappv2.domain.repository.PageRepository
import com.kudzai.mutswairo.stringsappv2.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository implementation for getting the web page content as a String from the url
 */
@Singleton
class PageRepositoryImpl @Inject constructor(
    private val baseUrl: URL
) : PageRepository {
    override suspend fun getWebPageFromUrl(): Resource<String> {
        return withContext(Dispatchers.IO) {
            val connection = baseUrl.openConnection() as HttpURLConnection
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            try {
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val content = BufferedReader(InputStreamReader(inputStream))
                    val response = StringBuilder()
                    var inputLine: String?
                    while (content.readLine().also { inputLine = it } != null) {
                        response.append(inputLine)
                    }
                    return@withContext Resource.Success(response.toString())
                } else {
                    return@withContext Resource.Error("Error: $responseCode")
                }
            } catch (e: Exception) {
                return@withContext Resource.Error(e.message ?: "An unknown error occurred")
            } finally {
                connection.disconnect()
            }
        }
    }
}
