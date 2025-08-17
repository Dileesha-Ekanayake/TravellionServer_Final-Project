package lk.travel.travellion.uitl.regexProvider;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to define a regular expression (regex) pattern along with an optional descriptive message.
 * This annotation is used to specify and associate regex patterns with elements such as
 * fields or parameters for validation purposes.
 *
 * Elements:
 * - reg: Specifies the regex pattern as a string. Defaults to an empty string if not provided.
 * - msg: Specifies an optional message to describe or provide feedback for the regex pattern.
 *        Defaults to an empty string if not provided.
 *
 * This annotation is retained at runtime and can be accessed via reflection.
 *
 * Example Use Case:
 * The `RegexPattern` annotation is particularly useful for situations where a custom regex
 * validation mechanism is needed. It can be processed with classes like `RegexProvider`
 * to extract regex and message mappings for field validation.
 *
 * Annotation Usage:
 * - Accessed at runtime to dynamically retrieve regex patterns and messages.
 * - Can be applied at field or method level for validation or processing purposes.
 */
@Retention(RUNTIME)
public @interface RegexPattern{

    public String reg() default "";
    public String msg() default "";

}
