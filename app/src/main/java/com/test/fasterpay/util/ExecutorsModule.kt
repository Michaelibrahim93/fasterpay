package com.test.fasterpay.util

import android.os.Handler
import android.os.Looper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object ExecutorsModule {
    const val EXECUTOR_DISK = "EXECUTOR_DISK"
    const val EXECUTOR_NETWORK = "EXECUTOR_NETWORK"
    const val EXECUTOR_MAIN = "EXECUTOR_MAIN"

    @Singleton
    @Named(EXECUTOR_DISK) @Provides @JvmStatic
    fun diskExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    @Singleton
    @Named(EXECUTOR_MAIN) @Provides @JvmStatic
    fun mainExecutor(): Executor {
        return MainThreadExecutor()
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}