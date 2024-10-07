package com.ujizin.catchallenge.core.data.local.dao

import com.ujizin.catchallenge.core.data.BreedDataModelUtils.createBreedEntityList
import com.ujizin.catchallenge.core.data.local.CatChallengeDatabaseRule
import com.ujizin.catchallenge.core.data.local.model.BreedEntity
import com.ujizin.catchallenge.core.test.MainCoroutineRule
import io.mockk.MockKAnnotations
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.random.Random

@RunWith(RobolectricTestRunner::class)
class BreedDaoTest: CatChallengeDatabaseRule() {

    @get:Rule(order = 0)
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var sutDao: BreedDao

    override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        sutDao = db.breedDao
    }

    @Test
    fun `given favorite, when updated then should reflective on find breed`() = runTest {
        // Given
        val breed = createBreedEntityList().random()
        sutDao.upsertAll(listOf(breed))
        val favoriteId = Random.nextLong()

        // When
        sutDao.updateFavorite(id = breed.id, favoriteId = favoriteId)

        // Then
        val updatedBreed = sutDao.findBreed(id = breed.id)
        assertEquals(favoriteId, updatedBreed?.favoriteId)
    }

    @Test
    fun `given favorite, when upsert all, then should be updated`() = runTest {
        // Given
        val breeds = createBreedEntityList()

        // When
        sutDao.upsertAll(breeds)

        // Then
        val favorites = sutDao.getFavoritesBreed().first()
        assertEquals(breeds, favorites)
    }

    @Test
    fun `given favorite, when delete all, then should be updated to empty`() = runTest {
        // Given
        val breeds = createBreedEntityList()

        // When
        sutDao.upsertAll(breeds)

        // Then
        val firstFavorites = sutDao.getFavoritesBreed().first()
        assertEquals(breeds, firstFavorites)

        // When
        sutDao.removeAllFavorite()
        val favorites = sutDao.getFavoritesBreed().first()

        // Then
        assertEquals(emptyList<BreedEntity>(), favorites)
    }

    @Test
    fun `given breed, when updated, then should reflect on get`() = runTest{
        // Given
        val breed = createBreedEntityList().random()
        sutDao.upsertAll(listOf(breed))

        // When
        val updatedBreed = breed.copy(favoriteId = Random.nextLong())
        sutDao.updateBreed(updatedBreed)

        // Then
        val actualBreed = sutDao.findBreed(updatedBreed.id)
        assertEquals(updatedBreed, actualBreed)
    }
}