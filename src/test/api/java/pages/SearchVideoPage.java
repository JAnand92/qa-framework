package pages;

import com.jayway.restassured.path.json.JsonPath;

import java.util.ArrayList;
import java.util.Collections;

public class SearchVideoPage {
    private static ArrayList videoIdList = null;

    /*Get Video Ids*/
    public static ArrayList getVideoIds(JsonPath jsonPath) {
        try {
            videoIdList = jsonPath.get("items.id");
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
        return videoIdList;
    }

    /*Find duplicate videos*/
    public static boolean findDuplicateVideos(ArrayList videoIdList) {
        try {
            for(int i=0;i<videoIdList.size();i++) {
                if(Collections.frequency(videoIdList, videoIdList.get(i)) > 1) {
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
        return true;
    }
}
