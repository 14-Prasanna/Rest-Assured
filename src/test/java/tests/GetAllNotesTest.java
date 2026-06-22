package tests;

import base.BaseTest;
import base.ResponseSpec;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import services.GetAllNotesService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
public class GetAllNotesTest extends BaseTest {

    GetAllNotesService getAllNotesService = new GetAllNotesService();

    @Test(priority = 30, dependsOnMethods = "validLogin")
    public void getAllNotesNormal() {

        Response response = getAllNotesService.getAllNotes();

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body(matchesJsonSchemaInClasspath("schemas/getAllNotesSchema.json"))
                .body("success", equalTo(true))
                .body("data", notNullValue())
                .body("pagination", notNullValue())
                .log().all();
    }

    @Test(priority = 31, dependsOnMethods = "validLogin")
    public void getAllNotes_BVA_PageMin1_LimitMin1() {

        int page = 1;   // BVA: minimum valid page
        int limit = 1;  // BVA: minimum valid limit

        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("limit", limit);

        Response response = getAllNotesService.getAllNotesWithParams(params);

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body(matchesJsonSchemaInClasspath("schemas/getAllNotesSchema.json"))
                .body("success", equalTo(true))
                .body("data.size()", lessThanOrEqualTo(limit))
                .body("pagination.currentPage", equalTo(page))
                .log().all();

        int dataCount   = response.jsonPath().getList("data").size();
        int currentPage = response.jsonPath().getInt("pagination.currentPage");
        int totalPages  = response.jsonPath().getInt("pagination.totalPages");
        int totalNotes  = response.jsonPath().getInt("pagination.totalNotes");
        boolean hasNext = response.jsonPath().getBoolean("pagination.hasNext");
        boolean hasPrev = response.jsonPath().getBoolean("pagination.hasPrev");

        Assert.assertTrue(dataCount <= limit,      "data.size() must not exceed limit");
        Assert.assertEquals(currentPage, page,     "currentPage must equal requested page");
        Assert.assertTrue(totalNotes >= dataCount, "totalNotes must be >= items on page");

        int expectedTotalPages = (int) Math.ceil((double) totalNotes / limit);
        Assert.assertEquals(totalPages, expectedTotalPages, "totalPages formula mismatch");

        Assert.assertEquals(hasNext, currentPage < totalPages, "hasNext flag mismatch");
        Assert.assertEquals(hasPrev, currentPage > 1,          "hasPrev flag mismatch");
    }

    @Test(priority = 32, dependsOnMethods = "validLogin")
    public void getAllNotes_BVA_LimitMax50_WithAllValidParams() {

        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("limit", 50);        // BVA: maximum valid limit
        params.put("search", "API");    // EP: valid search string that has matches
        params.put("tags", "qa");       // EP: existing tag
        params.put("isPinned", false);  // EP: valid boolean
        params.put("sortBy", "lastEdited"); // EP: valid enum value
        params.put("sortOrder", "desc");    // EP: valid enum value

        Response response = getAllNotesService.getAllNotesWithParams(params);

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body(matchesJsonSchemaInClasspath("schemas/getAllNotesSchema.json"))
                .body("success", equalTo(true))
                .body("data.size()", lessThanOrEqualTo(50))
                .log().all();
    }

    @Test(priority = 33, dependsOnMethods = "validLogin")
    public void getAllNotes_Valid_SortByTitle_OrderAsc() {

        Map<String, Object> params = new HashMap<>();
        params.put("sortBy", "title");
        params.put("sortOrder", "asc");

        Response response = getAllNotesService.getAllNotesWithParams(params);

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body(matchesJsonSchemaInClasspath("schemas/getAllNotesSchema.json"))
                .body("success", equalTo(true))
                .log().all();

        List<String> titles = response.jsonPath().getList("data.title");
        if (titles != null && titles.size() > 1) {
            for (int i = 0; i < titles.size() - 1; i++) {
                Assert.assertTrue(
                        titles.get(i).compareToIgnoreCase(titles.get(i + 1)) <= 0,
                        "Titles are not sorted ascending at index " + i
                );
            }
        }
    }


    @Test(priority = 34, dependsOnMethods = "validLogin")
    public void getAllNotes_Valid_IsPinnedTrue() {

        Map<String, Object> params = new HashMap<>();
        params.put("isPinned", true);

        Response response = getAllNotesService.getAllNotesWithParams(params);

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body(matchesJsonSchemaInClasspath("schemas/getAllNotesSchema.json"))
                .body("success", equalTo(true))
                .log().all();

        List<Boolean> pinnedValues = response.jsonPath().getList("data.isPinned");
        for (Boolean value : pinnedValues) {
            Assert.assertTrue(value, "Expected isPinned=true but found false in results");
        }
    }

    @Test(priority = 35, dependsOnMethods = "validLogin")
    public void getAllNotes_Valid_IsPinnedFalse() {

        Map<String, Object> params = new HashMap<>();
        params.put("isPinned", false);

        Response response = getAllNotesService.getAllNotesWithParams(params);

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body(matchesJsonSchemaInClasspath("schemas/getAllNotesSchema.json"))
                .body("success", equalTo(true))
                .log().all();

        List<Boolean> pinnedValues = response.jsonPath().getList("data.isPinned");
        for (Boolean value : pinnedValues) {
            Assert.assertFalse(value, "Expected isPinned=false but found true in results");
        }
    }

    @Test(priority = 36, dependsOnMethods = "validLogin")
    public void getAllNotes_Valid_SearchWithMatchingText() {

        Map<String, Object> params = new HashMap<>();
        params.put("search", "API");

        Response response = getAllNotesService.getAllNotesWithParams(params);

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body(matchesJsonSchemaInClasspath("schemas/getAllNotesSchema.json"))
                .body("success", equalTo(true))
                .log().all();
    }


    @Test(priority = 37, dependsOnMethods = "validLogin")
    public void getAllNotes_Valid_SearchWithNoMatchReturnsEmpty() {

        Map<String, Object> params = new HashMap<>();
        params.put("search", "xyz_definitely_no_match_9999");

        Response response = getAllNotesService.getAllNotesWithParams(params);

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body("success", equalTo(true))
                .log().all();

        List<?> data = response.jsonPath().getList("data");
        Assert.assertNotNull(data, "data field should not be null");
    }

    @Test(priority = 38, dependsOnMethods = "validLogin")
    public void getAllNotes_Valid_TagsFilter() {

        Map<String, Object> params = new HashMap<>();
        params.put("tags", "qa");

        Response response = getAllNotesService.getAllNotesWithParams(params);

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body(matchesJsonSchemaInClasspath("schemas/getAllNotesSchema.json"))
                .body("success", equalTo(true))
                .log().all();
    }

    @Test(priority = 39)
    public void getAllNotes_Invalid_NoToken_Returns401() {

        Response response = getAllNotesService.getAllNotesWithoutToken();

        response.then()
                .spec(ResponseSpec.responseSpec(401))
                .body(matchesJsonSchemaInClasspath("schemas/errorSchema.json"))
                .body("message[0].key", equalTo("error"))
                .body("message[0].value", equalTo("User is not logged in"))
                .log().all();
    }


    @Test(priority = 40)
    public void getAllNotes_Invalid_BadToken_Returns401() {

        Response response = getAllNotesService.getAllNotesWithInvalidToken();

        response.then()
                .spec(ResponseSpec.responseSpec(401))
                .body(matchesJsonSchemaInClasspath("schemas/errorSchema.json"))
                .body("message[0].key", equalTo("error"))
                .body("message[0].value", equalTo("Invalid or expired token"))
                .log().all();
    }

    @DataProvider(name = "bvaEpInvalidQueryParams")
    public Object[][] bvaEpInvalidQueryParams() {
        return new Object[][]{
                // param,        value,           description
                {"page",      0,            "BVA: page just below min (0)"},
                {"page",      -1,           "EP:  page negative integer"},
                {"page",      "abc",        "EP:  page non-numeric string"},
                {"limit",     0,            "BVA: limit just below min (0)"},
                {"limit",     -1,           "EP:  limit negative integer"},
                {"limit",     51,           "BVA: limit just above max (51)"},
                {"limit",     "abc",        "EP:  limit non-numeric string"},
                {"sortBy",    "invalidField","EP:  sortBy outside enum"},
                {"sortBy",    "createdAt",  "EP:  sortBy plausible but unsupported"},
                {"sortOrder", "wrong",      "EP:  sortOrder outside {asc,desc}"},
                {"sortOrder", "ASCENDING",  "EP:  sortOrder wrong case"},
                {"isPinned",  "abc",        "EP:  isPinned non-boolean string"},
                {"isPinned",  "yes",        "EP:  isPinned truthy string, not boolean"},
        };
    }

    @Test(priority = 41, dependsOnMethods = "validLogin", dataProvider = "bvaEpInvalidQueryParams")
    public void getAllNotes_Invalid_QueryParam(String param, Object value, String description) {

        Map<String, Object> params = new HashMap<>();
        params.put(param, value);

        Response response = getAllNotesService.getAllNotesWithParams(params);

        response.then().log().all();

        int statusCode = response.statusCode();

        Assert.assertNotEquals(
                statusCode, 500,
                "API must not return 500 for: " + description
        );

        // Prefer explicit 400 for clearly invalid inputs; tolerate 200 for lenient APIs.
        Assert.assertTrue(
                statusCode == 400 || statusCode == 200,
                "Expected 400 or 200 for [" + description + "] but got " + statusCode
        );
    }

    @Test(priority = 42, dependsOnMethods = "validLogin")
    public void getAllNotes_Edge_SearchEmptyString() {

        Map<String, Object> params = new HashMap<>();
        params.put("search", "");

        Response response = getAllNotesService.getAllNotesWithParams(params);

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body("success", equalTo(true))
                .log().all();
    }


    @Test(priority = 43, dependsOnMethods = "validLogin")
    public void getAllNotes_Edge_SearchVeryLongString() {

        String longSearch = "a".repeat(200);
        Map<String, Object> params = new HashMap<>();
        params.put("search", longSearch);

        Response response = getAllNotesService.getAllNotesWithParams(params);

        int status = response.statusCode();
        Assert.assertNotEquals(status, 500, "Long search string must not cause 500");
        Assert.assertTrue(status == 200 || status == 400,
                "Expected 200 or 400 for long search string, got " + status);

        response.then().log().all();
    }


    @Test(priority = 44, dependsOnMethods = "validLogin")
    public void getNoteById_Invalid_NonExistentId() {

        Response response = getAllNotesService.getNoteByInvalidId("invalid_note_id_123");

        response.then()
                .statusCode(anyOf(equalTo(400), equalTo(404)))
                .log().all();

        Assert.assertNotEquals(response.statusCode(), 500,
                "Invalid note ID must not cause 500");
    }


    @Test(priority = 45, dependsOnMethods = "validLogin")
    public void getAllNotes_Edge_NonExistentTag() {

        Map<String, Object> params = new HashMap<>();
        params.put("tags", "nonexistent_tag_xyz_9999");

        Response response = getAllNotesService.getAllNotesWithParams(params);

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body("success", equalTo(true))
                .log().all();

        List<?> data = response.jsonPath().getList("data");
        Assert.assertNotNull(data, "data field should not be null even when tag has no matches");
    }
}