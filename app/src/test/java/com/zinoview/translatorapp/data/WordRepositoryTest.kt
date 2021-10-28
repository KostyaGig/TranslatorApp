package com.zinoview.translatorapp.data

import com.zinoview.translatorapp.data.cache.CacheDataSource
import com.zinoview.translatorapp.data.cloud.CloudDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Test for [WordRepository.Test]
 */

class WordRepositoryTest {

    private lateinit var repository: WordRepository.TestRepository<List<Pair<String,String>>>

    @Before
    fun setUp() {
        val cloudDataSource = CloudDataSource.Test()
        val cacheDataSource = CacheDataSource.Test()
        repository = WordRepository.TestRepository.Test(cloudDataSource,cacheDataSource)
    }

    @Test
    fun test_fetching_translated_word() = runBlocking<Unit> {
        var expected: DataWords = DataWords.Failure("Test error")
        var actual = repository.translatedWord("Nothing")
        assertEquals(expected, actual)

        expected = DataWords.Test("Мышь","Mouse")
        actual = repository.translatedWord("Nothing")
        assertEquals(expected, actual)
    }

    @Test
    fun test_fetching_and_cache_translated_word() = runBlocking {
        repository.translatedWord("Nothing")
        repository.translatedWord("Nothing")
        repository.translatedWord("Nothing")
        repository.translatedWord("Nothing")
        val expected = listOf(
            Pair(
                "Mouse","Мышь"
            ),
            Pair(
                "Mouse","Мышь"
            )
        )
        val actual = repository.pairWords()
        assertEquals(expected,actual)
    }

}