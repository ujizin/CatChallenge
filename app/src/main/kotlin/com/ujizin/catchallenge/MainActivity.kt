package com.ujizin.catchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ujizin.catchallenge.navigation.CatChallengeNavigation
import com.ujizin.catchallenge.core.ui.theme.CatChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatChallengeTheme {
                CatChallengeNavigation()
            }
        }
    }
}
