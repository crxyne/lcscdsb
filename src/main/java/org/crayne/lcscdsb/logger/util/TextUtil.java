package org.crayne.lcscdsb.logger.util;

import org.bes.stain.text.AnsiColor;
import org.bes.stain.text.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class TextUtil {

    @NotNull
    public static TextComponent text(@NotNull final Color color, @NotNull final String text) {
        return TextComponent.of(color, text);
    }

    @NotNull
    public static TextComponent text(@NotNull final AnsiColor color, @NotNull final String text) {
        return TextComponent.of(color, text);
    }

    @NotNull
    public static String prependZeros(final int number, final int amountOfZeros) {
        if (amountOfZeros > 9 || amountOfZeros <= 0) throw new IllegalArgumentException("Amount of zeros must be above 0 and less than or equal to 9.");
        return String.format("%0" + amountOfZeros + "d", number);
    }

}