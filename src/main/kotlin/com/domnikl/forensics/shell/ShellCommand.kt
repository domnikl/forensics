package com.domnikl.forensics.shell

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

open class ShellCommand(val executable: File) {

    @Throws(IOException::class)
    open fun execute(command: List<String>, workingDirectory: File): BufferedReader {
        val proc = ProcessBuilder(listOf(executable.path) + command)
                .directory(workingDirectory)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()

        proc.waitFor(60, TimeUnit.MINUTES)

        if (proc.exitValue() != 0) {
            val message = proc.errorStream
                    .bufferedReader()
                    .readLines()
                    .joinToString("\n")

            throw IOException(message)
        }

        return proc.inputStream.bufferedReader()
    }
}
