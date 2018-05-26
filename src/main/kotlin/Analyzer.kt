import report.Report
import java.io.BufferedWriter
import java.io.File
import java.io.OutputStreamWriter
import loc.Factory as LocFactory
import vcs.Factory as VcsFactory

class Analyzer(private val path: File) {
    fun analyze(): Report {
        val report = Report()
        val locReport = LocFactory().build().createReport(path)
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
