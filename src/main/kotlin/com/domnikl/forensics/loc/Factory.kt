package com.domnikl.forensics.loc

import com.domnikl.forensics.shell.ShellCommand
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

class Factory {
    companion object {
        const val TOOL_CLOC = "cloc"
    }

    fun build(tool: String, pathToCloc: ShellCommand): LocAdapter {
        if (tool != TOOL_CLOC) {
            throw NotImplementedError("Tool $tool is not supported, only $TOOL_CLOC is supported by now")
        }

        if (!checkIfBinaryIsValid(pathToCloc)) {
            throw IllegalArgumentException("Invalid path to cloc given: ${pathToCloc.executable.path}")
        }

        return Cloc(pathToCloc)
    }

    private fun checkIfBinaryIsValid(binary: ShellCommand): Boolean
    {
        try {
            binary.executeWithTimeout(listOf("--version"), File("").absoluteFile, 10, TimeUnit.SECONDS)
        } catch (e: IOException) {
            return false
        }

        return true
    }
}
