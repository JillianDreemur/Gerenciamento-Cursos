package appGerente.GerenciamentoCursos.utils;

import java.util.regex.Pattern;

public class FormatadorUtil {
    public static String formatarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return nome;
        }
        
        String[] palavras = nome.trim().split("\\s+");
        StringBuilder nomeFormatado = new StringBuilder();
        
        for (int i = 0; i < palavras.length; i++) {
            if (palavras[i].length() > 0) {
                String primeiraLetra = palavras[i].substring(0, 1).toUpperCase();
                String resto = palavras[i].length() > 1 ? palavras[i].substring(1).toLowerCase() : "";
                nomeFormatado.append(primeiraLetra).append(resto);
                
                if (i < palavras.length - 1) {
                    nomeFormatado.append(" ");
                }
            }
        }
        
        return nomeFormatado.toString();
    }
    public static String formatarCPF(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return cpf;
        }
        
        String apenasNumeros = cpf.replaceAll("[^0-9]", "");
        
        if (apenasNumeros.length() != 11) {
            throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos");
        }
        
        return String.format("%s.%s.%s-%s",
                apenasNumeros.substring(0, 3),
                apenasNumeros.substring(3, 6),
                apenasNumeros.substring(6, 9),
                apenasNumeros.substring(9, 11));
    }

    public static String formatarTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            return telefone;
        }
        
        String apenasNumeros = telefone.replaceAll("[^0-9]", "");
        
        if (apenasNumeros.length() != 11) {
            throw new IllegalArgumentException("Telefone deve conter exatamente 11 dígitos (DDD + número)");
        }
        
        return String.format("(%s) %s %s-%s",
                apenasNumeros.substring(0, 2),
                apenasNumeros.substring(2, 3),
                apenasNumeros.substring(3, 7),
                apenasNumeros.substring(7, 11));
    }

    public static void validarEmailGmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        
        String emailLower = email.toLowerCase().trim();
        
        if (!emailLower.endsWith("@gmail.com")) {
            throw new IllegalArgumentException("Email deve ser do domínio @gmail.com");
        }
    
        String regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        if (!Pattern.matches(regex, emailLower)) {
            throw new IllegalArgumentException("Email inválido. Use apenas @gmail.com");
        }
    }
}

