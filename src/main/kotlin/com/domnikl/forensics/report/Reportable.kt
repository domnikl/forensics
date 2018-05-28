package com.domnikl.forensics.report

interface Reportable {
    fun reportTo(report: Report.Builder)
}
