package org.crayne.lcscdsb.logger;

import org.bes.stain.text.AnsiColor;
import org.bes.stain.text.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

import static org.crayne.lcscdsb.logger.util.TextUtil.prependZeros;
import static org.crayne.lcscdsb.logger.util.TextUtil.text;

public class DSBLogger {

    private DSBLogger() {

    }

    @NotNull
    public static String timeAndDatePrefix() {
        final LocalDateTime now = LocalDateTime.now();
        final String dateString = "" + now.getMonthValue() + "/" + now.getDayOfMonth() + "/" + now.getYear();
        final String timeString =
                        prependZeros(now.getHour(),   2)
                + ":" + prependZeros(now.getMinute(), 2)
                + ":" + prependZeros(now.getSecond(), 2);

        return "[" + dateString + " " + timeString + "] ";
    }

    public static void log(@NotNull final String text, @NotNull final DSBLogLevel level) {
        final TextComponent logPrefix = text(DSBLogLevel.INFO.color(), timeAndDatePrefix())
                .append(level.prefix());

        final TextComponent logText = logPrefix
                .append(text)
                .append(text(AnsiColor.RESET_ANSI_COLOR, ""));

        System.out.println(logText);
    }

    public static void info(@NotNull final String text) {
        log(text, DSBLogLevel.INFO);
    }

    public static void debug(@NotNull final String text) {
        log(text, DSBLogLevel.DEBUG);
    }

    public static void success(@NotNull final String text) {
        log(text, DSBLogLevel.SUCCESS);
    }

    public static void warn(@NotNull final String text) {
        log(text, DSBLogLevel.WARN);
    }

    public static void error(@NotNull final String text) {
        log(text, DSBLogLevel.ERROR);
    }

    public static void fatal(@NotNull final String text) {
        log(text, DSBLogLevel.FATAL);
    }

    public static void lcsc(@NotNull final String text) {
        log(text, DSBLogLevel.LCSC);
    }


}
