package util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class RuberDriverLoggerFormatter extends Formatter {

    private final String format = "%s %s [%s] %s%s\n";

    public synchronized String format(LogRecord record) {

        String message = formatMessage(record);
        String throwable = "";
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = sw.toString();
        }
        return String.format(format, //
                LocalDate.now().toString(), //
                LocalTime.now().toString(), //
                record.getLevel().getName() + " : " + record.getLevel().getLocalizedName(), //
                message, //
                throwable);
    }
}