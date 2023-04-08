package org.crayne.lcscdsb.dsb;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class DatasheetUtil {

    private static final long B = 1L;
    private static final long KiB = B << 10;
    private static final long MiB = KiB << 10;
    private static final long GiB = MiB << 10;
    private static final long TiB = GiB << 10;
    private static final long PiB = TiB << 10;
    private static final long EiB = PiB << 10;

    @NotNull
    private static DecimalFormat byteSizeFormat = new DecimalFormat("#.##");

    @NotNull
    public static String formatByteSize(final long sizeBytes, final long newSizeUnit, @NotNull final String unitName) {
        return byteSizeFormat.format((double) sizeBytes / newSizeUnit) + " " + unitName;
    }

    @NotNull
    public static String formatFileSize(final long sizeBytes) {
        if (sizeBytes >= EiB) return formatByteSize(sizeBytes, EiB, "EiB");
        if (sizeBytes >= PiB) return formatByteSize(sizeBytes, PiB, "PiB");
        if (sizeBytes >= TiB) return formatByteSize(sizeBytes, TiB, "TiB");
        if (sizeBytes >= GiB) return formatByteSize(sizeBytes, GiB, "GiB");
        if (sizeBytes >= MiB) return formatByteSize(sizeBytes, MiB, "MiB");
        if (sizeBytes >= KiB) return formatByteSize(sizeBytes, KiB, "KiB");
        return formatByteSize(sizeBytes, B, "B");
    }

    private DatasheetUtil() {

    }


}
