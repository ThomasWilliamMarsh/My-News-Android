package info.tommarsh.data

import info.tommarsh.data.source.local.LocalDataStore
import info.tommarsh.data.source.remote.RemoteDataStore
import info.tommarsh.domain.source.IRepository
import javax.inject.Inject

class DataRepository
@Inject constructor(
    private val local: LocalDataStore,
    private val remote: RemoteDataStore
) : IRepository