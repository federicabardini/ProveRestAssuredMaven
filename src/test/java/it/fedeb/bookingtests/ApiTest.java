package it.fedeb.bookingtests;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.exparity.hamcrest.date.LocalDateMatchers;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTest {

    @Test
    public void myFirstRaTest() {
        assertThat(RestAssured.config(), instanceOf(RestAssuredConfig.class));
    }

    @Test
    public void verifyStatusCodeSimpleRequest() {
        Response response = given().get("https://restful-booker.herokuapp.com/ping/");
        int statusCode = response.getStatusCode();

        assertThat(statusCode, is(201));
    }

    @Test
    public void verifyStatusCodeRequestWithHeaders() {
        Header header = new Header("accept", "application/json");
        Response response = given()
                .header(header)
                .get("https://restful-booker.herokuapp.com/booking/1");
        int statusCode = response.getStatusCode();

        assertThat(statusCode, is(200));
    }

    @Test
    public void verifyResponseBody() {
        Response response = given().get("https://restful-booker.herokuapp.com/booking/2");
        String responseBody1 = response.getBody().prettyPrint();
        BookingResponse responseBody = response.as(BookingResponse.class);
        String additionalneeds = responseBody.getAdditionalneeds();
        int totalprice = responseBody.getTotalprice();
        BookingDates bookingdates = responseBody.getBookingdates();
        LocalDate checkin = LocalDate.parse(bookingdates.getCheckin());
        LocalDate checkincomparison = LocalDate.of(2020, 5, 5);

        assertThat(additionalneeds, is("Breakfast"));
        assertThat(totalprice, is(873));
        assertThat(checkin, LocalDateMatchers.sameDay(checkincomparison));

        //assertThat(responseBody, containsString("Breakfast"));
    }

    @Test
    public void sendAuthPost(){
        AuthPayload authPayload = new AuthPayload("admin", "password123");
        Response response = given()
                .body(authPayload)
                .contentType("application/json")
                .post("https://restful-booker.herokuapp.com/auth");
        String authResponse = response.getBody().prettyPrint();
        assertThat(authResponse, containsString("token"));
    }

    @Test
    public void createNewBooking(){
        BookingDates bookingRequestDates = new BookingDates("2020-04-25", "2020-04-30");
        BookingResponse bookingRequest = new BookingResponse("Federica", "Bardini", 456, true, bookingRequestDates, "No smoking room");

        Response response = given()
                            .contentType("application/json")
                            .accept("application/json")
                            .body(bookingRequest)
                            .post("https://restful-booker.herokuapp.com/booking");

        String bodyResponse = response.getBody().prettyPrint();
        assertThat(bodyResponse, containsString("Federica"));
        assertThat(bodyResponse, containsString("bookingid"));

    }

}
