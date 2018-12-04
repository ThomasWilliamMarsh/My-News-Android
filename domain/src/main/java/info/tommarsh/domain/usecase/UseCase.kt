package info.tommarsh.domain.usecase

import info.tommarsh.domain.source.IRepository
import javax.inject.Inject

class UseCase
@Inject constructor(private val repository: IRepository)