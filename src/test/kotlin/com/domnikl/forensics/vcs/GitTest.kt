package com.domnikl.forensics.vcs

import com.domnikl.forensics.shell.ShellCommand
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.StringReader

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
}
