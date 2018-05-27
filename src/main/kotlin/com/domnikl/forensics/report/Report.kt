package com.domnikl.forensics.report

import java.io.BufferedWriter
import kotlin.math.roundToLong

open class Report {
    private var map = mutableMapOf<String, Item>()
    private var authors = mapOf<String, Pair<Long, Double>>()

    open fun addLoc(file: String, loc: Long) {
        ensureItemWasInitialized(file)
        map[file]!!.loc = loc
    }

    open fun addLanguage(file: String, language: String) {
        ensureItemWasInitialized(file)
        map[file]!!.language = language
    }

    fun addChangeFreqs(file: String, changes: Long) {
        ensureItemWasInitialized(file)
        map[file]!!.changes = changes
    }

    fun addAuthors(authors: Map<String, Pair<Long, Double>>) {
        this.authors = authors
    }

    private fun ensureItemWasInitialized(file: String) {
        if (!map.containsKey(file)) {
            map[file] = Item(filename = file)
        }
    }

    fun write(writer: BufferedWriter) {
        // TODO: most parts of this should be in a Builder class

        val l = map.toList().map { it.second }
                .filter { it.loc > 0 && it.changes > 0 && it.filename.substring(0, 1) != "." }
                .sortedByDescending { it.loc }
                .sortedByDescending { it.changes }

        val totalLoc = l.fold(0L) { sum, element -> sum + element.loc }

        val locPerLanguage = l
                .filter { it.loc > 0 }
                .groupingBy { it.language }
                .foldTo(mutableMapOf(), 0L) { sum, element -> sum + element.loc }
                .mapValues { Pair(it.value, it.value * 100.0 / totalLoc) }
                .toList()
                .sortedByDescending { it.second.first }
                .joinToString(", ") { "${it.first} = ${it.second.first} (${it.second.second.roundToLong()}%)" }

        val a = authors
                .toList()
                .sortedByDescending { it.second.first }
                .joinToString(", ") { "${it.first} = ${it.second.first} (${it.second.second.roundToLong()}%)" }

        writer.write("filename,language,changes,loc,(totalLoc = $totalLoc, languages = ($locPerLanguage), authors = ($a))\n")
        writer.flush()

        for (item in l) {
            writer.write("${item.filename},${item.language},${item.changes},${item.loc}\n")
            writer.flush()
        }
    }

    data class Item(val filename: String, var loc: Long = 0, var changes: Long = 0, var language: String = "")
}
