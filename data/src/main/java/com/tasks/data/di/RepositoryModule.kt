package com.tasks.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tasks.data.remote.ApiService
import com.tasks.data.repo.DataStoreRepositoryImpl
import com.tasks.data.repo.WeatherRepositoryImpl
import com.tasks.domain.repo.DataStoreRepository
import com.tasks.domain.repo.WeatherRepository
import com.tasks.domain.usecase.DataStoreUseCase
import com.tasks.domain.usecase.GetWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    private const val PREFERENCES_NAME = "my_preferences"

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    @Provides
    fun provideDataStoreRepository(@ApplicationContext app: Context): DataStoreRepository =
        DataStoreRepositoryImpl(app.dataStore)

    @Provides
    fun provideCurrentWeatherRepo(apiService: ApiService): WeatherRepository =
        WeatherRepositoryImpl(apiService)

    @Provides
    fun provideUseCase(repo: WeatherRepository): GetWeatherUseCase =
        GetWeatherUseCase(repo)

    @Provides
    fun provideDataStoreUseCase(repo: DataStoreRepository): DataStoreUseCase =
        DataStoreUseCase(repo)

}