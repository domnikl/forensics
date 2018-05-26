package vcs

import shell.ShellCommand
import java.io.File

class Factory {
    fun build(path: File): VCS {
        val availableSystems = listOf(
                Git(ShellCommand(File("git")))
        )

        for (system in availableSystems) {
            if (system.detect(path)) {
                return system
            }
        }

        throw UnknownVcsException("Could not detect used VCS in ${path.path})")
    }
}
