package tests;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.*;
import com.jayway.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;

public class RestfulBookerApiTest {

    static Response response = null;

    static RequestSpecification requestSpecification =
            new RequestSpecBuilder().
                    setBaseUri("https://restful-booker.herokuapp.com").build();

    @Test
    private static void healthCheckup() {
        try {
            given().
                    spec(requestSpecification).
                    when().
                    get("/ping").
                    then().
                    assertThat().
                    statusCode(201);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @Test
    private static void headerAndCookieTest() {
        try {
            /*Add Headers and Cookies in RequestSpecification*/
            Header someHeader = new Header("SomeHeader", "SomeValue");
            requestSpecification.header(someHeader);

            Cookie someCookie = new Cookie.Builder("SomeCookie", "SomeCookie Value").build();
            requestSpecification.cookie(someCookie);

            response = RestAssured.given(requestSpecification).
                    cookie("TestCookie", "TestValue").
                    header("TestHeader", "TestHeaderValue").log().all().
                    get("/ping");

            /*Get Header*/
            Headers headers = response.getHeaders();
            System.out.println(headers);

            Header serverHeader = headers.get("Server");
            System.out.println(serverHeader.getName() + " " + serverHeader.getValue());

            String serverHeader1 = response.getHeader("Server");
            System.out.println(serverHeader1);

            /*Get Cookies*/
            Cookies cookies = response.detailedCookies();
            System.out.println(cookies);

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @Test
    private static void getBookingIdWithoutFilter() {
        try {
            /*Get response with booking ids*/
            response = RestAssured.
                    given(requestSpecification).
                    get("/booking");
            response.print();

            /*Verify response 200*/
            Assert.assertEquals(response.getStatusCode(), "Status code is not 200!");

            /*Verify at least one booking id in response*/
            List<Integer> bookingIds = response.jsonPath().getList("bookingid");
            Assert.assertFalse(bookingIds.isEmpty(), "List of booking id is empty!");
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @Test
    private static void getBookingIdFilterByName() {
        try {
            /*Set Query Parameter*/
            requestSpecification.queryParam("firstName", "Jay");
            requestSpecification.queryParam("lastName", "Ana");

            /*Get Response*/
            response = RestAssured.given(requestSpecification).
                    get("/booking");

            /*Print Response*/
            response.print();

            Assert.assertEquals(response.getStatusCode(),200, "Status code is not 200!");

            List<Integer> bookingIds = response.jsonPath().getList("bookingid");
            Assert.assertFalse(bookingIds.isEmpty(), "Booking IDs' list is empty!");

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @Test
    private static void getBookingIdFilterByCheckingDetails() {
        try {
            response = RestAssured.given(requestSpecification).
                    get("/booking?checkin=2020-04-01&checkout=2020-04-03");
            System.out.println("Booking Details: ");
            response.print();

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @Test
    private static void getBookingDetails() {
        try {
            /*Set path parameter*/
            requestSpecification.pathParam("bookingId", 1);
            /*Get the Response*/
            response = RestAssured.given(requestSpecification).
                    get("/booking/{bookingId}");
            /*Print Response*/
            response.print();

            /*Check Status Code*/
            Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200!");

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @Test
    private static void getBookingDetailsXMLResponse() {
        try {
            /*Set path parameter*/
            requestSpecification.pathParam("bookingId", 1);

            /*Set Header*/
            Header xml = new Header("Accept", "application/xml");
            requestSpecification.header(xml);

            /*Get Response*/
            response = RestAssured.given(requestSpecification).log().all().get("/booking/{bookingId}");
            response.print();

            String firstName = response.xmlPath().getString("booking.firstname");
            Assert.assertEquals("Susan", firstName);

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @Test
    private static void createBooking() {
        try {
            /*Create JSON body*/
            JSONObject body = new JSONObject();
            body.put("firstname", "Jay");
            body.put("lastname", "ana");
            body.put("totalprice", "150");
            body.put("depositpaid", true);

            JSONObject bookingDates = new JSONObject();
            bookingDates.put("checkin", "2020-04-01");
            bookingDates.put("checkout", "2020-04-03");
            body.put("bookingdates",bookingDates );

            body.put("additionalneeds", "Extended Suits");

            /*Post Response*/
            response = RestAssured.given(requestSpecification).
                    contentType(ContentType.JSON).body(body.toString()).
                    post("/booking");
            response.print();

            /* Check Status Code */
            Assert.assertEquals(response.getStatusCode(),200, "Status code does not match!");

            /*Verify response*/
            SoftAssert softAssert = new SoftAssert();

            String firstName = response.jsonPath().getString("booking.firstname");
            softAssert.assertEquals("Jay", firstName);

            String lastName = response.jsonPath().getString("booking.lastname");
            softAssert.assertEquals("ana", lastName);

            int totalPrice = response.jsonPath().getInt("booking.totalprice");
            softAssert.assertEquals(150, totalPrice);

            boolean depositPaid = response.jsonPath().getBoolean("booking.depositpaid");
            softAssert.assertTrue(depositPaid);

            String checkIn = response.jsonPath().getString("booking.bookingdates.checkin");
            softAssert.assertEquals("2020-04-01", checkIn);

            String checkOut = response.jsonPath().getString("booking.bookingdates.checkout");
            softAssert.assertEquals("2020-04-03", checkOut);

            String additionalNeeds = response.jsonPath().getString("booking.additionalneeds");
            softAssert.assertEquals("Extended Suits", additionalNeeds);

            softAssert.assertAll();

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @Test
    private static void updateBooking() {
        try {
            /*Create Json Body*/
            JSONObject body = new JSONObject();
            body.put("firstname", "Jay");
            body.put("lastname", "Ana");
            body.put("totalprice", 150);
            body.put("depositpaid", true);

            JSONObject bookingDates = new JSONObject();
            bookingDates.put("checkin", "2020-04-01");
            bookingDates.put("checkout", "2020-04-03");
            body.put("bookingdates", bookingDates);

            body.put("additionalneeds", "Extended Suits");

            /*Create Booking*/
            response = RestAssured.given(requestSpecification).
                    contentType(ContentType.JSON).body(body.toString()).
                    post("/booking");
            System.out.println("Booking Details: ");
            response.print();

            /*Get Booking ID*/
            int bookingId = response.jsonPath().getInt("bookingid");

            /*Create Updated Json Body*/
            JSONObject updatedBody = new JSONObject();
            updatedBody.put("firstname", "Jay");
            updatedBody.put("lastname", "Ana");
            updatedBody.put("totalprice", 110);
            updatedBody.put("depositpaid", false);

            JSONObject bookingDates1 = new JSONObject();
            bookingDates1.put("checkin", "2020-04-01");
            bookingDates1.put("checkout", "2020-04-03");
            updatedBody.put("bookingdates", bookingDates1);

            updatedBody.put("additionalneeds", "Extended Suits");

            /*Update Response*/
            response = RestAssured.given(requestSpecification).contentType(ContentType.JSON).
                    auth().preemptive().basic("admin", "password123").body(updatedBody.toString()).
                    put("/booking/" + bookingId);
            System.out.println();
            System.out.println("Updated Booking Details: ");
            response.print();

            /*Check Status Code*/
            Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200!");

            /*Get Updated Response*/
            System.out.println();
            response = RestAssured.given(requestSpecification).
                    get("/booking/" +bookingId);
            System.out.println("Updated Booking Details for Confirmation: ");
            response.print();

            /*Verify Response*/
            System.out.println();
            SoftAssert softAssert = new SoftAssert();
            String firstName = response.jsonPath().getString("firstname");
            softAssert.assertEquals("Jay", firstName);
            String lastName = response.jsonPath().getString("lastname");
            softAssert.assertEquals("Ana", lastName);
            int ttlPrice = response.jsonPath().getInt("totalprice");
            softAssert.assertEquals("110", ttlPrice);
            boolean depositPaid = response.jsonPath().getBoolean("depositpaid");
            softAssert.assertTrue(depositPaid);
            String checkin = response.jsonPath().getString("bookingdates.checkin");
            softAssert.assertEquals("2020-04-01", checkin);
            String checkOut = response.jsonPath().getString("bookingdates.checkout");
            softAssert.assertEquals("2020-04-03", checkOut);
            softAssert.assertAll();

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @Test
    private static void updateBookingPartially() {
        try {
            /*Create Json Body*/
            JSONObject body = new JSONObject();
            body.put("firstname", "Jay");
            body.put("lastname", "Ana");
            body.put("totalprice", 150);
            body.put("depositpaid", true);

            JSONObject bookingDates = new JSONObject();
            bookingDates.put("checkin", "2020-04-01");
            bookingDates.put("checkout", "2020-04-03");
            body.put("bookingdates", bookingDates);

            body.put("additionalneeds", "Extended Suits");

            response = RestAssured.given(requestSpecification).contentType(ContentType.JSON).body(body.toString()).
                    post("/booking");
            System.out.println("Booking Created: ");
            response.print();

            /*Get Booking id*/
            int bookingId = response.jsonPath().getInt("bookingid");

            /*Updated body*/
            JSONObject updatedBody = new JSONObject();
            updatedBody.put("totalprice", 110);

            /*Patch Booking Details using PATCH*/
            System.out.println();
            response = RestAssured.given(requestSpecification).auth().preemptive().basic("admin", "password123").
                    contentType(ContentType.JSON).body(updatedBody.toString()).
                    patch("/booking/" + bookingId);
            System.out.println("Booking Updated: ");
            response.print();

            /*Check Status Code*/
            Assert.assertEquals(response.getStatusCode(), 200);

            /*Verify Updated Value*/
            response = RestAssured.get("https://restful-booker.herokuapp.com/booking/" + bookingId);
            System.out.println();
            int totalPrice = response.jsonPath().getInt("totalprice");

            Assert.assertEquals(110, totalPrice);

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @Test
    private static void deleteBooking() {
        try {
            /*Create Json Body*/
            JSONObject body = new JSONObject();
            body.put("firstname", "Jay");
            body.put("lastname", "Ana");
            body.put("totalprice", 150);
            body.put("depositpaid", true);

            JSONObject bookingDates = new JSONObject();
            bookingDates.put("checkin", "2020-04-01");
            bookingDates.put("checkout", "2020-04-03");
            body.put("bookingdates", bookingDates);

            body.put("additionalneeds", "Extended Suits");

            response = RestAssured.given(requestSpecification).contentType(ContentType.JSON).body(body.toString()).
                    post("/booking");
            System.out.println("Booking Created: ");
            response.print();

            /*Get Booking Id */
            int bookingId = response.jsonPath().getInt("bookingid");

            /*Delete Booking*/
            System.out.println();
            response = RestAssured.given(requestSpecification).auth().preemptive().basic("admin", "password123").
                    delete("/booking" + bookingId);

            Assert.assertEquals(response.getStatusCode(), 404, "Status code is not matching!");

            response = RestAssured.given(requestSpecification).
                    get("/booking" + bookingId);

            Assert.assertEquals(response.getBody().asString(), "Not Found", "Result not Matched!");

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}
