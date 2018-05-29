package com.domnikl.forensics.shell

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

open class ShellCommand(val executable: File) {

    @Throws(IOException::class)
    open fun execute(command: List<String>, workingDirectory: File): BufferedReader {
        return buildProcess(command, workingDirectory).inputStream.bufferedReader()
    }

    @Throws(IOException::class)
    open fun executeWithTimeout(command: List<String>, workingDirectory: File, timeout: Long, unit: TimeUnit): BufferedReader {
        val process = buildProcess(command, workingDirectory)

        process.waitFor(timeout, unit)

        try {
            if (process.exitValue() != 0) {
                val message = process.errorStream
                        .bufferedReader()
                        .readLines()
                        .joinToString("\n")

                throw IOException(message)
            }
        } catch (e: IllegalThreadStateException) {
            process.destroyForcibly()

            throw IOException(e)
        }

        return process.inputStream.bufferedReader()
    }

    private fun buildProcess(command: List<String>, workingDirectory: File): Process {
        return ProcessBuilder(listOf(executable.path) + command)
                .directory(workingDirectory)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()
    }
}
