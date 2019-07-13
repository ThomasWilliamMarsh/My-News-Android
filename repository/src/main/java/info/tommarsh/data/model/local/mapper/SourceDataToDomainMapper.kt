package info.tommarsh.data.model.local.mapper

import info.tommarsh.core.Mapper
import info.tommarsh.data.model.local.Source
import info.tommarsh.domain.model.SourceModel
import javax.inject.Inject

class SourceDataToDomainMapper
@Inject constructor() : Mapper<Source, SourceModel> {
    override fun map(from: Source) = SourceModel(
        id = from.id,
        name = from.name
    )
}