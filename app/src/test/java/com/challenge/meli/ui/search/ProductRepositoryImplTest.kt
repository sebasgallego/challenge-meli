package com.challenge.meli.ui.search


import com.challenge.meli.data.ProductRepository
import com.challenge.meli.data.model.Product
import com.challenge.meli.data.network.ProductApiClient
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class ProductRepositoryImplTest {

    private lateinit var repository: ProductRepository
    private lateinit var testApis: ProductApiClient
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        repository = ProductRepository()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `for no products, api must return empty with http code 200`() = runTest {
        val products = emptyList<Product>()
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(products))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getProductForName("")
        assertEquals(
            ArrayList<Product>(),
            actualResponse.body!!.results
        )
    }

    @Test
    fun `empty products list test`() {
    /*    runBlocking {
            Mockito.`when`(apiService.getProductForName(""))
                .thenReturn(Response.success(ProductResponse(ArrayList())))
            val response = productRepository.getProductForName("")
            assertEquals(
                NetworkState.Success(listOf<ProductResponse>()),
                NetworkState.Success(response).data
            )
        }*/
    }
}