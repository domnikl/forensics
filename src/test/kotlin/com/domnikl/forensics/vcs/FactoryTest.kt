package com.domnikl.forensics.vcs

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import java.io.File

internal class FactoryTest {
    @Test
    fun canBuildForGitVCS() {
        val path = File("").absoluteFile
        val vcsMock = createMockVCS(true)
        val config = listOf(vcsMock)

        Factory(config).build(path)
        verify(vcsMock).detect(path)
    }

    @Test(expected = UnknownVcsException::class)
    fun throwsUnknownVcsExceptionWhenNoVcsMatches() {
        Factory(emptyList()).build(File("").absoluteFile)
    }

    private fun createMockVCS(isDetected: Boolean): VCS {
        val mock = mock<VCS>()
        whenever(mock.detect(any())).thenReturn(isDetected)

        return mock
    }
}
