package report

import java.io.BufferedWriter

class Report {
    private var map = mutableMapOf<String, Item>()

    fun addLoc(file: String, loc: Long) {
        ensureItemWasInitialized(file)
        map[file]!!.loc = loc
    }

    fun addChangeFreqs(file: String, changes: Long) {
        ensureItemWasInitialized(file)
        map[file]!!.changes = changes
    }

    private fun ensureItemWasInitialized(file: String) {
        if (!map.containsKey(file)) {
            map[file] = Item(filename = file)
        }
    }

    fun write(writer: BufferedWriter) {
        writer.write("filename,changes,loc\n")
        writer.flush()

        val l = map.toList().map { it.second }
                .filter { it.changes > 0 && it.filename.substring(0, 1) != "." }
                .sortedByDescending { it.loc }
                .sortedByDescending { it.changes }

        for (item in l) {
            writer.write("${item.filename},${item.changes},${item.loc}\n")
            writer.flush()
        }
    }

    data class Item(val filename: String, var loc: Long = 0, var changes: Long = 0)
}
