package com.ujizin.core.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ujizin.catchallenge.core.data.local.CatChallengeDatabase
import org.junit.After
import org.junit.Before

abstract class CatChallengeDatabaseRule{

    lateinit var db: CatChallengeDatabase

    @Before
    open fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CatChallengeDatabase::class.java).build()
    }

    @After
    open fun tearDown() {
        db.close()
    }
}