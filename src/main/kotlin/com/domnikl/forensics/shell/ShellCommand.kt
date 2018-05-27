package com.domnikl.forensics.shell

import java.io.BufferedReader
import java.io.File
import java.util.concurrent.TimeUnit

open class ShellCommand(private val executable: File) {
    open fun execute(command: List<String>, workingDirectory: File): BufferedReader {
        val proc = ProcessBuilder(listOf(executable.path) + command)
                .directory(workingDirectory)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()

        proc.waitFor(60, TimeUnit.MINUTES)

        return proc.inputStream.bufferedReader()
    }
}
