package org.example;

import org.bes.stain.text.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Main {

    public static void main(@NotNull final String... args) {
        System.out.println(TextComponent.of(Color.RED, "hi"));
    }

}