package info.tommarsh.mynews.repository.model.remote.mapper

import info.tommarsh.mynews.core.Mapper
import info.tommarsh.mynews.core.model.SourceModel
import info.tommarsh.mynews.repository.model.remote.SourceResponse
import javax.inject.Inject

class SourceResponseMapper
@Inject constructor() : Mapper<SourceResponse, SourceModel> {

    override fun map(from: SourceResponse) = SourceModel(
        id = from.id.orEmpty(),
        name = from.name.orEmpty()
    )
}