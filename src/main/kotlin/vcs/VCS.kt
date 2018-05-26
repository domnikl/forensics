package vcs

import java.io.File

interface VCS {
    fun detect(path: File): Boolean

    fun createReport(path: File): VcsReport
}
