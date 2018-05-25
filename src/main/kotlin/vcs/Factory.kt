package vcs

class Factory {
    private val supportedSystems = listOf(GitAdapter::class)

    fun build(path: String): VCS {
        for (system in supportedSystems) {
            TODO("check if system to be chosen")
        }

        return GitAdapter(path)
    }
}
