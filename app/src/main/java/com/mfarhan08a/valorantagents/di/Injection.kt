package com.mfarhan08a.valorantagents.di

import com.mfarhan08a.valorantagents.data.Repository

object Injection {
    fun provideRepository(): Repository {
        return Repository.getInstance()
    }
}