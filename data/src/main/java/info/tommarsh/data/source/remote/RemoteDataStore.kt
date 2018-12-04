package info.tommarsh.data.source.remote

import info.tommarsh.data.model.remote.mapper.RemoteDataMapper
import javax.inject.Inject

class RemoteDataStore
@Inject constructor(
    private val mapper: RemoteDataMapper,
    private val networkHelper: NetworkHelper,
    private val api: ApiService
)