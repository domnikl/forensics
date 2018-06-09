package com.domnikl.forensics.report.writer

import com.domnikl.forensics.report.Report
import com.domnikl.forensics.report.Writer
import java.io.BufferedWriter
import kotlin.math.roundToLong

class CSV(private val writer: BufferedWriter): Writer {

    override fun write(report: Report) {
        val a = report.authors.joinToString(", ") {
            "${it.name}: ${it.totalChanges} (${it.percentChanges.roundToLong()}%)"
        }

        val l = report.languages.joinToString(", ") {
            "${it.name}: ${it.totalLoc} (${it.percentLoc.roundToLong()}%)"
        }

        writer.write("filename,language,changes,complexity,loc,(totalLoc = ${report.totalLoc}, languages = ($l), authors = ($a))\n")
        writer.flush()

        for (item in report.files) {
            writer.write("${item.filename},${item.language},${item.changes},${item.complexity},${item.loc}\n")
            writer.flush()
        }
    }
}
