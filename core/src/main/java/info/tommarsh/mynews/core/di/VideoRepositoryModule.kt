package info.tommarsh.mynews.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.tommarsh.mynews.core.video.data.VideoDataRepository
import info.tommarsh.mynews.core.video.data.VideoRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class VideoRepositoryModule {

    @Binds
    abstract fun provideVideoRepository(repository: VideoDataRepository): VideoRepository
}