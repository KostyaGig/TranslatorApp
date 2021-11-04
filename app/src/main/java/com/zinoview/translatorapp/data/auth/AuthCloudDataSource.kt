package com.zinoview.translatorapp.data.auth

interface AuthCloudDataSource {

    suspend fun register(userName: String,userPhone: String) : CloudAuth

    suspend fun login(userName: String,userPhone: String) : CloudAuth

    class Base(
        private val authService: AuthService
    ) : AuthCloudDataSource {

        override suspend fun register(userName: String, userPhone: String): CloudAuth {
            return authService.register(userName,userPhone)
        }

        override suspend fun login(userName: String, userPhone: String): CloudAuth {
            return authService.login(userName,userPhone)
        }
    }

    class Test : AuthCloudDataSource {

        data class TestUser(
            val userName: String,
            val userPhone: String
        )

        private var authorizes = mutableListOf<TestUser>()
        private var authorizesPhones = mutableListOf<String>()

        override suspend fun register(userName: String, userPhone: String): CloudAuth {
            if (authorizesPhones.contains(userPhone)) {
                return CloudAuth.Base("Phone $userPhone already registered","Exist",uniqueKey = "Empty key")
            }
            return if (userName.isNotEmpty() && userPhone.isNotEmpty()) {
                authorizes.add(TestUser(userName,userPhone))
                authorizesPhones.add(userPhone)
                CloudAuth.Base("Success register user","Success",uniqueKey = userName)
            } else {
                CloudAuth.Base("Name or phone not should be empty","Failure",uniqueKey = "Empty key")
            }
        }

        override suspend fun login(userName: String, userPhone: String): CloudAuth {

            if (userName.isEmpty() || userPhone.isEmpty()) {
                return CloudAuth.Base("Name or phone not should be empty","Failure",uniqueKey = "Empty key")
            }

            if (authorizesPhones.contains(userPhone).not()) {
                return CloudAuth.Base("Not found user with number $userPhone in system","Failure",uniqueKey = "Empty key")
            }

            val loginUser = TestUser(userName,userPhone)
            return if (authorizes.contains(loginUser)) {
                CloudAuth.Base("Success login in system","Success",uniqueKey = userName)
            } else {
                CloudAuth.Base("Incorrect name user for $userPhone number","Failure",uniqueKey = "Empty key")
            }
        }
    }
}