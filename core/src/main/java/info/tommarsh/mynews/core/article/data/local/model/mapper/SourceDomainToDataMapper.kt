package info.tommarsh.mynews.core.article.data.local.model.mapper

import info.tommarsh.mynews.core.article.data.local.model.Source
import info.tommarsh.mynews.core.article.domain.model.SourceModel
import info.tommarsh.mynews.core.model.Mapper
import javax.inject.Inject

class SourceDomainToDataMapper
@Inject constructor() : Mapper<SourceModel, Source> {
    override fun map(from: SourceModel) = Source(
        id = from.id,
        name = from.name
    )
}