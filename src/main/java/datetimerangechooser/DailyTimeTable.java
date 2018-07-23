package datetimerangechooser;

import lombok.Getter;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class DailyTimeTable {

    @Getter
    private Map<LocalTime, String> reserveAppointments = new HashMap<>();
    @Getter
    private Set<LocalTime> freeAppointments = new HashSet<>();

    private LocalTime lastTriedAppointment;
    private int lastTriedRange;
    private String lastTryingUser;

    @Getter
    private boolean isSuccessfullLastAppointmentReserving;

    public DailyTimeTable() {
        initFreeAppointments();
    }

    private void initFreeAppointments() {
        IntStream.range(8, 16).forEach(hour -> freeAppointments.add(LocalTime.of(hour, 0)));
    }

    //TODO Boxed input parameters to an object after first commit!
    public void tryReserveAppointment(String userName, LocalTime localTime, int range) {
        lastTriedAppointment = localTime;
        lastTriedRange = range;
        lastTryingUser = userName;
        if (isAppointmentFree() && isRangeCorrect()) {
            reserveAppointmentRange(userName, range);
            isSuccessfullLastAppointmentReserving = true;
        } else {
            isSuccessfullLastAppointmentReserving = false;
        }
    }

    private boolean isAppointmentFree() {
        return freeAppointments.contains(lastTriedAppointment);
    }

    private boolean isRangeCorrect() {
        LocalTime rangeEndInTime = lastTriedAppointment.plusHours(lastTriedRange - 1);
        return freeAppointments.contains(rangeEndInTime) && rangeEndInTime.isBefore(LocalTime.of(16, 0));
    }

    private void reserveAppointmentRange(String userName, int range) {
        int from = lastTriedAppointment.getHour();
        int until = lastTriedAppointment.getHour() + range;
        IntStream.range(from, until).forEach(hour -> {
            LocalTime actualHour = LocalTime.of(hour, 0);
            reserveAppointments.put(actualHour, userName);
            freeAppointments.remove(actualHour);
        });
    }
}
