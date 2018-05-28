package com.domnikl.forensics.vcs

import com.domnikl.forensics.report.Report
import com.domnikl.forensics.shell.ShellCommand
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.io.*

internal class GitTest {

    @Test
    fun detectReturnsTrueWhenShellCommandDoesNotThrowAnIOException() {
        val path = File("").absoluteFile
        val mockGit = mock<ShellCommand>()
        whenever(mockGit.execute(any(), any())).thenReturn(BufferedReader(StringReader("")))

        assert(Git(mockGit).detect(path))
    }

    @Test
    fun detectReturnsFalseWhenShellCommandThrowsIOException() {
        val path = File("").absoluteFile
        val mockGit = mock<ShellCommand>()
        whenever(mockGit.execute(any(), any())).thenThrow(IOException("fatal: not a git repository (or any of the parent directories): .git"))

        assert(!Git(mockGit).detect(path))
    }

    @Test
    fun canParseGitLogOutput() {
        val reportMock = mock<Report.Builder>()

        val reader = BufferedReader(InputStreamReader(javaClass.getResourceAsStream("/git_fixture.log")))
        val mockGit = mock<ShellCommand>()
        whenever(mockGit.execute(any(), any())).thenReturn(reader)

        val report = Git(mockGit).createReport(File(""))
        report.reportTo(reportMock)

        assertEquals(3, report.size())

        verify(reportMock).addAuthor("Dominik Liebler", 80L)
        verify(reportMock).addAuthor("Kinimod Relbeil", 20L)
        verify(reportMock).addChangeFreqs("src/main/kotlin/com/domnikl/forensics/vcs/Git.kt", 80)
        verify(reportMock).addChangeFreqs("src/main/kotlin/com/domnikl/forensics/Analyzer.kt", 18)
        verify(reportMock).addChangeFreqs("src/main/kotlin/com/domnikl/forensics/complexity/Scanner.kt", 2)
    }
}
