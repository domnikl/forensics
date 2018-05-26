package vcs

import report.Report
import report.Reportable

class VcsReport: Reportable {
    private var map = mutableMapOf<String, Long>()

    fun addFile(filename: String, changes: Long) {
        map[filename] = changes
    }

    fun size(): Int {
        return map.size
    }

    override fun report(report: Report) {
        for ((file, changes) in this.map) {
            report.addChangeFreqs(file, changes)
        }
    }
}
