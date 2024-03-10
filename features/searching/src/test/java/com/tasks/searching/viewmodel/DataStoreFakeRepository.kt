package com.tasks.searching.viewmodel

import com.tasks.domain.repo.DataStoreRepository

class DataStoreFakeRepository : DataStoreRepository {
    private var savedInput = ""
    override suspend fun putString(key: String, value: String) {
        savedInput = value
    }

    override suspend fun getString(key: String): String? {
        return savedInput
    }
}