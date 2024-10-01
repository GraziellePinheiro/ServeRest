package serverest.factory;

import serverest.pojo.UsuarioPojo;

public class UsuarioDataFactory {

    public static UsuarioPojo criarUsuarioAdm(){
        UsuarioPojo usuarioAdministrador = new UsuarioPojo();

        usuarioAdministrador.setNome("Admin");
        usuarioAdministrador.setEmail("admin@admin.com");
        usuarioAdministrador.setPassword("admin");
        usuarioAdministrador.setAdministrador(true);

        return usuarioAdministrador;
    }

    public static UsuarioPojo criarUsuarioComum(){
        UsuarioPojo usuarioComum = new UsuarioPojo();

        usuarioComum.setNome("user1");
        usuarioComum.setEmail("user1@user1.com");
        usuarioComum.setPassword("user1");
        usuarioComum.setAdministrador(false);

        return usuarioComum;
    }


}
