package edu.school21.service;

import com.google.auto.service.AutoService;

import edu.school21.annotations.HtmlForm;
import edu.school21.annotations.HtmlInput;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.lang.annotation.Annotation;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("edu.school21.annotations.HtmlForm")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);

        for (Element element : annotatedElements) {
            List<? extends Element> elements = element.getEnclosedElements();
            List<Annotation> fields = new ArrayList<>();
            for (Element element1 : elements) {
                HtmlInput field = element1.getAnnotation(HtmlInput.class);
                if (field != null)
                    fields.add(field);
            }
            createResultFile(element.getAnnotation(HtmlForm.class), fields);
        }
        return true;
    }

    public void createResultFile(HtmlForm form, List<Annotation> fields) {
        try {
            FileObject fileObject = processingEnv.getFiler()
                    .createResource(StandardLocation.CLASS_OUTPUT, "", form.fileName());
            try (PrintWriter printWriter = new PrintWriter(fileObject.openWriter())) {
                String formOpenTag = "<form action = \"" + form.action() + "\" method = \"" + form.method() + "\">";
                printWriter.println(formOpenTag);
                for (Annotation field : fields) {
                    HtmlInput input = (HtmlInput) field;
                    String inputToWrite = "<input type = \"" + input.type() + "\" " +
                            "name = \"" + input.name() + "\" " +
                            "placeholder = \"" + input.placeholder() + "\">";
                    printWriter.println(inputToWrite);
                }
                printWriter.println("<input type = \"submit\" value = \"Send\">");
                printWriter.println("</form>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}