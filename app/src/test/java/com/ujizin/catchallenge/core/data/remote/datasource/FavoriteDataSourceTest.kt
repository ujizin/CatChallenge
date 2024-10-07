package com.ujizin.catchallenge.core.data.remote.datasource

import com.ujizin.catchallenge.core.data.remote.model.FavoritePayload
import com.ujizin.catchallenge.core.data.remote.model.FavoriteResponse
import com.ujizin.catchallenge.core.data.remote.service.FavoriteService
import com.ujizin.catchallenge.core.test.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.util.UUID
import kotlin.random.Random

class FavoriteDataSourceTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var mockFavoriteService: FavoriteService

    private lateinit var sutDataSource: FavoriteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sutDataSource = FavoriteDataSource(
            favoriteService = mockFavoriteService,
            dispatcher = mainCoroutineRule.dispatcher
        )
    }

    @Test
    fun `given favorite, when sent, then should call service`() = runTest {
        // Given
        val imageId = UUID.randomUUID().toString()
        val favoriteId = Random.nextLong()
        coEvery {
            mockFavoriteService.sendFavorite(FavoritePayload(imageId))
        } returns FavoriteResponse(favoriteId)

        // When
        val result = sutDataSource.sendFavorite(imageId, null).first()

        // Then
        assertEquals(favoriteId, result.id)
    }

    @Test
    fun `given favorite, when already sent, then should repass id`() = runTest {
        // Given
        val breedId = UUID.randomUUID().toString()
        val favoriteId = Random.nextLong()

        // When
        val result = sutDataSource.sendFavorite(breedId, favoriteId).first()

        // Then
        assertEquals(favoriteId, result.id)
    }

    @Test
    fun `given favorite, when deleted, then should return true`() = runTest {
        // Given
        val favoriteId = Random.nextLong()
        coEvery { mockFavoriteService.deleteFavorite(favoriteId) } just Runs

        // When
        val result = sutDataSource.deleteFavorite(favoriteId).first()

        // Then
        assertTrue(result)
    }

    @Test
    fun `given favorite, when already deleted from back-end, then should return true`() = runTest {
        // Given
        val favoriteId = Random.nextLong()

        val httpException = mockk<HttpException>()
        every { httpException.code() } returns HTTP_BAD_REQUEST

        coEvery { mockFavoriteService.deleteFavorite(favoriteId) } throws httpException

        // When
        val result = sutDataSource.deleteFavorite(favoriteId).first()

        // Then
        coVerify(exactly = 1) { mockFavoriteService.deleteFavorite(favoriteId) }
        assertTrue(result)
    }

    @Test
    fun `given favorite, when get, then should return list of favorites`() = runTest {
        // Given
        val expected = List(10) { index ->
            FavoriteResponse(index.toLong(), "breedId-$index")
        }
        coEvery { mockFavoriteService.getFavorites() } returns expected

        // When
        val result = sutDataSource.getFavorites().first()

        // Then
        assertEquals(expected, result)
    }
}