package utils.constants;

import data.CreateUser;
import data.Login;

public class User {
    public static final String EMAIL = "test1romadin@mail.ru";
    public static final String PASSWORD = "123U7uw";
    public static final String LOGIN = "Roman";

    public static final String WRONG_EMAIL = "test2romadin@mail.ru";
    public static final String WRONG_PASSWORD = "133U7uw";

    public static final String NEW_EMAIL = "test3romadin@mail.ru";
    public static final String NEW_PASSWORD = "1233U7uw";
    public static final String NEW_LOGIN = "lolol";


    public static final CreateUser DEFAULT_CREATE_USER_DATA = new CreateUser(EMAIL, PASSWORD, LOGIN);
    public static final Login DEFAULT_LOGIN_DATA = new Login(EMAIL, PASSWORD);

}
