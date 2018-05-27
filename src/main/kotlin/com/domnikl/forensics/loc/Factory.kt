package com.domnikl.forensics.loc

import com.domnikl.forensics.shell.ShellCommand
import java.io.File

class Factory {
    fun build(): LocAdapter {
        //TODO("check if cloc is installed!")

        return Cloc(ShellCommand(File("cloc")))
    }
}
