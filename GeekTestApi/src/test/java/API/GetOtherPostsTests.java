package API;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetOtherPostsTests extends AbstractTest {
    @Test
    @DisplayName("Чужие посты с сортировкой ASC")
    @Tag("Positive")
    public void getOtherPostsAsc(){
        JsonPath response =given()
                .header("X-Auth-Token", "e91aa089f73debcb9138a2250a2de627")
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl()+getPathGetPosts())
                .then()
                .statusCode(200)
                .extract().body().jsonPath();
        assertThat(response.getString("meta.nextPage"), equalTo("2"));
        assertThat(response.getString("data[0].title"), equalTo("жареные сосиски"));
    }

    @Test
    @DisplayName("Чужие посты с сортировкой DESC")
    @Tag("Positive")
    public void getOtherPostsDesc(){
        JsonPath response =given()
                .header("X-Auth-Token", "e91aa089f73debcb9138a2250a2de627")
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl()+getPathGetPosts())
                .then()
                .statusCode(200)
                .extract().body().jsonPath();
        assertThat(response.getString("meta.nextPage"), equalTo("2"));
        assertThat(response.getString("data[0].title"), equalTo("Коктейль"));
    }
    @Test
    @DisplayName("Чужие посты с сортировкой ALL")
    @Tag("Positive")
    public void getOtherPostsALL(){
        JsonPath response =given()
                .header("X-Auth-Token", "e91aa089f73debcb9138a2250a2de627")
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ALL")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl()+getPathGetPosts())
                .then()
                .statusCode(200)
                .extract().body().jsonPath();
        assertThat(response.getString("meta.nextPage"), equalTo("2"));
    }

    @Test
    @DisplayName("Чужие посты без сортировки")
    @Tag("Positive")
    public void getOtherPostsWithoutSort(){
        JsonPath response =given()
                .header("X-Auth-Token", "e91aa089f73debcb9138a2250a2de627")
                .queryParam("owner", "notMe")
                .when()
                .get(getBaseUrl()+getPathGetPosts())
                .then()
                .statusCode(200)
                .extract().body().jsonPath();
        assertThat(response.getString("meta.nextPage"), equalTo("2"));
        assertThat(response.getString("data[0].title"), equalTo("Коктейль"));
    }
    @Test
    @DisplayName("Чужие посты с невалидными данными: символьное значение page")
    @Tag("Negative")
    public void getOtherPostsBedRequest(){
        JsonPath response =given()
                .header("X-Auth-Token", "e91aa089f73debcb9138a2250a2de627")
                .queryParam("page", "%26%4@$!")
                .when()
                .get(getBaseUrl()+getPathGetPosts())
                .then()
                .statusCode(400)
                .extract().body().jsonPath();
        assertThat(response.getString("message"), equalTo("Bad request"));
    }
    @Test
    @DisplayName("Чужие посты с невалидными данными: текстовое значение page")
    @Tag("Negative")
    public void getOtherPostsTextRequest(){
        JsonPath response =given()
                .header("X-Auth-Token", "e91aa089f73debcb9138a2250a2de627")
                .queryParam("page", "test")
                .when()
                .get(getBaseUrl()+getPathGetPosts())
                .then()
                .statusCode(400)
                .extract().body().jsonPath();
        assertThat(response.getString("message"), equalTo("Bad request"));
    }
}


