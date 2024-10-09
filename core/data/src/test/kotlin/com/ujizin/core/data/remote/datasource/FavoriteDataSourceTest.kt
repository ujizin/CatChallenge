package com.ujizin.core.data.remote.datasource

import com.ujizin.catchallenge.core.data.remote.datasource.FavoriteDataSource
import com.ujizin.catchallenge.core.data.remote.model.FavoritePayload
import com.ujizin.catchallenge.core.data.remote.model.FavoriteResponse
import com.ujizin.catchallenge.core.data.remote.provider.DeviceIdProvider
import com.ujizin.catchallenge.core.data.remote.service.FavoriteService
import com.ujizin.catchallenge.core.test.rules.MainCoroutineRule
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

    @MockK
    private lateinit var mockDeviceIdProvider: DeviceIdProvider

    private lateinit var sutDataSource: FavoriteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sutDataSource = FavoriteDataSource(
            favoriteService = mockFavoriteService,
            deviceIdProvider = mockDeviceIdProvider,
            dispatcher = mainCoroutineRule.dispatcher
        )
    }

    @Test
    fun `given favorite, when sent, then should call service`() = runTest {
        // Given
        val deviceId = UUID.randomUUID().toString()
        val imageId = UUID.randomUUID().toString()
        val favoriteId = Random.nextLong()
        every { mockDeviceIdProvider() } returns deviceId
        coEvery {
            mockFavoriteService.sendFavorite(FavoritePayload(imageId, deviceId))
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
    fun `given favorite, when get, then should return filter list of favorites`() = runTest {
        // Given
        val deviceId = UUID.randomUUID().toString()
        val favoriteResponse = List(10) { index ->
            FavoriteResponse(
                index.toLong(),
                "breedId-$index",
                userId = deviceId.takeIf { index % 2 == 0 },
            )
        }
        val expectedList = favoriteResponse.filter { it.userId != null }
        every { mockDeviceIdProvider() } returns deviceId
        coEvery { mockFavoriteService.getFavorites() } returns favoriteResponse

        // When
        val result = sutDataSource.getFavorites().first()

        // Then
        assertEquals(expectedList, result)
    }
}