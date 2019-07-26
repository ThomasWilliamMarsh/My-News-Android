package info.tommarsh.data.model.local.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.data.model.local.Source
import info.tommarsh.core.model.SourceModel
import javax.inject.Inject

class SourceDomainToDataMapper
@Inject constructor() : Mapper<SourceModel, Source> {
    override fun map(from: SourceModel) = Source(
        id = from.id,
        name = from.name
    )
}