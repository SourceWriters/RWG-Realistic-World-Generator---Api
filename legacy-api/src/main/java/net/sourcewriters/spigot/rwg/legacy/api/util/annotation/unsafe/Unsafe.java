package net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This class marks a method, class or package as unsafe and subject to change.
 * 
 * It is not recommended to use any of those methods or classes as it could
 * change everytime. Some of the classes markes as @Unsafe are not finished or
 * even not implemented at all, so keep that in mind.
 * 
 * All infos that are contained in this annotation can be read on runtime
 * therefore you can retrieve the state of a specific class if this annotation
 * is applied.
 */
@Retention(RUNTIME)
@Target({
    TYPE,
    METHOD,
    CONSTRUCTOR,
    PACKAGE
})
public @interface Unsafe {

    public UnsafeStatus status() default UnsafeStatus.WORK_IN_PROGRESS;

    public boolean useable() default false;

}
