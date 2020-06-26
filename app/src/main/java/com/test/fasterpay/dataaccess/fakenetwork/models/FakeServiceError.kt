package com.test.fasterpay.dataaccess.fakenetwork.models

import android.content.Context
import com.test.fasterpay.R

class FakeServiceError(
    val userMessage: String,
    message: String
): Throwable(message) {
    companion object {
        @JvmStatic
        fun wrongEmail(context: Context): FakeServiceError {
            return FakeServiceError(context.getString(R.string.no_cred_found)
                , "Email not registered")
        }

        @JvmStatic
        fun wrongPassword(context: Context): FakeServiceError {
            return FakeServiceError(context.getString(R.string.no_cred_found)
                , "Wrong password")
        }

        @JvmStatic
        fun userNotFound(context: Context): FakeServiceError {
            return FakeServiceError(context.getString(R.string.user_not_found)
                , "userNotFound")
        }

        @JvmStatic
        fun alreadyRegistered(context: Context): FakeServiceError {
            return FakeServiceError(context.getString(R.string.already_registered_user)
                , "alreadyRegistered")
        }

        @JvmStatic
        fun registrationFailed(context: Context): FakeServiceError {
            return FakeServiceError(context.getString(R.string.already_registered_user)
                , "registrationFailed")
        }
    }
}