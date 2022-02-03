package me.kansio.client.modules.api;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface ModuleData {
    ModuleCategory category();
    
    String description();
    
    int bind() default 0;
    
    String name();
}
