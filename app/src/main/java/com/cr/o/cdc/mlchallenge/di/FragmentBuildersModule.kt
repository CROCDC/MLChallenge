package com.cr.o.cdc.mlchallenge.di

import com.cr.o.cdc.mlchallenge.ui.ProductDetailFragment
import com.cr.o.cdc.mlchallenge.ui.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributesProductDetailFragment(): ProductDetailFragment
}
