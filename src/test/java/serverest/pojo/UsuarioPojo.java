package serverest.pojo;

public class UsuarioPojo {
    private String nome;
    private String email;
    private String password;
    private String administrador;


    public UsuarioPojo(String nome, String email, String password, String administrador) {
        if (nome == null || email == null || password == null || administrador == null) {
            throw new IllegalArgumentException("Nenhum argumento pode ser nulo");
        }
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.administrador = administrador;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
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
    
    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }
}
