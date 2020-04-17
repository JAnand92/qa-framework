package utils;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.testng.Assert;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;

public class RestUtils {
    /*Global setup variable*/
    public static String path;  //Rest request path

    /*Set Base URI
    * Before starting the test, we should set the RestAssured.baseURI
    */
    public static void setBaseURI(String baseURI) {
        try {
            RestAssured.baseURI = baseURI;
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    /*Set Base Path
    * Before starting the test, we should set the RestAssured.basePath
    */
    public static void setBasePath(String basePathTerm) {
        try {
            RestAssured.basePath = basePathTerm;
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    /*Rest Base URI
    * After test, reset the base URI
    */
    public static void resetBaseURI() {
        try {
            RestAssured.baseURI = null;
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    /*Reset Base Path
    * After test, reset the base Path
    */
    public static void resetBasePath() {
        try {
            RestAssured.basePath = null;
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    /*Set ContentType
    * We should set content type Json or Xml before starting the test
    */
    public static void setContentType(ContentType contentType) {
        try {
           given().contentType(contentType);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    /*Set search query path*/
    public static void createSearchQueryPath(String searchTerm, String jsonPathTerm, String param, String paramValue) {
        try {
            path = searchTerm + "/" + jsonPathTerm + "?" + param + "=" + paramValue;
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    /*Return Response
    * We send "path" as a parameter to the Rest Assured's get method
    * and "get" method return response of API
    */
    public static Response getResponse() {
        System.out.println("Path: " + path + "\n");
        return get(path);
    }

    /*
    * Return JsonPath Object
    * First convert the API response to String type with "asString()" method.
    * Then, send this String formatted json response to the Json class and return the JsonPath.
    */
    public static JsonPath getJsonPath(Response response) {
        String json = null;
        try {
            json = response.asString();
            System.out.println("Returned Json: " + json);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
        return new JsonPath(json);
    }

    /*Verify the HTTP response status returned. Check status code is 200?
    * We can use Rest Assured library's response's getStatusCode method.
    */
    public static void isStatus200(Response response) {
        try {
            Assert.assertEquals(response.getStatusCode(), 200, "Status Check Failed");
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}
