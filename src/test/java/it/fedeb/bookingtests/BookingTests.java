package it.fedeb.bookingtests;

import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

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
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("requests/createNewBooking.json")).getFile());

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

        int bookingid = response.path("bookingid");
        //System.out.println(bookingid);

    }

    @Test
    @DisplayName("Delete a newly created booking")
    public void deleteBooking(){

        //New Booking creation
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("requests/createNewBooking.json")).getFile());

        Response response = given()
                .body(file)
                .contentType("application/json")
                .accept("application/json")
                .post("https://restful-booker.herokuapp.com/booking");

        response.then()
                .statusCode(200);

        int bookingid = response.path("bookingid");
        System.out.println(bookingid);

        //Token auth
        File fileAuth = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("requests/newAuth.json")).getFile());
        Response responseAuth = given()
                .contentType("application/json")
                .body(fileAuth)
                .post("https://restful-booker.herokuapp.com/auth");

        responseAuth.then()
                .statusCode(200);
        String token = responseAuth.path("token");
        System.out.println(token);

        //Delete booking
        Response responseDelete = given()
                .contentType("application/json")
                .cookie("token", token)
                .delete("https://restful-booker.herokuapp.com/booking/"+ bookingid);

        responseDelete.then()
                .statusCode(201);
    }

}
