import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Date;

public class BookingDates {

    @JsonProperty
    private String checkin;
    @JsonProperty
    private String checkout;

    @JsonCreator
    public BookingDates(@JsonProperty("checkin") String checkin, @JsonProperty("checkout") String checkout){
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public String getCheckin() {
        return checkin;
    }

    public String getCheckout() {
        return checkout;
    }
}
