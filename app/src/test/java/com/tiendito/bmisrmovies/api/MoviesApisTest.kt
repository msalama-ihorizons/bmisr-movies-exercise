package com.tiendito.bmisrmovies.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tiendito.bmisrmovies.utils.Constants.API_KEY
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Mohamed Salama on 9/8/2020.
 * Email: msalama.tiendito@gmail.com
 */

class MoviesApisTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var moviesApis: MoviesApis

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createWebService(){
        mockWebServer = MockWebServer()
        moviesApis = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApis::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun testNowPlayingMovies() {
        enqueueResponse("movies_response.json")
        runBlocking {
            val result = moviesApis.getNowPlayingMovies(apiKey = API_KEY)

            Assert.assertThat(result.body()?.movies?.size, CoreMatchers.`is`(20))
            val movie1 = result.body()?.movies?.get(0)
            Assert.assertThat(movie1?.title, CoreMatchers.`is`("Rogue"))

        }

    }

    @Test
    fun testMovieCast() {
        enqueueResponse("cast_response.json")
        runBlocking {
            val result = moviesApis.getMovieCast(movieId = 550, apiKey = API_KEY)

            Assert.assertThat(result.body()?.cast?.size, CoreMatchers.`is`(79))
            val cast1 = result.body()?.cast?.get(0)
            Assert.assertThat(cast1?.character, CoreMatchers.`is`("The Narrator"))

        }

    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }

}