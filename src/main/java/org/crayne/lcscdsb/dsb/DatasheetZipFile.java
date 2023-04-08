package org.crayne.lcscdsb.dsb;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

public class DatasheetZipFile {

    @NotNull
    private final File file;
    private final int datasheetAmount;

    public DatasheetZipFile(@NotNull final File file, final int datasheetAmount) {
        this.file = file;
        this.datasheetAmount = datasheetAmount;
    }

    public int datasheetAmount() {
        return datasheetAmount;
    }

    @NotNull
    public File file() {
        return file;
    }

    public long fileSizeCompressed() {
        return file.length();
    }

    public ZipFile zipFile() throws IOException {
        return new ZipFile(file);
    }

}
