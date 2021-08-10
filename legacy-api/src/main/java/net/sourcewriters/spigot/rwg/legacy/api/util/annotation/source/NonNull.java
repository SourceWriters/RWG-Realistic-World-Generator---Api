package net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This class marks a method or parameter which can't be null.
 */
@Retention(SOURCE)
@Target({
    TYPE,
    METHOD,
    PARAMETER
})
public @interface NonNull {

}
