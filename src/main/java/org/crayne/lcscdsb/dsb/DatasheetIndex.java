package org.crayne.lcscdsb.dsb;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import static org.crayne.lcscdsb.logger.DSBLogger.*;

public class DatasheetIndex {

    private final int datasheetAmount;
    private final long totalCompressedFileSize;

    @NotNull
    private final Set<DatasheetZipFile> datasheetZips;

    public DatasheetIndex(@NotNull final Set<DatasheetZipFile> datasheetZips, final int datasheetAmount, final long totalCompressedFileSize) {
        this.datasheetAmount = datasheetAmount;
        this.totalCompressedFileSize = totalCompressedFileSize;
        this.datasheetZips = new HashSet<>(datasheetZips);
    }

    public long totalCompressedFileSize() {
        return totalCompressedFileSize;
    }

    public int datasheetAmount() {
        return datasheetAmount;
    }

    @NotNull
    public Set<DatasheetZipFile> datasheetZips() {
        return datasheetZips;
    }

    @NotNull
    public Map<ZipFile, Set<ZipEntry>> search(@NotNull final Function<ZipEntry, Boolean> search) {
        final Map<ZipFile, Set<ZipEntry>> finalResults = new HashMap<>();
        datasheetZips.forEach(datasheetZipFile -> {
            try (final ZipFile zipFile = datasheetZipFile.zipFile()) {
                final Set<ZipEntry> results = new HashSet<>();
                zipFile.stream().forEach(z -> {
                    if (search.apply(z)) results.add(z);
                });
                finalResults.put(zipFile, results);
            } catch (@NotNull final IOException e) {
                e.printStackTrace();
            }
        });
        return finalResults;
    }

    @NotNull
    public Map<ZipFile, Set<ZipEntry>> search(@NotNull final DatasheetSearchMode mode, @NotNull final String searchText) {
        return search(z -> mode.matches(z, searchText));
    }

    @NotNull
    public static Optional<DatasheetIndex> indexDatasheets() {
        info("Indexing...");

        final File datasheetsZipList = new File("datasheet.txt");
        if (!datasheetsZipList.isFile()) {
            fatal("Cannot index datasheet zip files; datasheet.txt is missing (" + datasheetsZipList.getAbsolutePath() + "), aborting.");
            return Optional.empty();
        }
        final Set<DatasheetZipFile> datasheetFiles = new HashSet<>();
        try {
            for (@NotNull final String line : Files.readAllLines(datasheetsZipList.toPath())) {
                final File file = new File(line);
                if (!file.exists()) {
                    error("Cannot load datasheet zip '" + line + "', file not found; Skipping this datasheet zip file.");
                    continue;
                }
                try (final ZipFile zipFile = new ZipFile(file)) {
                    datasheetFiles.add(new DatasheetZipFile(file, zipFile.size()));
                } catch (@NotNull final ZipException e) {
                    error("Cannot load datasheet zip '" + line + "': " + e.getMessage() + "; Skipping this datasheet zip file.");
                    e.printStackTrace();
                }
            }
        } catch (@NotNull final IOException e) {
            fatal("Cannot read datasheet.txt file: " + e.getMessage());
            e.printStackTrace(System.err);
            return Optional.empty();
        }
        final int datasheetAmount = datasheetFiles.stream().mapToInt(DatasheetZipFile::datasheetAmount).sum();
        final long totalCompressedFileSize = datasheetFiles.stream().mapToLong(DatasheetZipFile::fileSizeCompressed).sum();
        return Optional.of(new DatasheetIndex(datasheetFiles, datasheetAmount, totalCompressedFileSize));
    }


}
