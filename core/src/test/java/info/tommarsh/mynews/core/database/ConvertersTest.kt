package info.tommarsh.mynews.core.database

import info.tommarsh.mynews.core.article.data.local.model.Source
import info.tommarsh.mynews.test.UnitTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest : UnitTest<Converters>() {

    private val converters = Converters()

    private val source = fixture<Source>()
    private val sourceString = "${source.id} ${source.name}"

    @Test
    fun `Convert source to string`() {
        val expected = sourceString

        val actual = converters.sourceToString(source)

        assertEquals(expected, actual)
    }

    @Test
    fun `Convert string to source`() {
        val expected = source

        val actual = converters.stringToSource(sourceString)

        assertEquals(expected, actual)
    }

    override fun createSut(): Converters {
        return Converters()
    }
}