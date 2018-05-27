package com.domnikl.forensics.vcs

import java.io.File

class Factory(private val config: List<VCS>) {
    fun build(path: File): VCS {
        for (system in config) {
            if (system.detect(path)) {
                return system
            }
        }

        throw UnknownVcsException("Could not detect used VCS in ${path.path})")
    }
}
