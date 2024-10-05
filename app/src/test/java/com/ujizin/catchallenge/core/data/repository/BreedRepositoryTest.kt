package com.ujizin.catchallenge.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import androidx.paging.RemoteMediator.InitializeAction
import androidx.paging.testing.asPagingSourceFactory
import androidx.paging.testing.asSnapshot
import com.ujizin.catchallenge.core.data.local.dao.BreedDao
import com.ujizin.catchallenge.core.data.local.model.BreedEntity
import com.ujizin.catchallenge.core.data.repository.mapper.toDomain
import com.ujizin.catchallenge.core.data.repository.mediator.BreedRemoteMediator
import com.ujizin.catchallenge.core.data.repository.model.Breed
import com.ujizin.catchallenge.core.test.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalPagingApi::class)
class BreedRepositoryTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var mockBreedDao: BreedDao

    @MockK
    private lateinit var mockRemoteMediator: BreedRemoteMediator

    private lateinit var sutRepository: BreedRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coEvery {
            mockRemoteMediator.initialize()
        } returns InitializeAction.SKIP_INITIAL_REFRESH

        sutRepository = BreedRepository(
            remoteMediator = mockRemoteMediator,
            breedDao = mockBreedDao,
            dispatcher = mainCoroutineRule.dispatcher
        )
    }

    @Test
    fun `given paging, when getting breeds, then should return breed page`() = runTest {
        // Given
        val expected = List(10) { index ->
            BreedEntity(
                id = "id-$index",
                name = "name-$index",
                description = "description-$index",
                origin = "origin-$index",
                temperament = "temperament-$index",
                imageUrl = "imageUrl-$index",
            )
        }
        val pagingSourceFactory = expected.asPagingSourceFactory()
        every { mockBreedDao.getBreedsPagingSource() } returns pagingSourceFactory()

        val mediatorResult = RemoteMediator.MediatorResult.Success(endOfPaginationReached = true)
        coEvery { mockRemoteMediator.load(any(), any()) } returns mediatorResult

        // When
        val actual = mutableListOf<Breed>()
        sutRepository.getPaging().asSnapshot().toCollection(actual)

        // Then
        assertEquals(expected.toDomain(), actual)
    }
}
