package com.domnikl.forensics.loc

import java.io.File

interface LocAdapter {
    fun createReport(path: File): LocReport
}
