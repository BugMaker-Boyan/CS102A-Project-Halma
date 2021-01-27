package xyz.chengzi.halma.Internet;

import java.io.Serializable;

public class LoginResult implements Serializable {
    private static final long serialVersionUID=1L;
    public boolean userExist;
    public boolean passwordRight;
    public String userName;
    public int points;

    public LoginResult(boolean userExist, boolean passwordRight,String userName,int points) {
        this.userExist = userExist;
        this.passwordRight = passwordRight;
        this.userName=userName;
        this.points=points;
    }

    @Override
    public String toString() {
        return String.format("用户是否存在：%b\n密码是否正确：%b",userExist,passwordRight);
    }
}
