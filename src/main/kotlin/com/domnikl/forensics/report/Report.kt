package com.domnikl.forensics.report

open class Report(
        val totalLoc: Long,
        val authors: List<Author>,
        val languages: List<Language>,
        val files: List<File>) {

    data class Author(
            val name: String,
            var totalChanges: Long = 0,
            var percentChanges: Double = 0.0)

    data class Language(
            val name: String,
            val totalLoc: Long,
            val percentLoc: Double)

    data class File(
            val filename: String,
            var loc: Long = 0,
            var changes: Long = 0,
            var complexity: Long = 0,
            var language: String = "")

    open class Builder {
        private var files = mutableMapOf<String, File>()
        private var authors = mutableMapOf<String, Author>()

        open fun addLoc(file: String, loc: Long) {
            ensureFileWasInitialized(file).loc = loc
        }

        open fun addLanguage(file: String, language: String) {
            ensureFileWasInitialized(file).language = language
        }

        open fun addChange(author: String, filename: String, changes: Long) {
            ensureFileWasInitialized(filename).changes += changes
            ensureAuthorWasInitialized(author).totalChanges += changes
        }

        open fun addComplexity(file: String, complexity: Long) {
            ensureFileWasInitialized(file).complexity += complexity
        }

        fun build(): Report {
            val authors = this.authors
                    .toList()
                    .map { it.second }
                    .sortedByDescending { it.totalChanges }

            val files = files
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

            val totalChanges = authors.sumBy { it.totalChanges.toInt() }

            // calculate percentage for each authors
            authors.forEach { it.percentChanges = it.totalChanges * 100.0 / totalChanges}

            return Report(totalLoc, authors, locPerLanguage, files)
        }

        private fun ensureFileWasInitialized(file: String): File {
            if (!files.containsKey(file)) {
                files[file] = File(filename = file)
            }

            return files[file]!!
        }

        private fun ensureAuthorWasInitialized(author: String): Author {
            if (!authors.containsKey(author)) {
                authors[author] = Author(name = author)
            }

            return authors[author]!!
        }
    }
}
