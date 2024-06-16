import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import static utils.constants.Constants.*;
import static utils.constants.Order.*;
import static utils.constants.Steps.*;
import static utils.constants.User.*;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.Matchers.*;

public class GetOrdersTests extends BaseTest {


    @Test
    @DisplayName("Получить список заказов. Пользователь авторизован")
    @Description("Получен список заказов конкретного пользователя под его учетной записью")

    public void getOrdersAuthUser() {
        USER_API.sendCreateUser(DEFAULT_CREATE_USER_DATA).then().statusCode(CODE_200);
        String accessToken=USER_API.loginGetAccessToken(DEFAULT_LOGIN_DATA);

        ORDER_API.sendCreateOrder(DEFAULT_CREATE_ORDER_DATA, accessToken).then().statusCode(CODE_200).extract().response();

        ORDER_API.sendGetOrder(accessToken).then()
                .statusCode(CODE_200)
                .body("success", equalTo(true))
                .body("orders._id", everyItem(matchesPattern("^[0-9a-f]{24}$")))  // Проверка _id
                .body("orders.ingredients.flatten()", everyItem(matchesPattern("^[0-9a-f]{24}$")))
                .body("orders.status[0]", equalTo("done"))
                .body("orders.name", everyItem(matchesPattern("^[а-яА-Яa-zA-Z\\s-]+$")))
                .body("orders.createdAt", everyItem(matchesPattern("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$")))
                .body("orders.updatedAt", everyItem(matchesPattern("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$")))
                .body("orders.number", everyItem(instanceOf(Integer.class)))
                .body("total", instanceOf(Integer.class))
                .body("totalToday", instanceOf(Integer.class));
    }

    @Test
    @DisplayName("Получить список заказов. Пользователь не авторизован")
    @Description("Не получен список заказов, отсутствует авторизация")
    public void getOrdersUnAuthUser() {
        ORDER_API.sendGetOrder("").then()
                .statusCode(CODE_401)
                .body("message", equalTo("You should be authorised"));
    }

    @After
    public void sendDeleteUser() {
        USER_API.deleteUser(DEFAULT_LOGIN_DATA);
    }
}
