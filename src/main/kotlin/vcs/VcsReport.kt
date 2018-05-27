package vcs

import report.Report
import report.Reportable

class VcsReport: Reportable {
    private var listOfChanges = mutableListOf<Change>()
    private var totalChanges = 0L

    fun addChanges(filename: String, changes: Long, author: String) {
        listOfChanges.add(Change(filename, changes, author))
        totalChanges += changes
    }

    override fun report(report: Report) {
        val changesByFiles = listOfChanges
                .groupingBy { it.filename }
                .foldTo(mutableMapOf(), 0L) { sum, e -> sum + e.changes }

        for ((file, p) in changesByFiles) {
            report.addChangeFreqs(file, p)
        }

        val authors = listOfChanges
                .groupingBy { it.author }
                .foldTo(mutableMapOf(), 0L) { sum, e -> sum + e.changes }
                .mapValues { Pair(it.value, it.value * 100.0 / totalChanges) }

        report.addAuthors(authors)
    }

    private data class Change(val filename: String, val changes: Long, val author: String)
}
