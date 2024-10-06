package com.ujizin.catchallenge.core.data.repository

import androidx.paging.testing.asPagingSourceFactory
import androidx.paging.testing.asSnapshot
import com.ujizin.catchallenge.core.data.BreedDataModelUtils.createBreedEntityList
import com.ujizin.catchallenge.core.data.BreedDataModelUtils.createBreedList
import com.ujizin.catchallenge.core.data.BreedDataModelUtils.createBreedResponseList
import com.ujizin.catchallenge.core.data.BreedDataModelUtils.toEntity
import com.ujizin.catchallenge.core.data.local.dao.BreedDao
import com.ujizin.catchallenge.core.data.local.model.BreedEntity
import com.ujizin.catchallenge.core.data.remote.datasource.BreedDataSource
import com.ujizin.catchallenge.core.data.remote.datasource.FavoriteDataSource
import com.ujizin.catchallenge.core.data.remote.model.FavoriteResponse
import com.ujizin.catchallenge.core.data.repository.mapper.fromResponseToDomain
import com.ujizin.catchallenge.core.data.repository.mapper.fromResponseToEntity
import com.ujizin.catchallenge.core.data.repository.mapper.toDomain
import com.ujizin.catchallenge.core.data.repository.model.Breed
import com.ujizin.catchallenge.core.test.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class BreedRepositoryTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var mockBreedDao: BreedDao

    @MockK
    private lateinit var mockBreedDataSource: BreedDataSource

    @MockK
    private lateinit var mockFavoriteSource: FavoriteDataSource

    private lateinit var sutRepository: BreedRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sutRepository = BreedRepository(
            breedDataSource = mockBreedDataSource,
            favoriteDataSource = mockFavoriteSource,
            breedDao = mockBreedDao,
            dispatcher = mainCoroutineRule.dispatcher
        )
    }

    @Test
    fun `given paging, when getting breeds, then should return breed page`() = runTest {
        // Given
        val expected = createBreedResponseList()
        val pagingSourceFactory = expected.fromResponseToEntity().asPagingSourceFactory()
        val slot = slot<suspend BreedDao.() -> Unit>()
        coEvery { mockBreedDao.withTransaction(capture(slot)) } coAnswers {
            slot.captured(mockBreedDao)
        }
        coEvery { mockBreedDao.upsertAll(expected.fromResponseToEntity()) } just Runs
        every { mockBreedDao.getBreedsPagingSource() } returns pagingSourceFactory()

        coEvery { mockBreedDataSource.getBreeds(any(), any()) } returns flowOf(expected)

        // When
        val actual = mutableListOf<Breed>()
        sutRepository.pager.asSnapshot().toCollection(actual)

        // Then
        coVerify(exactly = 1) { mockBreedDao.withTransaction(slot.captured) }
        verify(exactly = 1) { mockBreedDataSource.getBreeds(any(), any()) }
        coVerify(exactly = 1) { mockBreedDao.upsertAll(expected.fromResponseToEntity()) }
        assertEquals(expected.fromResponseToDomain(), actual)
    }

    @Test
    fun `given favorites, when local favorites, then should return favorite list`() = runTest {
        // Given
        val expected = createBreedEntityList()
        every { mockBreedDao.getFavoritesBreed() } returns flowOf(expected)

        // When
        val response = sutRepository.getFavorites().first()

        // Given
        verify(exactly = 1) { mockBreedDao.getFavoritesBreed() }
        assertEquals(expected.map(BreedEntity::toDomain), response)
    }

    @Test
    fun `given favorites, when sent, then should update database`() = runTest {
        // Given
        val expectedBreed = createBreedList(1).first()
        val expectedFavorite = true

        val expectedResponse = FavoriteResponse(id = Random.nextLong())
        val entryBreedEntity = expectedBreed.toEntity()
        val expectedBreedEntity = entryBreedEntity.copy(favoriteId = expectedResponse.id)

        mockBreedFavorite(
            entryBreedEntity = entryBreedEntity,
            expectedBreed = expectedBreed,
            expectedResponse = expectedResponse,
            expectedBreedEntity = expectedBreedEntity,
        )

        // When
        val result = sutRepository.updateFavorite(expectedBreed.id, expectedFavorite).first()

        // Then
        coVerify(exactly = 0) { mockFavoriteSource.deleteFavorite(any()) }
        coVerify(exactly = 1) { mockFavoriteSource.sendFavorite(expectedBreed.id, null) }
        coVerify(exactly = 1) { mockBreedDao.updateBreed(expectedBreedEntity) }
        assertTrue(result)
    }

    @Test
    fun `given favorites, when deleted, then should update database`() = runTest {
        // Given
        val expectedBreed = createBreedList(1).first()
        val expectedFavorite = false

        val entryBreedEntity = expectedBreed.toEntity().copy(
            favoriteId = Random.nextLong(),
        )
        val expectedBreedEntity = entryBreedEntity.copy(favoriteId = null)

        mockBreedFavorite(
            entryBreedEntity = entryBreedEntity,
            expectedBreed = expectedBreed,
            expectedBreedEntity = expectedBreedEntity,
        )

        // When
        val result = sutRepository.updateFavorite(expectedBreed.id, expectedFavorite).first()

        // Then
        coVerify(exactly = 0) { mockFavoriteSource.sendFavorite(any(), any()) }
        coVerify(exactly = 1) { mockFavoriteSource.deleteFavorite(entryBreedEntity.favoriteId) }
        coVerify(exactly = 1) { mockBreedDao.updateBreed(expectedBreedEntity) }
        assertTrue(result)
    }

    private fun mockBreedFavorite(
        entryBreedEntity: BreedEntity,
        expectedBreed: Breed,
        expectedResponse: FavoriteResponse? = null,
        expectedBreedEntity: BreedEntity,
        expectedUpdateResult: Int = 1,
        deleteResult: Boolean = true,
    ) {
        coEvery { mockBreedDao.findBreed(expectedBreed.id) } returns entryBreedEntity

        if (expectedResponse != null) {
            every {
                mockFavoriteSource.sendFavorite(expectedBreed.id, entryBreedEntity.favoriteId)
            } returns flowOf(expectedResponse)
        }

        every {
            mockFavoriteSource.deleteFavorite(entryBreedEntity.favoriteId)
        } returns flowOf(deleteResult)

        coEvery { mockBreedDao.updateBreed(expectedBreedEntity) } returns expectedUpdateResult
    }
}
