package com.cr.o.cdc.mlchallenge.di

import com.cr.o.cdc.mlchallenge.MLChallengeApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MLChallengeApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: MLChallengeApp)
}