package com.ujizin.catchallenge.feature.home

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.testing.asPagingSourceFactory
import androidx.paging.testing.asSnapshot
import com.ujizin.catchallenge.core.data.repository.BreedRepository
import com.ujizin.catchallenge.core.data.repository.model.Breed
import com.ujizin.catchallenge.core.test.MainCoroutineRule
import com.ujizin.catchallenge.feature.BreedFeatureModelUtils.createBreedList
import com.ujizin.catchallenge.feature.home.ui.HomeUIEvent
import com.ujizin.catchallenge.feature.home.ui.mapper.toBreedUI
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var mockBreedRepository: BreedRepository

    private lateinit var sutViewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `given paging, when started, then should return list`() = runTest {
        // Given
        val breedList = createBreedList()
        every { mockBreedRepository.pager } returns breedList.fakePager()
        every { mockBreedRepository.syncFavorites() } returns flowOf(true)
        // When
        createSut()

        // Then
        val uiState = sutViewModel.uiState.first()
        val actualList = uiState.paging.asSnapshot()

        assertEquals(breedList.map(Breed::toBreedUI), actualList)
    }

    @Test
    fun `giving paging, when filtered, then should return filtered list`() = runTest {
        val breedList = createBreedList()
        val filterBreed = breedList.random().toBreedUI()
        every { mockBreedRepository.pager } returns breedList.fakePager()
        every { mockBreedRepository.syncFavorites() } returns flowOf(true)

        createSut()

        // When
        sutViewModel.onEvent(HomeUIEvent.OnSearch(filterBreed.name))

        // Then
        val uiState = sutViewModel.uiState.first()
        val actualList = uiState.paging.asSnapshot()

        assertEquals(listOf(filterBreed), actualList)
    }

    @Test
    fun `given paging, when update favorite, then should call repository`() {
        val breedList = createBreedList()
        val breedUI = breedList.random().toBreedUI()

        every { mockBreedRepository.pager } returns breedList.fakePager()
        every { mockBreedRepository.syncFavorites() } returns flowOf(true)
        every {
            mockBreedRepository.updateFavorite(
                breedUI.id,
                !breedUI.isFavorite
            )
        } returns flowOf(true)
        createSut()

        // When
        sutViewModel.onEvent(HomeUIEvent.OnBreedFavorite(breedUI, !breedUI.isFavorite))

        // Given
        coVerify(exactly = 1) {
            mockBreedRepository.updateFavorite(breedUI.id, !breedUI.isFavorite)
        }
    }

    @Test
    fun `given paging, when breed click, then should do nothing`() {
        val breedList = createBreedList()
        val breedUI = breedList.random().toBreedUI()

        every { mockBreedRepository.pager } returns breedList.fakePager()
        every { mockBreedRepository.syncFavorites() } returns flowOf(true)

        createSut()

        // When
        sutViewModel.onEvent(HomeUIEvent.OnBreedClick(breedUI))

        // Given
        coVerify(exactly = 0) { mockBreedRepository.updateFavorite(any(), any()) }
    }

    private fun createSut() {
        sutViewModel = HomeViewModel(mockBreedRepository)
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun List<Breed>.fakePager() =
        Pager(
            config = PagingConfig(pageSize = size),
            remoteMediator = object : RemoteMediator<Int, Breed>() {
                override suspend fun load(
                    loadType: LoadType,
                    state: PagingState<Int, Breed>
                ): MediatorResult {
                    return MediatorResult.Success(true)
                }
            },
            pagingSourceFactory = asPagingSourceFactory()
        ).flow
}