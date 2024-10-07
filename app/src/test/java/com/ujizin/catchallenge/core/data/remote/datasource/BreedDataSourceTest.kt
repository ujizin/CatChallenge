package com.ujizin.catchallenge.core.data.remote.datasource

import com.ujizin.catchallenge.core.data.BreedDataModelUtils.createBreedResponseList
import com.ujizin.catchallenge.core.data.remote.model.FavoriteResponse
import com.ujizin.catchallenge.core.data.remote.service.BreedService
import com.ujizin.catchallenge.core.test.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class BreedDataSourceTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var mockBreedService: BreedService

    @MockK
    private lateinit var mockFavoriteDataSource: FavoriteDataSource

    private lateinit var sutDataSource: BreedDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        sutDataSource = BreedDataSource(
            breedService = mockBreedService,
            favoriteDataSource = mockFavoriteDataSource,
            dispatcher = mainCoroutineRule.dispatcher
        )
    }

    @Test
    fun `given breeds and favorites, when fetched, then should return list mapped`() = runTest {
        // Given
        val breedList = createBreedResponseList(TEST_LIMIT_PAGE)
        val favorites = breedList.take(Random.nextInt(TEST_LIMIT_PAGE)).mapIndexed { index, breed ->
            FavoriteResponse(index.toLong(), breedId = breed.id)
        }
        val expected = breedList.map { breed ->
            breed.copy(favoriteId = favorites.find { it.breedId == breed.id }?.id)
        }

        coEvery { mockBreedService.getBreeds(TEST_LIMIT_PAGE, TEST_PAGE) } returns breedList
        coEvery { mockFavoriteDataSource.getFavorites() } returns flowOf(favorites)

        // When
        val actualBreedList = sutDataSource.getBreeds(TEST_LIMIT_PAGE, TEST_PAGE).first()

        // Then
        assertEquals(expected, actualBreedList)
    }

    companion object {
        private const val TEST_PAGE = 1
        private const val TEST_LIMIT_PAGE = 20
    }
}