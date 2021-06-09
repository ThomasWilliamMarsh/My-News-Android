package info.tommarsh.mynews.paging

import info.tommarsh.mynews.core.database.TransactionRunner

class SyncTransactionRunner : TransactionRunner {

    override suspend fun runTransaction(block: suspend () -> Unit) {
        block()
    }
}