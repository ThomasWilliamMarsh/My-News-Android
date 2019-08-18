package info.tommarsh.mynews.core

interface Mapper<From, To> {
    fun map(from: From): To
}