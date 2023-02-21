package com.challenge.meli

import com.challenge.meli.data.ProductRepository
import com.challenge.meli.data.model.Product
import com.challenge.meli.data.model.ProductResponse
import com.challenge.meli.data.network.ProductApiClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class SearchFragmentTest {

    private lateinit var mainRepository: ProductRepository

    @Mock
    lateinit var apiService: ProductApiClient

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mainRepository = ProductRepository()
    }

    @Test
    fun `for no products, api must return empty with http code 200`() {
        runBlocking {
            Mockito.`when`(apiService.getProductForName("")).thenReturn(Response.success(ProductResponse(ArrayList<Product>())))
            val response = mainRepository.getProductForName("")
            assertEquals(ArrayList<Product>(), response.body!!.results)
        }
    }

    @Test
    fun `for products with title ps5, api must return data with http code 200`() {
        runBlocking {
            Mockito.`when`(apiService.getProductForName("ps5")).thenReturn(Response.success(ProductResponse(ArrayList<Product>())))
            val response = mainRepository.getProductForName("ps5")
            assertEquals(HttpURLConnection.HTTP_OK, response.httpCode)
        }
    }
}