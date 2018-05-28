package com.domnikl.forensics.vcs

import com.domnikl.forensics.report.Report
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import kotlin.test.assertEquals

internal class VcsReportTest {

    @Test
    fun canAddChangesFromFiles() {
        val vcsReport = VcsReport()
        val reportMock = mock<Report.Builder>()

        vcsReport.addChanges("src/foobar.ruby", 20, "Peter Lustig")
        vcsReport.addChanges("src/foobar.ruby", 80, "Dominik Liebler")
        vcsReport.reportTo(reportMock)

        val authors = mapOf("Dominik Liebler" to Pair(80L, 80.0), "Peter Lustig" to Pair(20L, 20.0))

        assertEquals(2, vcsReport.size())
        verify(reportMock).addAuthors(authors)
        verify(reportMock).addChangeFreqs("src/foobar.ruby", 100L)
    }
}
