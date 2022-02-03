package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import java.util.function.*;
import java.util.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

@Debug.Renderer(text = "this.debuggerString()", childrenArray = "this.children().toArray()", hasChildren = "!this.children().isEmpty()")
public abstract class AbstractComponent implements Component
{
    private static final Predicate<Component> NOT_EMPTY;
    protected final List<Component> children;
    protected final Style style;
    
    protected AbstractComponent(@NotNull final List<? extends ComponentLike> children, @NotNull final Style style) {
        this.children = ComponentLike.asComponents(children, AbstractComponent.NOT_EMPTY);
        this.style = style;
    }
    
    @NotNull
    @Override
    public final List<Component> children() {
        return this.children;
    }
    
    @NotNull
    @Override
    public final Style style() {
        return this.style;
    }
    
    @NotNull
    @Override
    public Component replaceText(@NotNull final Consumer<TextReplacementConfig.Builder> configurer) {
        Objects.requireNonNull(configurer, "configurer");
        return this.replaceText(Buildable.configureAndBuild(TextReplacementConfig.builder(), configurer));
    }
    
    @NotNull
    @Override
    public Component replaceText(@NotNull final TextReplacementConfig config) {
        Objects.requireNonNull(config, "replacement");
        if (!(config instanceof TextReplacementConfigImpl)) {
            throw new IllegalArgumentException("Provided replacement was a custom TextReplacementConfig implementation, which is not supported.");
        }
        return TextReplacementRenderer.INSTANCE.render((Component)this, ((TextReplacementConfigImpl)config).createState());
    }
    
    @NotNull
    @Override
    public Component compact() {
        return ComponentCompaction.compact(this, null);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbstractComponent)) {
            return false;
        }
        final AbstractComponent that = (AbstractComponent)other;
        return Objects.equals(this.children, that.children) && Objects.equals(this.style, that.style);
    }
    
    @Override
    public int hashCode() {
        int result = this.children.hashCode();
        result = 31 * result + this.style.hashCode();
        return result;
    }
    
    private String debuggerString() {
        return StringExaminer.simpleEscaping().examine(this.examinableName(), this.examinablePropertiesWithoutChildren());
    }
    
    protected Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
        return Stream.of(ExaminableProperty.of("style", this.style));
    }
    
    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(this.examinablePropertiesWithoutChildren(), (Stream<? extends ExaminableProperty>)Stream.of((T)ExaminableProperty.of("children", this.children)));
    }
    
    @Override
    public String toString() {
        return this.examine((Examiner<String>)StringExaminer.simpleEscaping());
    }
    
    static {
        NOT_EMPTY = (component -> component != Component.empty());
    }
}
