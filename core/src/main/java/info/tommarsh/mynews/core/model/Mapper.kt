package info.tommarsh.mynews.core.model

interface Mapper<From, To> {
    fun map(from: From): To
}