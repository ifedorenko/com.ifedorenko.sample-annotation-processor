package com.ifedorenko.sample.proc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;

@SupportedAnnotationTypes("com.ifedorenko.sample.proc.SampleAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class SampleProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    for (Element element : roundEnv.getElementsAnnotatedWith(SampleAnnotation.class)) {
      try {
        TypeElement cls = (TypeElement) element;
        SampleAnnotation ann = cls.getAnnotation(SampleAnnotation.class);
        PackageElement pkg = (PackageElement) cls.getEnclosingElement();
        String clsSimpleName = ann.prefix() + cls.getSimpleName();
        String pkgName = pkg.getQualifiedName().toString();
        String clsQualifiedName = pkgName + "." + clsSimpleName;
        FileObject sourceFile = processingEnv.getFiler().createSourceFile(clsQualifiedName, cls);
        try (BufferedWriter w = new BufferedWriter(sourceFile.openWriter())) {
          w.append("package ").append(pkgName).append(";");
          w.newLine();
          w.append("public class ").append(clsSimpleName);
          w.append("{}");
        }
      } catch (IOException e) {
        e.printStackTrace();
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
      }
    }
    return false; // not "claimed" so multiple processors can be tested
  }

}
