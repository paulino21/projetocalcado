package br.com.projetocalcado.infra.exception;

public class ValidacaoException extends RuntimeException{
        public ValidacaoException(String mensagem) {
            super(mensagem);
    }
}
