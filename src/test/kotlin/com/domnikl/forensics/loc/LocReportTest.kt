package com.domnikl.forensics.loc

import com.domnikl.forensics.report.Report
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

internal class LocReportTest {
    @Test
    fun canBeAddedToReport() {
        val locReport = LocReport()

        val reportMock = mock<Report>()

        locReport.addFile("src/foobar.rb", 42, "Ruby")
        locReport.report(reportMock)

        verify(reportMock).addLoc("src/foobar.rb", 42)
        verify(reportMock).addLanguage("src/foobar.rb", "Ruby")
    }
}
