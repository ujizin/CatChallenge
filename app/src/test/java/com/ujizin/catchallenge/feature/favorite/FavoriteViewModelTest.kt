package com.ujizin.catchallenge.feature.favorite

import com.ujizin.catchallenge.core.data.repository.BreedRepository
import com.ujizin.catchallenge.core.data.repository.model.Breed
import com.ujizin.catchallenge.core.test.MainCoroutineRule
import com.ujizin.catchallenge.feature.BreedFeatureModelUtils.createBreedList
import com.ujizin.catchallenge.feature.favorites.FavoriteViewModel
import com.ujizin.catchallenge.feature.favorites.ui.FavoriteUIEvent
import com.ujizin.catchallenge.feature.home.ui.mapper.toBreedUI
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoriteViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var mockBreedRepository: BreedRepository

    private lateinit var sutViewModel: FavoriteViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `given favorites, when fetched, then state should be updated to ready`() = runTest {
        // Given
        val breedList = createBreedList()
        every { mockBreedRepository.getFavorites() } returns flowOf(breedList)

        // When
        createSut()

        // Then
        val uiState = sutViewModel.uiState.first()

        assertFalse(uiState.isLoading)
        assertEquals(breedList.map(Breed::toBreedUI), uiState.favoriteList)
    }

    @Test
    fun `given favorites, when updated, then should call repository`() {
        // Given
        val breedList = createBreedList()
        every { mockBreedRepository.getFavorites() } returns flowOf(breedList)
        val breedUI = breedList.map(Breed::toBreedUI).random()

        coEvery {
            mockBreedRepository.updateFavorite(breedUI.id, !breedUI.isFavorite)
        } returns flowOf(true)

        createSut()

        // When
        sutViewModel.onEvent(FavoriteUIEvent.OnFavoriteChanged(breedUI, !breedUI.isFavorite))

        // Then
        coVerify {
            mockBreedRepository.updateFavorite(breedUI.id, !breedUI.isFavorite)
        }
    }

    @Test
    fun `given favorites, when breed clicked, then should do nothing`() {
        // Given
        val breedList = createBreedList()
        every { mockBreedRepository.getFavorites() } returns flowOf(breedList)
        val breedUI = breedList.random().toBreedUI()

        createSut()

        // When
        sutViewModel.onEvent(FavoriteUIEvent.OnBreedClick(breedUI))

        // Then
        coVerify(exactly = 0) { mockBreedRepository.updateFavorite(any(), any()) }
    }

    private fun createSut() {
        sutViewModel = FavoriteViewModel(mockBreedRepository)
    }
}