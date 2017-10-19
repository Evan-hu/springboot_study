package com.test.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Evan on 2017/10/9.
 */
public class AnnotationParsing {

    public static void main(String[] args) {
        try {
            for (Method method : AnnotationParsing.class.getClassLoader()
                    .loadClass(("com.test.annotation.AnnotationExample")).getMethods()) {
                if (method.isAnnotationPresent(com.test.annotation.MethodInfo.class)) {
                    for (Annotation annotation : method.getDeclaredAnnotations()) {
                        System.out.println("Annotation in method" + method.getName() + " anno" + annotation);
                    }
                    MethodInfo methodAnno = method.getAnnotation(MethodInfo.class);
                    System.out.println("methodInfo author " + methodAnno.author());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
