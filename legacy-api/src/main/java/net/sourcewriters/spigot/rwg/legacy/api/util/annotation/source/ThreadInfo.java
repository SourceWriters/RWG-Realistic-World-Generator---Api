package net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(SOURCE)
@Target(METHOD)
public @interface ThreadInfo {

    boolean sync();

}
