package com.domnikl.forensics.vcs

import com.domnikl.forensics.report.Report
import com.domnikl.forensics.report.Reportable

class VcsReport: Reportable {
    private var listOfChanges = mutableListOf<Change>()

    fun addChanges(filename: String, changes: Long, author: String) {
        listOfChanges.add(Change(filename, changes, author))
    }

    fun size(): Int {
        return listOfChanges.count()
    }

    override fun reportTo(report: Report.Builder) {
        listOfChanges.forEach {
            report.addChange(it.author, it.filename, it.changes)
        }
    }

    private data class Change(val filename: String, val changes: Long, val author: String)
}
