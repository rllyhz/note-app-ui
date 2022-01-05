package id.rllyhz.meapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Singleton

private const val APP_PREFERENCES_NAME = "app_preferences"

@Singleton
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = APP_PREFERENCES_NAME
)

object PreferencesKeys {
    val NAME = stringPreferencesKey("name")
    val JOB_NAME = stringPreferencesKey("job_name")
    val SHOW_ON_BOARDING = booleanPreferencesKey("show_on_boarding")
}
