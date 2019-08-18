package info.tommarsh.mynews.repository.model.local.mapper

import info.tommarsh.mynews.core.Mapper
import info.tommarsh.mynews.core.model.SourceModel
import info.tommarsh.mynews.repository.model.local.Source
import javax.inject.Inject

class SourceDataToDomainMapper
@Inject constructor() : Mapper<Source, SourceModel> {
    override fun map(from: Source) = SourceModel(
        id = from.id,
        name = from.name
    )
}