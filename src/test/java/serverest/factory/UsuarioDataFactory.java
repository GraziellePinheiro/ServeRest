package serverest.factory;

import java.util.UUID;

import serverest.pojo.UsuarioPojo;

public class UsuarioDataFactory {

    public static UsuarioPojo criarUsuarioAdm(){
        return new UsuarioPojo("admin", "admin@admin.com.br", "admin", "true");
    }

    public static UsuarioPojo criarUsuarioComum(){
        return new UsuarioPojo("user1", "user1@user1.com.br", "user1", "false");
    }


}
