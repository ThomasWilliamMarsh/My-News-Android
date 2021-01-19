package info.tommarsh.mynews.core.article.data.paging

import info.tommarsh.mynews.core.database.TransactionRunner

internal class SyncTransactionRunner : TransactionRunner {
    override suspend fun runTransaction(block: suspend () -> Unit) {
        block()
    }
}