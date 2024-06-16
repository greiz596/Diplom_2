import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import data.Login;
import org.junit.After;
import org.junit.Test;
import static utils.constants.Constants.*;
import static utils.constants.Steps.*;
import static utils.constants.User.*;
import static org.hamcrest.Matchers.*;

public class LoginUserTests extends BaseTest {

    @Test
    @DisplayName("Логин. Пользователь существует")
    @Description("Успешный логин существующего пользователя")
    public void loginValidUser() {

        USER_API.sendCreateUser(DEFAULT_CREATE_USER_DATA).then().statusCode(CODE_200);
        USER_API.sendLoginUser(DEFAULT_LOGIN_DATA).then()
                .statusCode(CODE_200)
                .body("success", equalTo(true))
                .body("accessToken", startsWith("Bearer "))
                .body("refreshToken", not(emptyString()))
                .body("user.email", equalTo(EMAIL))
                .body("user.name", equalTo(LOGIN));
    }

    @Test
    @DisplayName("Логин. Неправильный email")
    @Description("Неуспешный логин с неправильным email, но правильным паролем")
    public void loginInvalidEmail() {
        USER_API.sendCreateUser(DEFAULT_CREATE_USER_DATA).then().statusCode(CODE_200);
        Login login = new Login(WRONG_EMAIL, PASSWORD);

        USER_API.sendLoginUser(login).then()
                .statusCode(CODE_401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Логин. Неправильный пароль")
    @Description("Неуспешный логин с правильным email, но неправильным паролем")
    public void loginInvalidPassword() {
        USER_API.sendCreateUser(DEFAULT_CREATE_USER_DATA).then().statusCode(CODE_200);
        Login login = new Login(EMAIL, WRONG_PASSWORD);

        USER_API.sendLoginUser(login).then()
                .statusCode(CODE_401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @After
    public void sendDeleteUser() {
        USER_API.deleteUser(DEFAULT_LOGIN_DATA);
    }
}
