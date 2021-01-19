package info.tommarsh.mynews.core.database

interface TransactionRunner {
    
    suspend fun runTransaction(block: suspend () -> Unit)
}