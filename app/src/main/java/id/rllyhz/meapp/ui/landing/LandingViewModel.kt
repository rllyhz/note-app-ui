package id.rllyhz.meapp.ui.landing

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rllyhz.meapp.data.preferences.AppPreferences
import id.rllyhz.meapp.utils.PreferencesKeys
import id.rllyhz.meapp.utils.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

class LandingViewModel : ViewModel() {
    fun getUserPreferences(context: Context): Flow<AppPreferences> =
        context.dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else
                    throw exception
            }.map { prefs ->
                val name = prefs[PreferencesKeys.NAME] ?: "?"
                val jobName = prefs[PreferencesKeys.JOB_NAME] ?: "?"
                val shouldShowOnBoarding = prefs[PreferencesKeys.SHOW_ON_BOARDING] ?: true

                AppPreferences(name, jobName, shouldShowOnBoarding)
            }

    fun setUserPreferencesByStringKey(
        context: Context,
        key: Preferences.Key<String>,
        value: String
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            context.dataStore.edit { prefs ->
                prefs[key] = value
            }
        }

    fun setUserPreferencesByBooleanKey(
        context: Context,
        key: Preferences.Key<Boolean>,
        value: Boolean
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            context.dataStore.edit { prefs ->
                prefs[key] = value
            }
        }

    fun shouldShowOnBoardingPage(context: Context, should: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            context.dataStore.edit { prefs ->
                prefs[PreferencesKeys.SHOW_ON_BOARDING] = should
            }
        }
}