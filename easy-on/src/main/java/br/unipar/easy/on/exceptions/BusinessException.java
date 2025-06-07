package br.unipar.easy.on.exceptions;

// classe de exceção personalizada pra as regras de negócio do service
public class BusinessException extends Exception {
    
    // construtor que recebe uma mensagem de erro e passa para a superclasse (uma Exception)
    public BusinessException(String messege){
        super(messege);
    }
}
