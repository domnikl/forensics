package loc

class LocReport {
    private var map = mutableMapOf<String, Long>()

    fun addFile(filename: String, loc: Long) {
        map[filename] = loc
    }

    fun size(): Int {
        return map.size
    }
}
