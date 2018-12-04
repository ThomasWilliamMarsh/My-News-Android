package info.tommarsh.cleanbase.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import info.tommarsh.data.DataRepository
import info.tommarsh.data.source.local.Dao
import info.tommarsh.data.source.local.Database
import info.tommarsh.data.source.remote.ApiService
import info.tommarsh.domain.source.IRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideOkHttpClient() = OkHttpClient()

    @Provides
    fun provideApi(client: OkHttpClient) = Retrofit.Builder()
        .baseUrl("baseurl")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(client)
        .build()
        .create(ApiService::class.java)

    @Provides
    fun provideDb(db: Database): Dao = db

    @Provides
    fun provideRepository(repository: DataRepository): IRepository =
        repository
}