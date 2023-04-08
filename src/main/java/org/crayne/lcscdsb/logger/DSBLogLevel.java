package org.crayne.lcscdsb.logger;

import org.bes.stain.text.AnsiColor;
import org.bes.stain.text.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public enum DSBLogLevel {

    INFO(Color.LIGHT_GRAY),
    DEBUG(Color.MAGENTA),
    SUCCESS(Color.GREEN),
    WARN(Color.YELLOW),
    ERROR(Color.RED),
    FATAL(Color.RED.darker()),

    LCSC(Color.CYAN) {
        @NotNull
        public TextComponent prefix() {
            return TextComponent.of(Color.CYAN, "");
        }
    };

    private final Color color;

    DSBLogLevel(@NotNull final Color color) {
        this.color = color;
    }

    @NotNull
    public Color color() {
        return color;
    }

    @NotNull
    public TextComponent prefix() {
        return TextComponent.of(color, "[" + name() + "]")
                .append(TextComponent.of(AnsiColor.RESET_ANSI_COLOR, ": "));
    }

}
