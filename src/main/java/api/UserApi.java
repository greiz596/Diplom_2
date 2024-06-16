package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import data.*;

import static io.restassured.RestAssured.given;
import static utils.constants.Constants.*;
import static utils.constants.Api.*;
import static utils.constants.Steps.USER_API;
import static utils.constants.User.DEFAULT_LOGIN_DATA;
import static org.hamcrest.Matchers.equalTo;

public class UserApi {
    @Step("POST /api/auth/register")
    public Response sendCreateUser(CreateUser createUser) {
        return given()
                .header("Content-type", "application/json")
                .body(createUser)
                .post(CREATE_USER_URL);
    }

    @Step("POST /api/auth/login")
    public Response sendLoginUser(Login login) {
        return given()
                .header("Content-type", "application/json")
                .body(login)
                .post(LOGIN_USER_URL);
    }

    @Step("PATCH /api/auth/user")
    public Response sendUpdateUser(UpdateUser updateUser, String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(updateUser)
                .patch(UPDATE_INFO_URL);
    }

    @Step("Получить accessToken")
    public String loginGetAccessToken(Login login) {
        String accessToken = USER_API.sendLoginUser(DEFAULT_LOGIN_DATA).path("accessToken");
        return accessToken;
    }

    @Step("Удалить пользователь")
    public void deleteUser(Login login) {
        String accessToken = USER_API.loginGetAccessToken(login);
        if (accessToken != null) {
            Response responseDelete =
                    given()
                            .header("Authorization", accessToken)
                            .delete(USER_URL);
            responseDelete.then()
                    .statusCode(CODE_202)
                    .body("message", equalTo("User successfully removed"));
        }
    }
}
