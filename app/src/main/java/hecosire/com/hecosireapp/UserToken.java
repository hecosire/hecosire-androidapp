package hecosire.com.hecosireapp;

public class UserToken {

    private String email;
    private String token;

    public UserToken(String email, String token) {

        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
