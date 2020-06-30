package it.fedeb.bookingtests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingResponse {

    @JsonProperty
    private String firstname;
    @JsonProperty
    private String lastname;
    @JsonProperty
    private int totalprice;
    @JsonProperty
    private boolean depositpaid;
    @JsonProperty
    private BookingDates bookingdates;
    @JsonProperty
    private String additionalneeds;

    @JsonCreator
    public BookingResponse(
            @JsonProperty("firstname") String firstname,
            @JsonProperty("lastname") String lastname,
            @JsonProperty("totalprice") int totalprice,
            @JsonProperty("depositpaid") boolean depositpaid,
            @JsonProperty("bookingdates") BookingDates bookingdates,
            @JsonProperty("additionalneeds") String additionalneeds){
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.bookingdates = bookingdates;
        this.additionalneeds = additionalneeds;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }
    public int getTotalprice(){
       return totalprice;
    }
    public BookingDates getBookingdates() {
        return bookingdates;
    }
}
