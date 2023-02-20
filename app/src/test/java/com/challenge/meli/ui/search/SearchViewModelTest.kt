package com.challenge.meli.ui.search

import com.challenge.meli.data.ProductRepository
import com.challenge.meli.data.model.Product
import com.challenge.meli.data.model.ProductResponse
import com.challenge.meli.data.network.NetworkState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class SearchViewModelTest{

    lateinit var productRepository: ProductRepository


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        productRepository = ProductRepository()
    }

    @Test
    fun `get all movie test`() {
        runBlocking {
            val response = productRepository.getAllProducts()
            assertEquals(listOf<ProductResponse>(),  NetworkState.Success(response).data)
        }
    }

}