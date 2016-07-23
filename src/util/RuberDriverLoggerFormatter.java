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

        String date = LocalDate.now().toString();
        String time = LocalTime.now().toString();
        String type = record.getLevel().getName();
        String msg = formatMessage(record);

        String throwable = "";
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = sw.toString();
        }

        return String.format(format, date, time, type, msg, throwable);
    }
}