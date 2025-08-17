package lk.travel.travellion.uitl.regexProvider;

import jakarta.validation.constraints.Pattern;
import lk.travel.travellion.exceptions.ResourceNotFoundException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Provides functionality to extract and organize regex patterns and their associated messages
 * from annotated fields within a given object. This utility class processes annotations
 * such as {@code Pattern} and {@code RegexPattern} applied to fields and constructs a hierarchical
 * mapping of the field names to their corresponding regex patterns and messages.
 *
 * The method within this class scans the fields of the provided object's class using reflection,
 * retrieving the annotation data if the fields are annotated with compatible annotations.
 * Annotations supported are:
 * - {@code Pattern}
 * - {@code RegexPattern}
 *
 * Each matching annotation leads to the creation of an entry in the resulting map. The output map
 * is structured as a {@code HashMap}, where:
 * - The key is the name of the field in the provided class.
 * - The value is another map, containing:
 *   - A "regex" key with the regular expression string as the value.
 *   - A "message" key with the related descriptive message as the value.
 *
 * If any exception occurs during the processing, a {@code ResourceNotFoundException} is thrown.
 *
 * @param <T> The type of the object from which the regex patterns and messages are to be retrieved.
 */
public class RegexProvider {

    /**
     * Extracts regex patterns and messages from the annotations present on the fields of the provided object.
     * The method scans the class definition of the provided object using reflection, collects the data
     * from annotations such as {@code Pattern} and {@code RegexPattern}, and organizes them
     * into a hierarchical structure.
     *
     * @param <T> The type of the input object whose class will be scanned for annotations.
     * @param t   The object whose annotated field data is to be retrieved. This cannot be null.
     * @return A map where the keys represent field names and the values are maps containing
     *         two elements:
     *         - "regex": The regular expression string from the annotation.
     *         - "message": The message associated with the regex pattern.
     * @throws ResourceNotFoundException If the method encounters an error while accessing the object's
     *                                    fields or annotations, or if any other exception occurs during processing.
     */
    public static <T> HashMap<String, HashMap<String, String>> get(T t) {
        try {
            Class<?> aClass = t.getClass();
            HashMap<String, HashMap<String, String>> regex = new HashMap<>();

            for (Field field : aClass.getDeclaredFields()) {

                Annotation[] annotations = field.getDeclaredAnnotations();

                for (Annotation annotation : annotations) {

                    if (annotation instanceof Pattern myAnnotation) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("regex", myAnnotation.regexp());
                        map.put("message", myAnnotation.message());
                        regex.put(field.getName(), map);
                    }

                    if (annotation instanceof RegexPattern myAnnotation) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("regex", myAnnotation.reg());
                        map.put("message", myAnnotation.msg());
                        regex.put(field.getName(), map);
                    }
                }
            }
            return regex;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Resource not found : " + t.getClass().getName());
        }
    }
}
