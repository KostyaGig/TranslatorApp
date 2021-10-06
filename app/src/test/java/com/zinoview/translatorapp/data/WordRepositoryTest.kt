package com.zinoview.translatorapp.data

import com.zinoview.translatorapp.data.cloud.CloudDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

/**
 * Test for [WordRepository.Test]
 */

class WordRepositoryTest {

    @Test
    fun test_fetching_translated_word() = runBlocking<Unit> {
        val cloudDataSource = CloudDataSource.Test()
        val repository = WordRepository.Test(cloudDataSource)

        var expected: DataWord = DataWord.Failure("Test error")
        var actual = repository.translatedWord("Nothing")
        assertEquals(expected, actual)

        expected = DataWord.Success("Мышь","Mouse",com.zinoview.translatorapp.core.Language.Test())
        actual = repository.translatedWord("Nothing")
        assertEquals(expected, actual)
    }

}