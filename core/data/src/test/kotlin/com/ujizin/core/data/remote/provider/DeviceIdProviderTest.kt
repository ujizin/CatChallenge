package com.ujizin.core.data.remote.provider

import android.content.Context
import android.provider.Settings
import com.ujizin.catchallenge.core.data.remote.provider.DeviceIdProvider
import com.ujizin.catchallenge.core.test.rules.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.UUID

class DeviceIdProviderTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var mockContext: Context

    private lateinit var sutProvider: DeviceIdProvider

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockkStatic(Settings.Secure::class)

        sutProvider = DeviceIdProvider(mockContext)
    }

    @Test
    fun `given deviceId, when get, then should be retrieved`() {
        // Given
        every { mockContext.contentResolver } returns mockk()
        val expected = UUID.randomUUID().toString()
        every {
            Settings.Secure.getString(
                mockContext.contentResolver,
                Settings.Secure.ANDROID_ID,
            )
        } returns expected

        // When
        val result = sutProvider()

        // Then
        assertEquals(expected, result)
    }
}