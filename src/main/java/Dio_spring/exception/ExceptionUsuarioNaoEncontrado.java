package Dio_spring.exception;

public class ExceptionUsuarioNaoEncontrado extends RuntimeException{
    public ExceptionUsuarioNaoEncontrado(String msg){
        super(msg);
    }
}
