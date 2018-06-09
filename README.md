# forensics

Extract simple yet effective metrics from software projects to be exported and evaluated in a simple CSV format.

## runtime dependencies

forensics depends on these tools installed and in your `$PATH` to run:

* [git](https://git-scm.com/)
* [cloc](https://github.com/AlDanial/cloc)

## build & run

To build a JAR file using Maven, run the following command:

```shell
mvn clean install
```

Now you can run the JAR using the following command:

```shell
cd /path/to/your/project/to/analyze
java -jar target/forensics-*-jar-with-dependencies.jar
```

## metrics

As forensics is not yet stable, the used CSV file format is subject to change.

The exported CSV file conforms to the following format:

```csv
filename,language,changes,complexity,loc,(total_loc = 699, languages = (Kotlin: 592 (85%), Maven: 88 (13%), Markdown: 19 (3%)), authors = (Dominik Liebler: 5671 (100%)))
src/main/kotlin/com/domnikl/forensics/report/Report.kt,Kotlin,348,852,73
src/main/kotlin/com/domnikl/forensics/vcs/Git.kt,Kotlin,107,672,68
pom.xml,Maven,95,1322,88
src/test/kotlin/com/domnikl/forensics/vcs/GitTest.kt,Kotlin,86,196,41
src/main/kotlin/com/domnikl/forensics/shell/ShellCommand.kt,Kotlin,74,312,36
src/main/kotlin/com/domnikl/forensics/loc/Cloc.kt,Kotlin,68,368,45
src/main/kotlin/com/domnikl/forensics/vcs/VcsReport.kt,Kotlin,66,76,18
src/main/kotlin/com/domnikl/forensics/CLI.kt,Kotlin,59,200,38
src/test/kotlin/com/domnikl/forensics/loc/ClocTest.kt,Kotlin,56,152,35
src/main/kotlin/com/domnikl/forensics/complexity/Scanner.kt,Kotlin,52,300,35
src/main/kotlin/com/domnikl/forensics/loc/Factory.kt,Kotlin,43,156,28
src/test/kotlin/com/domnikl/forensics/vcs/VcsReportTest.kt,Kotlin,43,76,19
src/test/kotlin/com/domnikl/forensics/loc/FactoryTest.kt,Kotlin,33,92,27
src/test/kotlin/com/domnikl/forensics/vcs/FactoryTest.kt,Kotlin,32,104,26
README.md,Markdown,29,0,19
src/main/kotlin/com/domnikl/forensics/vcs/Factory.kt,Kotlin,29,72,12
src/main/kotlin/com/domnikl/forensics/report/writer/CSV.kt,Kotlin,27,120,21
src/main/kotlin/com/domnikl/forensics/loc/LocReport.kt,Kotlin,25,84,18
src/test/kotlin/com/domnikl/forensics/loc/LocReportTest.kt,Kotlin,25,60,16
src/main/kotlin/com/domnikl/forensics/complexity/Report.kt,Kotlin,18,56,14
src/main/kotlin/com/domnikl/forensics/vcs/VCS.kt,Kotlin,12,8,7
src/main/kotlin/com/domnikl/forensics/loc/LocAdapter.kt,Kotlin,7,4,5
src/main/kotlin/com/domnikl/forensics/report/Reportable.kt,Kotlin,7,4,4
src/main/kotlin/com/domnikl/forensics/report/Writer.kt,Kotlin,5,4,4
src/main/kotlin/com/domnikl/forensics/vcs/UnknownVcsException.kt,Kotlin,3,0,2
```

* **language**: detected programming language or config file format
* **changes**: # of all commits targeting this file
* **complexity**: a simple complexity metric to be able to sort results in terms of complexity
* **loc**: lines of code in this file

The header also includes project-level metrics. 

## credits

Forensics is mostly based on [Adam Tornhill](https://twitter.com/AdamTornhill)'s ideas presented in his book [Your code as a crime scene](https://pragprog.com/book/atcrime/your-code-as-a-crime-scene).
