package org.crayne.lcscdsb;

import org.apache.commons.lang3.StringUtils;
import org.crayne.lcscdsb.dsb.DatasheetIndex;
import org.crayne.lcscdsb.dsb.DatasheetSearchMode;
import org.crayne.lcscdsb.dsb.DatasheetUtil;
import org.crayne.lcscdsb.logger.DSBLogger;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.crayne.lcscdsb.logger.DSBLogger.*;

public class Main {

    @NotNull
    private static DatasheetIndex indexFiles() {
        final long millisBegin = System.currentTimeMillis();
        final Optional<DatasheetIndex> optIndex = DatasheetIndex.indexDatasheets();
        final long millisEnd = System.currentTimeMillis();
        if (optIndex.isEmpty()) System.exit(1);

        final DatasheetIndex index = optIndex.get();
        success("Indexed all zip files in " + (millisEnd - millisBegin) + "ms. Found " + index.datasheetAmount() + " datasheets with total file size of " + DatasheetUtil.formatFileSize(index.totalCompressedFileSize()));
        return index;
    }

    @NotNull
    private static Map<ZipFile, Set<ZipEntry>> searchFiles(@NotNull final DatasheetIndex index, @NotNull final String searchText, @NotNull final String searchModeStr) {
        final Optional<DatasheetSearchMode> searchMode = DatasheetSearchMode.ofAbbreviation(searchModeStr);

        if (searchMode.isEmpty()) {
            fatal("Unknown search mode '" + searchModeStr + "'; Aborting.");
            System.exit(1);
        }
        final boolean foundAnything;
        info("Searching for '" + searchText + "' using mode " + searchMode.get().name().toLowerCase().replace("_", " ") + "...");
        final long millisBegin = System.currentTimeMillis();

        final Map<ZipFile, Set<ZipEntry>> results = index.search(searchMode.get(), searchText);
        final long millisEnd = System.currentTimeMillis();

        final Set<ZipEntry> entries = results.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
        entries.stream().map(ZipEntry::getName).forEach(DSBLogger::debug);
        foundAnything = !entries.isEmpty();
        if (!foundAnything) {
            error("Could not find component '" + searchText + "'.");
            System.exit(1);
        }
        info("Found " + entries.size() + " results in " + (millisEnd - millisBegin) + "ms.");
        return results;
    }

    @NotNull
    private static File exportFiles(@NotNull final Map<ZipFile, Set<ZipEntry>> results) {
        info("Exporting only one result to pdf file.");

        final var firstFoundEntryPair = results.entrySet().stream().filter(s -> !s.getValue().isEmpty()).findFirst().orElseThrow(IllegalStateException::new);
        final ZipEntry firstFoundEntry = firstFoundEntryPair.getValue().stream().findFirst().orElseThrow(IllegalStateException::new);

        final File exportTo = new File(StringUtils.substringAfterLast(firstFoundEntry.getName(), "/"));

        try (@NotNull final ZipFile zipFile = new ZipFile(new File(firstFoundEntryPair.getKey().getName()))) {
            final InputStream inputStream = zipFile.getInputStream(firstFoundEntry);
            Files.copy(inputStream, exportTo.toPath(), StandardCopyOption.REPLACE_EXISTING);
            exportTo.deleteOnExit();
        } catch (@NotNull final IOException e) {
            fatal("Could not export datasheet: " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        }
        return exportTo;
    }

    private static void openDatasheet(@NotNull final File exported) {
        success("Finished. Datasheet location: " + exported.getAbsolutePath());
        info("Opening datasheet...");
        try {
            Desktop.getDesktop().open(exported);
        } catch (final IOException e) {
            fatal("Could not open datasheet: " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public static void main(@NotNull final String... args) throws IOException {
        lcsc("Initializing LCSC Datasheet Browser");

        if (args.length < 1 || args.length > 2) {
            error("Expected at least one argument, at most 2 arguments.");
            lcsc("Usage: dsb <mode> '<search>'");
            lcsc("Another usage: dsb '<search>'");
            lcsc("Possible modes: s = containing string, r = match regex, p = match part number, l = match lcsc number, e = match exact filename");
            lcsc("The default mode is 's'.");
            System.exit(0);
        }
        final DatasheetIndex index = indexFiles();

        final String searchText = args.length == 1 ? args[0] : args[1];
        final String searchModeStr = args.length == 1 ? "s" : args[0];

        final var results = searchFiles(index, searchText, searchModeStr);
        final File exported = exportFiles(results);
        openDatasheet(exported);

        info("Press enter to exit.");
        //noinspection ResultOfMethodCallIgnored
        System.in.read();
    }

}