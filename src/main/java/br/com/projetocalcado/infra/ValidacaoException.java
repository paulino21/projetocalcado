package br.com.projetocalcado.infra;

public class ValidacaoException extends RuntimeException{
        public ValidacaoException(String mensagem) {
            super(mensagem);
    }
}
