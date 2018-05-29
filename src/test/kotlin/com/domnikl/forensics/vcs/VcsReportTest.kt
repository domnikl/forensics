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

        verify(reportMock).addChange("Peter Lustig", "src/foobar.ruby", 20)
        verify(reportMock).addChange("Dominik Liebler", "src/foobar.ruby", 80)
    }
}
