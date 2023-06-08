package com.gvfs.vollmed

import com.gvfs.vollmed.features.login.service.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.ktor.client.HttpClient

@Module
@InstallIn(ActivityRetainedComponent::class)
object LoginServiceModule {

    @Provides
    @ActivityRetainedScoped
    fun provideHttpClient(): HttpClient {
        return HttpClient()
    }

    @Provides
    @ActivityRetainedScoped
    fun provideLoginService(httpClient: HttpClient): LoginService {
        return LoginService(httpClient)
    }

}