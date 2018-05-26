package vcs

import shell.ShellCommand
import java.io.BufferedReader
import java.io.File

const val GIT_DELETED_IDX = 0
const val GIT_ADDED_IDX = 1
const val GIT_FILENAME_IDX = 2

class Git(private val shellCommand: ShellCommand) : VCS {
    override fun detect(path: File): Boolean {
        val f = File(path, ".git")

        return f.isDirectory && f.canRead()
    }

    override fun createReport(path: File): VcsReport {
        val reader = shellCommand.execute(
                listOf("log", "--all", "--numstat", "--date=short", "--pretty=format:'--%h--%ad--%aN'", "--no-renames"),
                path
        )

        return parse(reader)
    }

    private fun parse(reader: BufferedReader): VcsReport {
        var line = reader.readLine()
        val report = VcsReport()

        while (line != null) {
            if (line.isNotEmpty() && line.substring(1, 3) != "--") {
                val t = line.split("\t")
                report.addFile(t[GIT_FILENAME_IDX], t[GIT_DELETED_IDX].toLong() + t[GIT_ADDED_IDX].toLong())
            }

            line = reader.readLine()
        }

        return report
    }
}
