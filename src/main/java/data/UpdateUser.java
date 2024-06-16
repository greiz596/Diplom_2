package data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUser {
    private String email;
    private String password;
    private String name;
    public UpdateUser() {
    }
    public UpdateUser(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
