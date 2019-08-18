package info.tommarsh.mynews.repository.model.local.mapper

import info.tommarsh.mynews.core.Mapper
import info.tommarsh.mynews.core.model.SourceModel
import info.tommarsh.mynews.repository.model.local.Source
import javax.inject.Inject

class SourceDomainToDataMapper
@Inject constructor() : Mapper<SourceModel, Source> {
    override fun map(from: SourceModel) = Source(
        id = from.id,
        name = from.name
    )
}