package Dio_spring.exception;

public class ExceptionConflitoUsuario extends RuntimeException{

    public ExceptionConflitoUsuario(String msg){
        super(msg);
    }
}
