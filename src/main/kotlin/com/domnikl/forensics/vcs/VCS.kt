package com.domnikl.forensics.vcs

import java.io.File
import java.time.LocalDateTime

interface VCS {
    fun detect(path: File): Boolean

    fun createReport(path: File, after: LocalDateTime): VcsReport
}
