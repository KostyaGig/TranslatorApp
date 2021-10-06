package com.zinoview.translatorapp.data

import org.junit.Assert.*
import org.junit.Test
import java.lang.Exception
import java.net.UnknownHostException

/**
 * Test for [ExceptionMapper.Test]
 */

class ExceptionMapperTest {

    @Test
    fun test_map_handled_exception() {
        val exceptionMapper = ExceptionMapper.Test()
        val expected = "No connection"
        val actual = exceptionMapper.map(UnknownHostException())
        assertEquals(expected,actual)
    }

    @Test
    fun test_map_not_handled_exception() {
        val exceptionMapper = ExceptionMapper.Test()
        val expected = "Generic"
        val actual = exceptionMapper.map(GenericException())
        assertEquals(expected,actual)
    }

    private inner class GenericException : Exception()
}