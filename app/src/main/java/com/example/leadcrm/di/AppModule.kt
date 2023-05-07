package com.example.leadcrm.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.example.leadcrm.data.ApolloLeadsClient
import com.example.leadcrm.domain.GetLeadsUseCase
import com.example.leadcrm.domain.LeadsClient
import com.example.leadcrm.utils.Constants.GRAPHQL_TOKEN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Headers
import okhttp3.Headers.Companion.toHeaders
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient{
        val headers = mapOf("Authorization" to GRAPHQL_TOKEN)
       val okHttpClient = OkHttpClient.Builder()
           .addInterceptor{ chain ->
               val request = chain.request().newBuilder()
                   .headers(headers.toHeaders())
                   .build()
               chain.proceed(request)
           }
           .build()

        return ApolloClient.Builder()
            .serverUrl("http://54.246.238.84:3000/graphql")
            .okHttpClient(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
    fun provideLeadsApolloClient(apolloClient: ApolloClient):LeadsClient{
        return ApolloLeadsClient(apolloClient)
    }

    @Provides
    @Singleton
    fun provideGetLeadsUseCase(leadsClient: LeadsClient):GetLeadsUseCase{
        return GetLeadsUseCase(leadsClient)
    }
}