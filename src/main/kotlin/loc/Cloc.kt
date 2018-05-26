package loc

import shell.ShellCommand
import java.io.BufferedReader
import java.io.File

const val CLOC_FILENAME_IDX = 1
const val CLOC_LOC_IDX = 4

class Cloc(private val shellCommand: ShellCommand) : LocAdapter {
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

        if (header == null || header.substring(0, expectedHeader.length) != expectedHeader) {
            throw IllegalArgumentException("cloc header expected to begin with $expectedHeader, but was $header")
        }

        val report = LocReport()
        line = reader.readLine()

        while (line != null) {
            val tokens = line.split(",")
            val filename = tokens[CLOC_FILENAME_IDX].removePrefix("./")

            report.addFile(filename, tokens[CLOC_LOC_IDX].toLong())

            line = reader.readLine()
        }

        return report
    }
}
