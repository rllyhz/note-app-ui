package id.rllyhz.meapp.ui.activities.landing

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.rllyhz.meapp.data.preferences.AppPreferences
import id.rllyhz.meapp.utils.PreferencesKeys
import id.rllyhz.meapp.utils.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

class LandingViewModel : ViewModel() {
    private val defaultShowOnBoardingPageValue = true

    fun getAppPreferences(context: Context): LiveData<AppPreferences> =
        context.dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else
                    throw exception
            }.map { prefs ->
                val name = prefs[PreferencesKeys.NAME] ?: "?"
                val jobName = prefs[PreferencesKeys.JOB_NAME] ?: "?"
                val shouldShowOnBoarding =
                    prefs[PreferencesKeys.SHOW_ON_BOARDING] ?: defaultShowOnBoardingPageValue

                AppPreferences(name, jobName, shouldShowOnBoarding)
            }.asLiveData()

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

    fun setShouldShowOnBoardingPage(context: Context, should: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            context.dataStore.edit { prefs ->
                prefs[PreferencesKeys.SHOW_ON_BOARDING] = should
            }
        }

    fun shouldShownOnBoardingPage(context: Context): LiveData<Boolean> =
        context.dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else
                    throw exception
            }
            .map { prefs ->
                prefs[PreferencesKeys.SHOW_ON_BOARDING] ?: defaultShowOnBoardingPageValue
            }
            .asLiveData()
}