package com.viaversion.viaversion.libs.kyori.adventure.text.event;

import java.net.*;
import java.util.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import org.jetbrains.annotations.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;

public final class ClickEvent implements Examinable, StyleBuilderApplicable
{
    private final Action action;
    private final String value;
    
    @NotNull
    public static ClickEvent openUrl(@NotNull final String url) {
        return new ClickEvent(Action.OPEN_URL, url);
    }
    
    @NotNull
    public static ClickEvent openUrl(@NotNull final URL url) {
        return openUrl(url.toExternalForm());
    }
    
    @NotNull
    public static ClickEvent openFile(@NotNull final String file) {
        return new ClickEvent(Action.OPEN_FILE, file);
    }
    
    @NotNull
    public static ClickEvent runCommand(@NotNull final String command) {
        return new ClickEvent(Action.RUN_COMMAND, command);
    }
    
    @NotNull
    public static ClickEvent suggestCommand(@NotNull final String command) {
        return new ClickEvent(Action.SUGGEST_COMMAND, command);
    }
    
    @NotNull
    public static ClickEvent changePage(@NotNull final String page) {
        return new ClickEvent(Action.CHANGE_PAGE, page);
    }
    
    @NotNull
    public static ClickEvent changePage(final int page) {
        return changePage(String.valueOf(page));
    }
    
    @NotNull
    public static ClickEvent copyToClipboard(@NotNull final String text) {
        return new ClickEvent(Action.COPY_TO_CLIPBOARD, text);
    }
    
    @NotNull
    public static ClickEvent clickEvent(@NotNull final Action action, @NotNull final String value) {
        return new ClickEvent(action, value);
    }
    
    private ClickEvent(@NotNull final Action action, @NotNull final String value) {
        this.action = Objects.requireNonNull(action, "action");
        this.value = Objects.requireNonNull(value, "value");
    }
    
    @NotNull
    public Action action() {
        return this.action;
    }
    
    @NotNull
    public String value() {
        return this.value;
    }
    
    @Override
    public void styleApply(final Style.Builder style) {
        style.clickEvent(this);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        final ClickEvent that = (ClickEvent)other;
        return this.action == that.action && Objects.equals(this.value, that.value);
    }
    
    @Override
    public int hashCode() {
        int result = this.action.hashCode();
        result = 31 * result + this.value.hashCode();
        return result;
    }
    
    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((ExaminableProperty[])new ExaminableProperty[] { ExaminableProperty.of("action", this.action), ExaminableProperty.of("value", this.value) });
    }
    
    @Override
    public String toString() {
        return this.examine((Examiner<String>)StringExaminer.simpleEscaping());
    }
    
    public enum Action
    {
        OPEN_URL("open_url", true), 
        OPEN_FILE("open_file", false), 
        RUN_COMMAND("run_command", true), 
        SUGGEST_COMMAND("suggest_command", true), 
        CHANGE_PAGE("change_page", true), 
        COPY_TO_CLIPBOARD("copy_to_clipboard", true);
        
        public static final Index<String, Action> NAMES;
        private final String name;
        private final boolean readable;
        
        private Action(final String name, final boolean readable) {
            this.name = name;
            this.readable = readable;
        }
        
        public boolean readable() {
            return this.readable;
        }
        
        @NotNull
        @Override
        public String toString() {
            return this.name;
        }
        
        static {
            NAMES = Index.create(Action.class, constant -> constant.name);
        }
    }
}
