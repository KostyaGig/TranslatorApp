package com.zinoview.translatorapp.data.auth

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Test for [com.zinoview.translatorapp.data.auth.AuthRepository.Test]
 * */

class AuthRepositoryTest {

    private lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {

        val authCloudDataSource = AuthCloudDataSource.Test()

        authRepository = AuthRepository.Test(authCloudDataSource,CloudAuthMapper.Base())
    }

    @Test
    fun test_success_register_user_in_application() = runBlocking<Unit> {
        val userName = "Kostya"
        val userNumberPhone = "123"

        val expected = DataAuth.Success("Success register user","Kostya")
        val actual = authRepository.register(userName,userNumberPhone)

        assertEquals(expected, actual)
    }

    @Test
    fun test_register_existing_user_in_application() = runBlocking<Unit> {
        val firstUserName = "Kostya"
        val firstUserNumberPhone = "123"

        authRepository.register(firstUserName,firstUserNumberPhone)

        val secondUserName = "Egor"
        val secondUserNumberPhone = "029"
        authRepository.register(secondUserName,secondUserNumberPhone)

        val thirdUserName = "Kostya"
        val thirdUserNumberPhone = "123"

        val expected = DataAuth.Exist("Phone $thirdUserNumberPhone already registered")
        val actual = authRepository.register(thirdUserName,thirdUserNumberPhone)

        assertEquals(expected, actual)
    }

    @Test
    fun test_failure_register_user_in_system() = runBlocking {
        val firstUserName = ""
        val firstUserNumberPhone = "123"

        val expected = DataAuth.Failure("Name or phone not should be empty")
        val actual = authRepository.register(firstUserName,firstUserNumberPhone)

        assertEquals(expected, actual)
    }

    @Test
    fun test_success_login_user_in_system() = runBlocking {
        val userName = "Kesha"
        val userNumberPhone = "876"

        authRepository.register(userName,userNumberPhone)

        val expected = DataAuth.Success("Success login in system",userName)
        val actual = authRepository.login(userName,userNumberPhone)

        assertEquals(expected, actual)
    }

    @Test
    fun test_login_with_not_found_number_phone_among_registered_in_system() = runBlocking {
        val userName = "Kesha"
        val userNumberPhone = "876"

        val expected = DataAuth.Failure("Not found user with number $userNumberPhone in system")
        val actual = authRepository.login(userName,userNumberPhone)

        assertEquals(expected, actual)
    }

    @Test
    fun test_login_user_with_incorrect_name_for_correct_number_phone() = runBlocking {
        val userName = "Vasiliy"
        val userNumberPhone = "524"

        authRepository.register(userName,userNumberPhone)

        val inCorrectUserName = "Petya"
        val correctUserNumberPhone = "524"

        val expected = DataAuth.Failure("Incorrect name user for $correctUserNumberPhone number")
        val actual = authRepository.login(inCorrectUserName,correctUserNumberPhone)

        assertEquals(expected, actual)
    }

    @Test
    fun test_failure_login_user_in_system() = runBlocking {
        val userName = "Nusha"
        val userNumberPhone = ""

        val expected = DataAuth.Failure("Name or phone not should be empty")
        val actual = authRepository.login(userName,userNumberPhone)

        assertEquals(expected, actual)
    }

    @Test
    fun test_is_authorized_user() = runBlocking {
        val userName = "Kostya"
        val userNumberPhone = "123"

        authRepository.register(userName,userNumberPhone)
        val expected = true
        val actual = authRepository.requestAuthorize()

        assertEquals(expected, actual)
    }
}