package com.abi.movieapp.utils.other

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.abi.movieapp.internal.enums.Language
import kotlinx.coroutines.flow.map

class DataStoreUtil(private val context : Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "movie_app_data_store")

    suspend fun setMovieLanguage(language : Language) {
        context.dataStore.edit {
            it[CURRENT_LANGUAGE_KEY] = language.name
        }
    }

    val movieLanguage = context.dataStore.data.map {
        it[CURRENT_LANGUAGE_KEY] ?: Language.Malayalam.name
    }

    companion object {
        val CURRENT_LANGUAGE_KEY = stringPreferencesKey("language_key")
    }

}