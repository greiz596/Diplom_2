import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import data.CreateUser;
import org.junit.After;
import org.junit.Test;

import static utils.constants.Constants.*;
import static utils.constants.Steps.*;
import static utils.constants.User.*;
import static org.hamcrest.Matchers.*;

public class CreateUserTests extends BaseTest{

    @Test
    @DisplayName("Создать новый уникальный пользователь")
    @Description("Успешное создание нового уникального пользователя")
    public void createNewUser(){
   USER_API.sendCreateUser(DEFAULT_CREATE_USER_DATA)
                .then()
                .statusCode(CODE_200)
                .body("success", equalTo(true))
                .body("user.email", equalTo(EMAIL))
                .body("user.name", equalTo(LOGIN))
                .body("accessToken", not(emptyString()))
                .body("refreshToken", not(emptyString()));
    }

    @Test
    @DisplayName("Создать новый неуникальный пользователь")
    @Description("Неуспешное создание пользователя, который уже существует")
    public void createDuplicationUser() {
        USER_API.sendCreateUser(DEFAULT_CREATE_USER_DATA).then().statusCode(CODE_200);
        USER_API.sendCreateUser(DEFAULT_CREATE_USER_DATA).then()
                .statusCode(CODE_403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создать пользователя без email")
    @Description("Неуспешное создание пользователя без email")
    public void createUserWithoutEmail() {
        CreateUser createUser = new CreateUser(null, PASSWORD, LOGIN);
        USER_API.sendCreateUser(createUser).then()
                .statusCode(CODE_403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создать пользователя без поля password")
    @Description("Неуспешное  создание пользователя без password")
    public void createUserWithoutPassword() {
        CreateUser createUser = new CreateUser(EMAIL, null, LOGIN);
        USER_API.sendCreateUser(createUser).then()
                .statusCode(CODE_403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создать пользователя без login")
    @Description("Неуспешное  создание пользователя без login")
    public void createUserWithoutLogin() {
        CreateUser createUser = new CreateUser(EMAIL, PASSWORD, null);
        USER_API.sendCreateUser(createUser).then()
                .statusCode(CODE_403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    public void sendDeleteUser() {
        USER_API.deleteUser(DEFAULT_LOGIN_DATA);
    }
}
