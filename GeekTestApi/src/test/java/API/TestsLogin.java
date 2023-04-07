package API;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestsLogin extends AbstractTest {
    @Test
    @DisplayName("Авторизация с валидными данными")
    @Tag("Positive")
    void loginValidTest (){
        JsonPath body = given()
                .contentType("multipart/form-data")
                .multiPart("username", "Nastya2103")
                .multiPart("password", "7b9b0f8005")
                .when()
                .post(getBaseUrl()+getPathLogin())
                .then()
                .statusCode(200)
                .extract().body().jsonPath();

        assertThat(body.getString("username"), equalTo("Nastya2103"));
        assertThat(body.getString("token"), not(""));
    }

    @Test
    @DisplayName("Авторизация с невалидными данными: кирилица")
    @Tag("Negative")
    void loginInValidTestRusLogin () {
        JsonPath body = given()
                .contentType("multipart/form-data")
                .multiPart("username", "арпово")
                .multiPart("password", "08738c0ea2")
                .when()
                .post(getBaseUrl() + getPathLogin())
                .then()
                .statusCode(401)
                .extract().body().jsonPath();

        assertThat(body.getString("username"), equalTo(null));
        assertThat(body.getString("token"), not(""));
    }

    @Test
    @DisplayName("Авторизация незарегистрированного пользователя")
    @Tag("Negative")
    void loginInValidTestNoRegistr () {
        JsonPath body = given()
                .contentType("multipart/form-data")
                .multiPart("username", "Andreeva")
                .multiPart("password", "7b9b0f8005")
                .when()
                .post(getBaseUrl() + getPathLogin())
                .then()
                .statusCode(401)
                .extract().body().jsonPath();

        assertThat(body.getString("username"), equalTo(null));
        assertThat(body.getString("token"), not(""));
    }

    @Test
    @DisplayName("Авторизация с невалидными данными: логин в символьном значении")
    @Tag("Negative")
    void loginInValidTestSymbol () {
        JsonPath body = given()
                .contentType("multipart/form-data")
                .multiPart("username", ";%;:%?:№")
                .multiPart("password", "b7745f0741")
                .when()
                .post(getBaseUrl() + getPathLogin())
                .then()
                .statusCode(401)
                .extract().body().jsonPath();

        assertThat(body.getString("username"), equalTo(null));
        assertThat(body.getString("token"), not(""));
    }
    }
