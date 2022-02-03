package me.kansio.client.commands;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandData {
    String name();
    
    String[] aliases() default {};
    
    String description();
}
