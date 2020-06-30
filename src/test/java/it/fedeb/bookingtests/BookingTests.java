package it.fedeb.bookingtests;

import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class BookingTests {

    @Test
    public void verifyResponseStatusCode(){
        Response response = given()
                            .get("https://restful-booker.herokuapp.com/booking");
        response.then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Test di creazione prenotazione")
    public void createBooking(){
        File file = new File("/home/federica/IdeaProjects/ProveRestAssuredMaven/src/test/resources/createNewBooking.json");

        Response response = given()
                .body(file)
                .contentType("application/json")
                .accept("application/json")
                .post("https://restful-booker.herokuapp.com/booking");

        response.getBody().prettyPrint();

        response.then()
                .statusCode(200)
                .body("booking.firstname", equalTo("Federica"))
                .body("booking.lastname", equalTo("Rossi"))
                .body("booking.totalprice", equalTo(75))
                .body("booking.depositpaid", equalTo(true))
                .body("booking.additionalneeds", equalTo("No smoking room"))
                .body("booking.bookingdates.checkin", equalTo("2020-07-07"))
                .body("booking.bookingdates.checkout", equalTo("2020-07-10"));

    }
}
