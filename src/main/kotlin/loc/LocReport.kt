package loc

import report.Report
import report.Reportable

class LocReport: Reportable {
    private var map = mutableMapOf<String, Long>()

    fun addFile(filename: String, loc: Long) {
        map[filename] = loc
    }

    fun size(): Int {
        return map.size
    }

    override fun report(report: Report) {
        for ((file, loc) in this.map) {
            report.addLoc(file, loc)
        }
    }
}
