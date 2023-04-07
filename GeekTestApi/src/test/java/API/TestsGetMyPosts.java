package API;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestsGetMyPosts extends AbstractTest {
    @Test
    @DisplayName("Свои посты с сортировкой ASC")
    @Tag("Positive")
    void getMyPostsASCTest() {
        JsonPath response = given()
                .header("X-Auth-Token", "e91aa089f73debcb9138a2250a2de627")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl() + getPathGetPosts())
                .then()
                .statusCode(200)
                .extract().body().jsonPath();
        assertThat(response.getString("data[0].title"), equalTo("павпап"));
        if (Integer.parseInt(response.getString("meta.count")) > 4) {
            assertThat(response.getString("meta.nextPage"), equalTo("2"));
        } else {
            assertThat(response.getString("meta.nextPage"), isEmptyOrNullString());
        }
    }

    @Test
    @DisplayName("Свои посты с сортировкой DESC")
    @Tag("Positive")
    void getMyPostsDESCTest() {
        JsonPath response = given()
                .header("X-Auth-Token", "e91aa089f73debcb9138a2250a2de627")
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl() + getPathGetPosts())
                .then()
                .statusCode(200)
                .extract().body().jsonPath();
        assertThat(response.getString("data[0].title"), equalTo("Самый свежий пост"));
        if (Integer.parseInt(response.getString("meta.count")) > 4) {
            assertThat(response.getString("meta.nextPage"), equalTo("2"));
        } else {
            assertThat(response.getString("meta.nextPage"), isEmptyOrNullString());
        }
    }

    @Test
    @DisplayName("Свои посты с сортировкой 2-й страницы")
    @Tag("Positive")
    void getMyPostsPage2Test() {
        JsonPath response = given()
                .header("X-Auth-Token", "e91aa089f73debcb9138a2250a2de627")
                .queryParam("sort", "createdAt")
                .queryParam("page", "2")
                .when()
                .get(getBaseUrl() + getPathGetPosts())
                .then()
                .statusCode(200)
                .extract().body().jsonPath();
        assertThat(response.getString("meta.prevPage"), equalTo("1"));
        if (Integer.parseInt(response.getString("meta.count")) > 8) {
            assertThat(response.getString("meta.nextPage"), equalTo("3"));
        } else {
            assertThat(response.getString("meta.nextPage"), equalTo("null"));
        }
    }

    @Test
    @DisplayName("Свои посты с невалидными данными: отрицательное значение page")
    @Tag("Negative")
    void getMyPostsBedRequestTest() {
        JsonPath response = given()
                .header("X-Auth-Token", "e91aa089f73debcb9138a2250a2de627")
                .queryParam("page", "-1")
                .when()
                .get(getBaseUrl() + getPathGetPosts())
                .then()
                .statusCode(400)
                .extract().body().jsonPath();
        assertThat(response.getString("message"), equalTo("Bad request"));
    }

    @Test
    @DisplayName("Свои посты без токена")
    @Tag("Negative")
    void getMyPostsWithoutTokenTest() {
        JsonPath response = given()
                .when()
                .get(getBaseUrl() + getPathGetPosts())
                .then()
                .assertThat()
                .statusCode(401)
                .extract().jsonPath();
        assertThat(response.getString("message"), equalTo("Auth header required X-Auth-Token"));
    }


    @Test
    @DisplayName("Свои посты с невалидным токеном")
    @Tag("Negative")
    void getMyPostsInvalidTokenTest() {
        JsonPath response = given()
                .header("X-Auth-Token", "123456")
                .when()
                .get(getBaseUrl() + getPathGetPosts())
                .then()
                .assertThat()
                .statusCode(401)
                .extract().jsonPath();
        assertThat(response.getString("message"), equalTo("No API token provided or is not valid"));
    }
}



