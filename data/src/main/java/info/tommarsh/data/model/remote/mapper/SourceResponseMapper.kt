package info.tommarsh.data.model.remote.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.data.model.remote.SourceResponse
import info.tommarsh.domain.model.SourceModel
import javax.inject.Inject

class SourceResponseMapper
@Inject constructor() : Mapper<SourceResponse, SourceModel> {

    override fun map(from: SourceResponse) = SourceModel(
        id = from.id.orEmpty(),
        name = from.name.orEmpty()
    )
}