package com.tasks.domain.usecase

import com.tasks.domain.repo.DataStoreRepository

class DataStoreUseCase(private val repo: DataStoreRepository) {
    suspend fun save(key: String, value: String) = repo.putString(key, value)
    suspend fun read(key: String) = repo.getString(key)

}