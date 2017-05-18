package cz.schutzpetr.stock.server.command.annotation;

import cz.schutzpetr.stock.server.command.utils.CommandType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Petr Schutz on 23.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String command();

    String[] aliases();

    CommandType type();

    int min() default 0;

    int max() default -1;

    String description();

    String usage() default "";

}
