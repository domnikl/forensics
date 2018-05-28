package com.domnikl.forensics.vcs

import com.domnikl.forensics.report.Report
import com.domnikl.forensics.report.Reportable

class VcsReport: Reportable {
    private var listOfChanges = mutableListOf<Change>()
    private var totalChanges = 0L

    fun addChanges(filename: String, changes: Long, author: String) {
        listOfChanges.add(Change(filename, changes, author))
        totalChanges += changes
    }

    fun size(): Int {
        return listOfChanges.count()
    }

    override fun reportTo(report: Report.Builder) {
        listOfChanges
                .groupingBy { it.filename }
                .foldTo(mutableMapOf(), 0L) { sum, e -> sum + e.changes }
                .forEach { report.addChangeFreqs(it.key, it.value) }

        listOfChanges
                .groupingBy { it.author }
                .foldTo(mutableMapOf(), 0L) { sum, e -> sum + e.changes }
                .mapValues { Pair(it.value, it.value * 100.0 / totalChanges) }
                .forEach { report.addAuthor(it.key, it.value.first) }
    }

    private data class Change(val filename: String, val changes: Long, val author: String)
}
