package com.tasks.domain.usecase

import com.tasks.domain.repo.DataStoreRepository
import kotlinx.coroutines.runBlocking

import org.junit.Assert.assertEquals
import org.junit.Test

class DataStoreUseCaseTest {
    private val mockRepo: DataStoreRepository = mock()
    private val useCase = DataStoreUseCase(mockRepo)

    @Test
    fun `save() calls putString() on the repository`(): Unit = runBlocking {
        // Given
        val key = "test_key"
        val value = "test_value"

        // When
        useCase.save(key, value)

        // Then
        assertEquals(useCase.read(key), value)
    }

    private fun mock() = object : DataStoreRepository {
        lateinit var savedValuePair: Pair<String, String>
        override suspend fun putString(key: String, value: String) {
            savedValuePair = Pair(key, value)
        }

        override suspend fun getString(key: String): String {
            return savedValuePair.second
        }

    }
}