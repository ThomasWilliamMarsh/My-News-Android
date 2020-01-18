package info.tommarsh.mynews.core.article.data.remote.model.mapper

import info.tommarsh.mynews.core.article.data.remote.model.SourceResponse
import info.tommarsh.mynews.core.article.domain.model.SourceModel
import info.tommarsh.mynews.core.model.Mapper
import javax.inject.Inject

class SourceResponseMapper
@Inject constructor() : Mapper<SourceResponse, SourceModel> {

    override fun map(from: SourceResponse) =
        SourceModel(
            id = from.id.orEmpty(),
            name = from.name.orEmpty()
        )
}