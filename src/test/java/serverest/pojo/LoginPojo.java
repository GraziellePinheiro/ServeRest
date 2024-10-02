package serverest.pojo;

public class LoginPojo {
    private String email;
    private String password;

    public LoginPojo(String email, String password){
        if(email == null || password == null){
            throw new IllegalArgumentException("Nenhum argumento pode ser nulo");
        }
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }



}
