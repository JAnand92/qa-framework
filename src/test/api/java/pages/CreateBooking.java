package pages;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateBooking {
    /*Create Body Using POJOs*/

    static BookingDates bookingDates = new BookingDates("2020-04-01", "2020-04-03");
    static Booking booking = new Booking("Jay", "Ana",
            200, true, "No Need", bookingDates);

    static RequestSpecification requestSpecification = new RequestSpecBuilder().
            setBaseUri("https://restful-booker.herokuapp.com").build();

    static Response response = null;
    static BookingId bookingId = null;

    @Test
    public static void createBooking() {
        try {
            response = RestAssured.given(requestSpecification).contentType(ContentType.JSON).
                    body(booking).post("/booking");

            bookingId = response.as(BookingId.class);

            System.out.println("Request Booking : " + booking.toString());
            System.out.println("Response Booking : " + bookingId.getBooking().toString());

            Assert.assertEquals(bookingId.getBooking().toString(), booking.toString());

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}
