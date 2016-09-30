package com.ifedorenko.sample.proc.test;

import static io.takari.maven.testing.TestMavenRuntime.newParameter;
import static io.takari.maven.testing.TestResources.assertFilesPresent;

import java.io.File;

import org.apache.maven.project.MavenProject;
import org.junit.Rule;
import org.junit.Test;

import io.takari.maven.testing.TestMavenRuntime;
import io.takari.maven.testing.TestResources;

public class SampleProcessorTest {

  @Rule
  public final TestResources resources = new TestResources();

  @Rule
  public final TestMavenRuntime maven = new TestMavenRuntime();

  @Test
  public void testBasic() throws Exception {

    // this creates a temporary copy of src/test/projects/basic test project
    File basedir = resources.getBasedir("basic");

    // create MavenProject model for the test project
    MavenProject project = maven.readMavenProject(basedir);

    // add annotation processor to the test project dependencies (i.e. classpath)
    maven.newDependency(new File("target/classes").getCanonicalFile()).addTo(project);

    // run java compiler with annotation processing enabled
    maven.executeMojo(project, "compile" //
        , newParameter("compilerId", "jdt") // can test with javac too
        , newParameter("proc", "proc") // annotation processing is off by default, enable it
    );

    // generated sources and compiled classes location
    File generatedSources = new File(basedir, "target/generated-sources/annotations");
    File compiledClasses = new File(basedir, "target/classes");

    // assert expected java source was generated and compiled during the test
    assertFilesPresent(generatedSources,
        "com/ifedorenko/sample/proc/test/basic/GeneratedBasicAnnotatedClass.java");
    assertFilesPresent(compiledClasses,
        "com/ifedorenko/sample/proc/test/basic/GeneratedBasicAnnotatedClass.class");
  }

}
