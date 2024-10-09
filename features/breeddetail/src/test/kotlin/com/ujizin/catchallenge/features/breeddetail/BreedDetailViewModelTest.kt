package com.ujizin.catchallenge.features.breeddetail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.ujizin.catchallenge.core.data.repository.BreedRepository
import com.ujizin.catchallenge.core.navigation.destination.Destination.BreedDetail
import com.ujizin.catchallenge.core.navigation.destination.Destination.BreedDetail.Companion.typeMap
import com.ujizin.catchallenge.core.test.rules.MainCoroutineRule
import com.ujizin.catchallenge.core.test.utils.BreedFeatureModelUtils.createBreedList
import com.ujizin.catchallenge.core.ui.mapper.toBreedUI
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BreedDetailViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var mockBreedRepository: BreedRepository

    @RelaxedMockK
    private lateinit var mockSavedStateHandle: SavedStateHandle

    private lateinit var sutViewModel: BreedDetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        // FIXME https://issuetracker.google.com/issues/349807172?pli=1
        mockkStatic(SAVED_STATE_HANDLE_PACKAGE_NAME)
    }

    @After
    fun tearsDown() {
        unmockkStatic(SAVED_STATE_HANDLE_PACKAGE_NAME)
    }

    @Test
    fun `given detail breed, when starts, then should retrieve from route`() {
        // Given
        val breedUI = createBreedList().random().toBreedUI()
        every { mockSavedStateHandle.toRoute<BreedDetail>(typeMap) } returns BreedDetail(breedUI)

        // When
        createSut()

        // Then
        val value = sutViewModel.uiState.value
        assertEquals(breedUI, value.breed)
    }

    @Test
    fun `given favorite, when updated, then should call repository and update state`() = runTest {
        // Given
        val breedUI = createBreedList().random().toBreedUI()
        val expectedIsFavorite = !breedUI.isFavorite

        every { mockSavedStateHandle.toRoute<BreedDetail>(typeMap) } returns BreedDetail(breedUI)

        coEvery {
            mockBreedRepository.updateFavorite(breedUI.id, expectedIsFavorite)
        } returns flowOf(true)

        createSut()

        // When
        sutViewModel.onEvent(com.ujizin.catchallenge.features.breeddetail.ui.BreedDetailUIEvent.OnFavoriteChanged(expectedIsFavorite))

        // Then
        coVerify {
            mockBreedRepository.updateFavorite(breedUI.id, expectedIsFavorite)
        }

        val uiState = sutViewModel.uiState.first()

        assertEquals(expectedIsFavorite, uiState.breed.isFavorite)
    }

    @Test
    fun `given detail breed, when back pressed, then should do nothing`() {
        // Given
        val breedUI = createBreedList().random().toBreedUI()
        every { mockSavedStateHandle.toRoute<BreedDetail>(typeMap) } returns BreedDetail(breedUI)

        createSut()

        // When
        sutViewModel.onEvent(com.ujizin.catchallenge.features.breeddetail.ui.BreedDetailUIEvent.BackPressed)

        // Then
        coVerify(exactly = 0) { mockBreedRepository.updateFavorite(any(), any()) }
    }

    private fun createSut() {
        sutViewModel = BreedDetailViewModel(
            mockBreedRepository,
            mockSavedStateHandle
        )
    }

    companion object {
        private const val SAVED_STATE_HANDLE_PACKAGE_NAME = "androidx.navigation.SavedStateHandleKt"
    }
}