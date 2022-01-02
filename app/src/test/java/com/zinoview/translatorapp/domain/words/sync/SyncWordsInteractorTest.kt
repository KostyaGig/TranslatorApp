package com.zinoview.translatorapp.domain.words.sync

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Test for [com.zinoview.translatorapp.domain.words.sync.SyncWordsInteractor.Test.Base]
 * */

class SyncWordsInteractorTest {

    private var interactor: SyncWordsInteractor.Test? = null

    @Before
    fun setUp() {
        interactor = SyncWordsInteractor.Test.Base()
    }

    @Test
    fun test_success_sync_words() = runBlocking {
        interactor?.sync()
        val actual = interactor?.isSync()
        assertEquals(actual,true)
    }

    @Test
    fun test_failure_sync_words_failure_login_and_register() = runBlocking {
        interactor?.sync()
        interactor?.sync()
        val actual = interactor?.isSync()
        assertEquals(actual,false)
    }

    @After
    fun clean() {
        interactor = null
    }
}