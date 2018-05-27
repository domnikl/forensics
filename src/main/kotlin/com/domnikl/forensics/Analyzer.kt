package com.domnikl.forensics

import com.domnikl.forensics.report.Report
import com.domnikl.forensics.shell.ShellCommand
import java.io.BufferedWriter
import java.io.File
import java.io.OutputStreamWriter
import com.domnikl.forensics.loc.Factory as LocFactory
import com.domnikl.forensics.vcs.Factory as VcsFactory

class Analyzer(private val path: File) {
    fun analyze(): Report {
        val cloc = ShellCommand(File("cloc"))

        val report = Report()
        val locReport = LocFactory().build(LocFactory.TOOL_CLOC, cloc).createReport(path)
        val vcsReport = VcsFactory().build(path).createReport(path)

        locReport.report(report)
        vcsReport.report(report)

        return report
    }
}

fun main(args: Array<String>) {
    val report = Analyzer(File("").absoluteFile).analyze()

    report.write(BufferedWriter(OutputStreamWriter(System.out)))
}
