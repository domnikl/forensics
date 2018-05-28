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

        assertEquals(2, vcsReport.size())

        verify(reportMock).addAuthor("Dominik Liebler", 80)
        verify(reportMock).addAuthor("Peter Lustig", 20)
        verify(reportMock).addChangeFreqs("src/foobar.ruby", 100)
    }
}
