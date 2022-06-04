package pwa.Forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CodeForm {

    @NotNull
    String code;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    private String passwordConfirm;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;

        if (!this.password.equals(this.passwordConfirm)) {
            this.passwordConfirm = null;
        }

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
