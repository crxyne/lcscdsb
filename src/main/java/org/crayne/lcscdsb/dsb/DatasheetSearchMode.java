package org.crayne.lcscdsb.dsb;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

public enum DatasheetSearchMode {

    CONTAINING_STRING("s") {
        public boolean matches(@NotNull final ZipEntry entry, @NotNull final String searchText) {
            return entry.getName().contains(searchText);
        }
    },
    MATCHING_REGEX("r") {
        public boolean matches(@NotNull final ZipEntry entry, @NotNull final String searchText) {
            final Pattern pattern = Pattern.compile(searchText);
            return pattern.matcher(entry.getName()).matches();
        }
    },
    MATCHING_PART_NUMBER("p") {
        public boolean matches(@NotNull final ZipEntry entry, @NotNull final String searchText) {
            final Pattern pattern = Pattern.compile(".+" + searchText + ".+_C.+");
            return pattern.matcher(entry.getName()).matches();
        }
    },
    MATCHING_LCSC_PART_NUMBER("l") {
        public boolean matches(@NotNull final ZipEntry entry, @NotNull final String searchText) {
            final Pattern pattern = Pattern.compile(".+_C" + (searchText.toLowerCase().startsWith("c") ? searchText.substring(1) : searchText) + "\\.pdf");
            return pattern.matcher(entry.getName()).matches();
        }
    },
    EXACT_FILE_NAME("e") {
        public boolean matches(@NotNull final ZipEntry entry, @NotNull final String searchText) {
            return StringUtils.substringAfterLast(entry.getName(), "/").equals(searchText);
        }
    };

    @NotNull
    private final String abbreviation;

    DatasheetSearchMode(@NotNull final String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @NotNull
    public String abbreviation() {
        return abbreviation;
    }

    @NotNull
    public static Optional<DatasheetSearchMode> ofAbbreviation(@NotNull final String abbreviation) {
        return Arrays.stream(values()).filter(sm -> sm.abbreviation().equalsIgnoreCase(abbreviation)).findAny();
    }

    public boolean matches(@NotNull final ZipEntry entry, @NotNull final String searchText) {
        throw new UnsupportedOperationException("Matcher not implemented for search mode " + name() + ".");
    }

}
