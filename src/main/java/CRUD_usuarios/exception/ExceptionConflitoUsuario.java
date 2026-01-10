package CRUD_usuarios.exception;

public class ExceptionConflitoUsuario extends RuntimeException{

    public ExceptionConflitoUsuario(String msg){
        super(msg);
    }
}
