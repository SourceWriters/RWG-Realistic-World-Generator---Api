package net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This class marks a method or constructor to be caller sensitive
 * 
 * That means that the outcome of the call to such a method could change
 * depending on who is calling the object
 */
@Retention(SOURCE)
@Target({
    METHOD,
    CONSTRUCTOR
})
public @interface CallerSensitive {

}
