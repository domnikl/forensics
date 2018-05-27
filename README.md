# forensics

Runs analyses over software projects.

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

## credits

Forensics is mostly based on [Adam Tornhill](https://twitter.com/AdamTornhill)'s ideas presented in his book [Your code as a crime scene](https://pragprog.com/book/atcrime/your-code-as-a-crime-scene).
