package com.domnikl.forensics.complexity

import com.domnikl.forensics.report.Report
import com.domnikl.forensics.report.Reportable

class Report: Reportable {
    private val mapFilesToComplexity = mutableMapOf<String, Long>()

    fun addFile(filepath: String, complexity: Long) {
        mapFilesToComplexity[filepath] = complexity
    }

    override fun reportTo(report: Report.Builder) {
        for ((filepath, complexity) in mapFilesToComplexity) {
            report.addComplexity(filepath, complexity)
        }
    }
}
