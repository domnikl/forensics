package com.domnikl.forensics.loc

import com.domnikl.forensics.report.Report
import com.domnikl.forensics.report.Reportable

class LocReport: Reportable {
    private var mapLoc = mutableMapOf<String, Pair<Long, String>>()

    fun addFile(filename: String, loc: Long, language: String) {
        mapLoc[filename] = Pair(loc, language)
    }

    fun size(): Int {
        return mapLoc.size
    }

    override fun reportTo(report: Report.Builder) {
        for ((file, item) in this.mapLoc) {
            report.addLoc(file, item.first)
            report.addLanguage(file, item.second)
        }
    }
}
