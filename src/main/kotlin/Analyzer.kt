import com.domnikl.forensics.Report
import java.io.File
import vcs.Factory as VcsFactory
import loc.Factory as LocFactory

class Analyzer (private val path: File) {
    fun analyze(): Report {
        val locReport = LocFactory().build().createReport(path)

        return Report()
    }
}

fun main(args: Array<String>) {
    Analyzer(File("").absoluteFile).analyze()
}
