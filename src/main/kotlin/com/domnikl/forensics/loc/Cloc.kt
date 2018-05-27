package com.domnikl.forensics.loc

import com.domnikl.forensics.shell.ShellCommand
import java.io.BufferedReader
import java.io.File

class Cloc(private val shellCommand: ShellCommand) : LocAdapter {
    companion object {
        const val CLOC_LANGUAGE_IDX = 0
        const val CLOC_FILENAME_IDX = 1
        const val CLOC_LOC_IDX = 4
    }

    override fun createReport(path: File): LocReport {
        return parse(execute(path))
    }

    private fun execute(path: File): BufferedReader {
        return shellCommand.execute(
                listOf(".", "--unix", "--by-file", "--csv", "--quiet"),
                path
        )
    }

    private fun parse(reader: BufferedReader): LocReport {
        val expectedHeader = "language,filename,blank,comment,code"
        var header = reader.readLine()
        var line: String?

        if (header == "" || header == null) {
            header = reader.readLine()
        }

        if (header == null || !header.startsWith(expectedHeader)) {
            throw IllegalArgumentException("cloc header expected to begin with $expectedHeader, but was $header")
        }

        val report = LocReport()
        line = reader.readLine()

        while (line != null) {
            val tokens = line.split(",")
            val filename = tokens[CLOC_FILENAME_IDX].removePrefix("./")

            report.addFile(
                    filename,
                    loc = tokens[CLOC_LOC_IDX].toLong(),
                    language = tokens[CLOC_LANGUAGE_IDX]
                    )

            line = reader.readLine()
        }

        reader.close()

        return report
    }
}
