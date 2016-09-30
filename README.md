Testing and debugging java annotation processors
===================================================


## Project overview (excuse my clumsy ascii diagram)

```
sample-annotation-processor/
  |-- src/main/java/com/ifedorenko/sample/proc/
  |  |-- SampleAnnotation.java                       sample @annotation
  |  |-- SampleProcessor.java                        sample annotation processor
  |-- src/test/java/com/ifedorenko/sample/proc/test/
  |  |-- SampleProcessorTest.java                    annotation processor unit test
  |-- src/test/projects/basic/                       test project
  |-- [javac] Compile ... .launch                    javac launch configuration
  |-- pom.xml                                        maven pom.xml
  |-- README.md                                      this readme

```

## Prerequisites

1. Eclipse 4.6.1 SDK from [download.eclipse.org](http://download.eclipse.org/eclipse/downloads/).
2. Takari "TEAM" feature installed from [repository.takari.io](https://repository.takari.io/content/sites/m2e.extras/takari-team/0.1.0/N/LATEST/).

## Import sample project in Eclipse

1. From command line execute `git clone https://github.com/ifedorenko/com.ifedorenko.sample-annotation-processor.git`
2. From Eclipse, Import, Existing Maven Projects, select location of the sample project
   * this may need to "download the Internet", which takes time
3. Optionally, import test project as Existing Project (i.e. it is not Maven) 

At this point annotation processor project should be imported and compiled in Eclipse. If you see error markers on the project, something went wrong.

## Running and debugging annotation processor unit test

1. Select `SampleProcessorTest` in Package Explorer
2. Right-click on the test, Run As (or Debug As), Maven JUnit Test

Expected behaviour

* the unit test is expected to run successfully
* breakpoints in the annotation processor are expected to work
* changes to the annotation processor or the test project are expected to take immediate effect (need to rerun the test, obviously)

## Debugging direct `javac` execution

1. Right-click on `[javac] Compile sample-annotation-processor-basic.launch` launch configuration file in Package Explorer, Run As (or Debug As), `[javac] Compile sample-annotation-processor-basic`
   * the launch file assumes jdk is installed in `/opt/java`, you need to edit bootstrap classpath to use alternative location

Expected behaviour

* the compilation is expected to succeed
* breakpoints in the annotation processor are expected to work
* changes to the annotation processor or the test project are expected to take immediate effect (need to relaunch the compiler)
