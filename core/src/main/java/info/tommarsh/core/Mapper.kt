package info.tommarsh.core

interface Mapper<From, To> {
    fun map(from: From): To
}