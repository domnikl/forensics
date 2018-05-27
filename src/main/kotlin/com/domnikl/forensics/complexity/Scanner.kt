package com.domnikl.forensics.complexity

import java.io.BufferedReader
import java.io.File

class Scanner {
    fun scan(path: File): Map<String, Long> {
        return scanForFiles(path)
                .map { it.path to scanFile(it) }
                .filter { it.second > 0 }
                .toMap()
    }

    private fun scanForFiles(path: File): List<File> {
        val maxFileSize = 256 * 1024

        return path
                .walk()
                .onEnter { !it.isHidden }
                .toList()
                .filter { it.isFile && it.canRead() && it.length() > 0 && it.length() < maxFileSize }
    }

    private fun scanFile(f: File): Long {
        val reader = BufferedReader(f.reader())
        var line = reader.readLine()
        var indentations = 0L

        while (line != null) {
            if (line.trim().isNotEmpty()) {
                indentations += (line.length - line.trimStart().length).toLong()
            }

            line = reader.readLine()
        }

        reader.close()

        return indentations
    }
}
