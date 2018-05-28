package com.domnikl.forensics.report

import java.io.BufferedWriter
import kotlin.math.roundToLong

open class Report(
        private val totalLoc: Long,
        private val authors: List<Author>,
        private val languages: List<Language>,
        private val files: List<File>) {

    fun write(writer: BufferedWriter) {
        val a = authors.joinToString(", ") {
            "${it.name}: ${it.totalChanges} (${it.percentChanges.roundToLong()}%)"
        }

        val l = languages.joinToString(", ") {
            "${it.name}: ${it.totalLoc} (${it.percentLoc.roundToLong()}%)"
        }

        writer.write("filename,language,changes,loc,(totalLoc = $totalLoc, languages = ($l), authors = ($a))\n")
        writer.flush()

        for (item in files) {
            writer.write("${item.filename},${item.language},${item.changes},${item.loc}\n")
            writer.flush()
        }
    }

    data class Author(
            val name: String,
            val totalChanges: Long,
            val percentChanges: Double)

    data class Language(
            val name: String,
            val totalLoc: Long,
            val percentLoc: Double)

    data class File(
            val filename: String,
            var loc: Long = 0,
            var changes: Long = 0,
            var language: String = "")

    open class Builder {
        private var map = mutableMapOf<String, File>()
        private var authors = mutableMapOf<String, Long>()

        open fun addLoc(file: String, loc: Long) {
            ensureFileWasInitialized(file).loc = loc
        }

        open fun addLanguage(file: String, language: String) {
            ensureFileWasInitialized(file).language = language
        }

        open fun addChangeFreqs(file: String, changes: Long) {
            ensureFileWasInitialized(file).changes = changes
        }

        open fun addAuthor(name: String, changes: Long) {
            if (this.authors.containsKey(name)) {
                this.authors[name]!!.plus(changes)
            } else {
                this.authors[name] = changes
            }
        }

        fun build(): Report {
            val authors = this.authors.toList()

            val files = map
                    .toList()
                    .map { it.second }
                    .filter { it.loc > 0 && it.changes > 0 && it.filename.substring(0, 1) != "." }
                    .sortedByDescending { it.loc }
                    .sortedByDescending { it.changes }

            val totalLoc = files.fold(0L) { sum, e -> sum + e.loc }

            val locPerLanguage = files
                    .groupingBy { it.language }
                    .foldTo(mutableMapOf(), 0L) { sum, element -> sum + element.loc }
                    .mapValues { Pair(it.value, it.value * 100.0 / totalLoc) }
                    .toList()
                    .sortedByDescending { it.second.first }
                    .map { Language(name = it.first, totalLoc = it.second.first, percentLoc = it.second.second) }

            val totalChanges = authors.sumBy { it.second.toInt() }
            val a = authors
                    .toList()
                    .sortedByDescending { it.second }
                    .map { Author(name = it.first, totalChanges = it.second, percentChanges = it.second * 100.0 / totalChanges) }

            return Report(totalLoc, a, locPerLanguage, files)
        }

        private fun ensureFileWasInitialized(file: String): File {
            if (!map.containsKey(file)) {
                map[file] = File(filename = file)
            }

            return map[file]!!
        }
    }
}
