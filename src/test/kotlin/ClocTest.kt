import com.nhaarman.mockito_kotlin.*
import loc.Cloc
import shell.ShellCommand
import java.io.BufferedReader
import java.io.File
import java.io.Reader
import java.io.StringReader
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ClocTest {

    @Test(expected = IllegalArgumentException::class)
    fun throwsExceptionWhenTryingToReadEmpyCsv() {
        val mock = createShellCommand(BufferedReader(StringReader("")))

        Cloc(mock).createReport(File("."))
    }

    @Test
    fun returnsEmptyReportWhenNoLineInCsvReport() {
        val reader = BufferedReader(StringReader("\nlanguage,filename,blank,comment,code\n"))
        val mock = createShellCommand(reader)

        val report = Cloc(mock).createReport(File("."))

        assertEquals(0, report.size())
    }

    @Test
    fun returnsCanParseCsvOutput() {
        val reader = BufferedReader(StringReader("\nlanguage,filename,blank,comment,code\nJava,src/main/Foo.java,0,0,42\n"))
        val mock = createShellCommand(reader)

        val report = Cloc(mock).createReport(File("."))

        assertEquals(1, report.size())
    }

    private fun createShellCommand(reader: BufferedReader): ShellCommand
    {
        val mock = mock<ShellCommand>()

        whenever(mock.execute(any(), any())).thenReturn(reader)

        return mock
    }
}
