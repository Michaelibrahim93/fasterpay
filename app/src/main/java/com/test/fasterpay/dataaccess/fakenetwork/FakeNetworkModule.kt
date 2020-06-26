package com.test.fasterpay.dataaccess.fakenetwork

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class FakeNetworkModule {
    @Binds
    abstract fun bindWebService(webService: FakeWebServiceImpl): FakeWebService
}