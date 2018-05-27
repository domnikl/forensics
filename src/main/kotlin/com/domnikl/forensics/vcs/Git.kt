package com.domnikl.forensics.vcs

import com.domnikl.forensics.shell.ShellCommand
import java.io.BufferedReader
import java.io.File
import java.io.IOException

class Git(private val shellCommand: ShellCommand) : VCS {
    companion object {
        const val GIT_HEADER_AUTHOR_IDX = 3
        const val GIT_DELETED_IDX = 0
        const val GIT_ADDED_IDX = 1
        const val GIT_FILENAME_IDX = 2
    }

    override fun detect(path: File): Boolean {
        try {
            shellCommand.execute(listOf("log", "-1"), path)
        } catch (e: IOException) {
            return false
        }

        return true
    }

    override fun createReport(path: File): VcsReport {
        val logReader = shellCommand.execute(
                listOf("log", "--all", "--numstat", "--date=short", "--pretty=format:--%h--%ad--%aN", "--no-renames"),
                path
        )

        return parse(logReader)
    }

    private fun parse(reader: BufferedReader): VcsReport {
        var line = reader.readLine()
        val report = VcsReport()
        var author = ""

        while (line != null) {
            if (line.isNotEmpty() && line.startsWith("--")) {
                author = parseHeader(line)
            } else if (line.isNotEmpty() && !line.startsWith("-")) {
                val t = line.split("\t")
                report.addChanges(
                        t[GIT_FILENAME_IDX],
                        changes = t[GIT_DELETED_IDX].toLong() + t[GIT_ADDED_IDX].toLong(),
                        author = author)
            }

            line = reader.readLine()
        }

        reader.close()

        return report
    }

    private fun parseHeader(line: String): String {
        return line.split("--")[GIT_HEADER_AUTHOR_IDX]
    }
}
