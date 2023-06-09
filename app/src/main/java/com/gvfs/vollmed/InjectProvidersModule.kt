package com.gvfs.vollmed

import android.content.Context
import com.gvfs.vollmed.config.httpInterceptor.AuthInterceptor
import com.gvfs.vollmed.features.appointment.service.AppointmentService
import com.gvfs.vollmed.features.doctor.DoctorService
import com.gvfs.vollmed.features.login.service.LoginService
import com.gvfs.vollmed.features.patient.PatientService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object InjectProvidersModule {

    @Provides
    @ActivityRetainedScoped
    fun provideAuthInterceptor(
        @ApplicationContext context: Context
    ): AuthInterceptor {
        return AuthInterceptor(context)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideLoginService(
        authInterceptor: AuthInterceptor,
        @ApplicationContext context: Context
    ): LoginService {
        return LoginService(authInterceptor, context)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideDoctorService(
        authInterceptor: AuthInterceptor
    ): DoctorService {
        return DoctorService(authInterceptor)
    }

    @Provides
    @ActivityRetainedScoped
    fun providePatientService(
        authInterceptor: AuthInterceptor
    ): PatientService {
        return PatientService(authInterceptor)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideAppointmentService(
        authInterceptor: AuthInterceptor
    ): AppointmentService {
        return AppointmentService(authInterceptor)
    }

}