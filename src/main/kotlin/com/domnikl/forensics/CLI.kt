package com.domnikl.forensics

import com.domnikl.forensics.complexity.Scanner
import com.domnikl.forensics.report.Report
import com.domnikl.forensics.report.Reportable
import com.domnikl.forensics.report.writer.CSV
import com.domnikl.forensics.shell.ShellCommand
import com.domnikl.forensics.vcs.Git
import java.io.BufferedWriter
import java.io.File
import java.io.OutputStreamWriter
import java.time.LocalDateTime
import com.domnikl.forensics.loc.Factory as LocFactory
import com.domnikl.forensics.vcs.Factory as VcsFactory

class CLI(private val path: File) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // that should be long enough to include everything
            val after = LocalDateTime.now().minusYears(20)
            val report = CLI(File("").absoluteFile).analyze(after)
            val writer = CSV(BufferedWriter(OutputStreamWriter(System.out)))

            writer.write(report)
        }
    }

    fun analyze(after: LocalDateTime): Report {
        val cloc = ShellCommand(File("cloc"))
        val vcsConfig = listOf(Git(ShellCommand(File("git"))))
        val scanner = Scanner()
        val reportBuilder = Report.Builder()

        val reports = mutableListOf<Reportable>()

        reports.add(VcsFactory(vcsConfig).build(path).createReport(path, after))
        reports.add(LocFactory().build(LocFactory.TOOL_CLOC, cloc).createReport(path))
        reports.add(scanner.scan(path))

        for (reportable in reports) {
            reportable.reportTo(reportBuilder)
        }

        return reportBuilder.build()
    }
}
