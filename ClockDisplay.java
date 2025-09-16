import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * ClockDisplay menampilan jam untuk
 * jam 24 jam dengan jam, menit, detik, dan tanggal.
 *
 * 
 * 
 */

public class ClockDisplay {
    private NumberDisplay hours;
    private NumberDisplay minutes;
    private NumberDisplay seconds;
    private LocalDate date; 
    private String displayString; 

    public ClockDisplay() {
        hours = new NumberDisplay(24);
        minutes = new NumberDisplay(60);
        seconds = new NumberDisplay(60);

        LocalDateTime now = LocalDateTime.now();
        hours.setValue(now.getHour());
        minutes.setValue(now.getMinute());
        seconds.setValue(now.getSecond());
        date = now.toLocalDate();

        updateDisplay();
    }

    public void timeTick() {
        seconds.increment();
        if (seconds.getValue() == 0) {
            minutes.increment();
            if (minutes.getValue() == 0) {
                hours.increment();
                if (hours.getValue() == 0) {
                    date = date.plusDays(1);
                }
            }
        }
        updateDisplay();
    }

    public void setTime(int hour, int minute, int second) {
        hours.setValue(hour);
        minutes.setValue(minute);
        seconds.setValue(second);
        updateDisplay();
    }

    public String getTime() {
        return displayString;
    }

    public String getDate() {
        int d = date.getDayOfMonth();
        int m = date.getMonthValue();
        int y = date.getYear();
        return String.format("%02d-%02d-%04d", d, m, y);
    }

    private void updateDisplay() {
        displayString = hours.getDisplayValue() + ":" +
                        minutes.getDisplayValue() + ":" +
                        seconds.getDisplayValue();
    }
}
