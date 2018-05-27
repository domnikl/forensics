package com.domnikl.forensics.loc

import com.domnikl.forensics.shell.ShellCommand
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import java.io.BufferedReader
import java.io.File
import java.io.StringReader

internal class FactoryTest {
    @Test(expected = NotImplementedError::class)
    fun throwsNotImplementedErrorForToolsOtherThanCloc() {
        Factory().build("foobar", ShellCommand(File("cloc")))
    }

    @Test(expected = IllegalArgumentException::class)
    fun throwsIllegalArgumentExceptionWhenShellCommandIsNotExecutable() {
        Factory().build(Factory.TOOL_CLOC, ShellCommand(File("foobar")))
    }

    @Test
    fun canBuildCloc() {
        val reader = BufferedReader(StringReader("1.0"))
        val mock = mock<ShellCommand>()
        whenever(mock.execute(any(), any())).thenReturn(reader)

        val cloc = Factory().build(Factory.TOOL_CLOC, mock)

        assert(cloc is Cloc)
    }
}
