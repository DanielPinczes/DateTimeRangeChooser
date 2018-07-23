package datetimerangechooser;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DailyTimeTableTest {

    private DailyTimeTable wednesday = new DailyTimeTable();
    ;
    private Set<LocalTime> freeAppointmentSet = new HashSet<>();

    @Before
    public void fillTimeTable() {
        wednesday.tryReserveAppointment("user1", LocalTime.of(8, 0), 4);
        wednesday.tryReserveAppointment("user2", LocalTime.of(14, 0), 2);
        freeAppointmentSet.add(LocalTime.of(12, 0));
        freeAppointmentSet.add(LocalTime.of(13, 0));
    }

    @Test
    public void testDailyTimeTable() {
        assertEquals(freeAppointmentSet, wednesday.getFreeAppointments());
        wednesday.tryReserveAppointment("user3", LocalTime.of(8, 0), 4);
        assertFalse(wednesday.isSuccessfullLastAppointmentReserving());
        wednesday.tryReserveAppointment("user3", LocalTime.of(12, 0), 2);
        assertTrue(wednesday.isSuccessfullLastAppointmentReserving());
        assertEquals(0, wednesday.getFreeAppointments().size());
    }

}
