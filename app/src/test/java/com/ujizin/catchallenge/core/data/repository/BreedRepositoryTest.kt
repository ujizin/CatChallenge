package com.ujizin.catchallenge.core.data.repository

import androidx.paging.testing.asPagingSourceFactory
import androidx.paging.testing.asSnapshot
import com.ujizin.catchallenge.core.data.local.dao.BreedDao
import com.ujizin.catchallenge.core.data.remote.datasource.BreedDataSource
import com.ujizin.catchallenge.core.data.remote.datasource.FavoriteDataSource
import com.ujizin.catchallenge.core.data.remote.model.BreedResponse
import com.ujizin.catchallenge.core.data.repository.mapper.fromResponseToDomain
import com.ujizin.catchallenge.core.data.repository.mapper.fromResponseToEntity
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
        val expected = List(10) { index ->
            BreedResponse(
                id = "id-$index",
                name = "name-$index",
                description = "description-$index",
                origin = "origin-$index",
                temperament = "temperament-$index",
            )
        }
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
}
