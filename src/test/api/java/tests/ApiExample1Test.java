package tests;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.SearchVideoPage;
import utils.RestUtils;
import utils.helpers.PropertyReaderHelper;

import javax.swing.text.Utilities;

public class ApiExample1Test {

    /*Declare Response and JsonPath Object*/
    private Response response = null;
    private JsonPath jsonPath = null;

    @BeforeTest
    private void beforeTest() {
        try {
            RestUtils.setBaseURI(PropertyReaderHelper.getProperties("BASE_URI"));
            RestUtils.setBasePath("search");
            RestUtils.setContentType(ContentType.JSON);
            RestUtils.createSearchQueryPath("barack obama",
                    "videos.json", "num_of_videos", "4");
            response = RestUtils.getResponse();
            jsonPath = RestUtils.getJsonPath(response);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @AfterTest
    private void afterTest() {
        try {
            RestUtils.resetBaseURI();
            RestUtils.resetBasePath();
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @Test
    private void T01_StatusCodeTest() {
        try {
            /*Verify the http response status returned. Check status code is 200?*/
            RestUtils.isStatus200(response);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @Test
    private void T02_SearchTermTest() {
        try {
            /*Verify the response contained the relevant search term (barack obama)*/
            Assert.assertEquals(jsonPath.get("api-info-title"), "Search results for \"barack obama\"",
                    "Title is wrong!");
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}
