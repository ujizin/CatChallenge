package com.ujizin.catchallenge.core.navigation.navtype

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.ujizin.catchallenge.core.ui.model.BreedUI
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object BreedNavType : NavType<BreedUI>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): BreedUI? {
        return Json.decodeFromString<BreedUI>(bundle.getString(key) ?: return null)
    }

    override fun parseValue(value: String): BreedUI {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: BreedUI): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun put(bundle: Bundle, key: String, value: BreedUI) {
        bundle.putString(key, Json.encodeToString(value))
    }
}