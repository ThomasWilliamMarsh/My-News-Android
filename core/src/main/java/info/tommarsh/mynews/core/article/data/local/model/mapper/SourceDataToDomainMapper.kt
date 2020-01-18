package info.tommarsh.mynews.core.article.data.local.model.mapper

import info.tommarsh.mynews.core.article.data.local.model.Source
import info.tommarsh.mynews.core.article.domain.model.SourceModel
import info.tommarsh.mynews.core.model.Mapper
import javax.inject.Inject

class SourceDataToDomainMapper
@Inject constructor() : Mapper<Source, SourceModel> {
    override fun map(from: Source) = SourceModel(
        id = from.id,
        name = from.name
    )
}