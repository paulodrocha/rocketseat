package br.gov.mg.semad.rocketseat.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.gov.mg.semad.rocketseat.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{

    @Autowired

    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                var serveletPath = request.getServletPath();

                if(serveletPath.startsWith("/tasks/")){

                //Pegar a autenticação (usuário e senha), formato <Basic code>
                var authorization = request.getHeader("Authorization");
                
                //Separar o código da senha do Basic, formato <code>
                var authEncoded = authorization.substring("Basic".length()).trim();

                //vetor de bytes, decodificado
                byte[] authDecode = Base64.getDecoder().decode(authEncoded);

                //string decodificada, formato user:senha
                var authString = new String(authDecode);

                //separa senha e usuário, cada um em um índice de vetor
                String[] credentials = authString.split(":");
                String username = credentials[0];
                String password = credentials[1];
                
                //Validar usuário
                var user = this.userRepository.findByUsername(username);
                //Se usuário não está no banco, emitir mensagem de erro
                if(user == null){
                    response.sendError(401);
                } else {
                    //Validar senha
                    var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                    if(passwordVerify.verified){
                        //Passar o ID do usuário para a tabela tasks, sem ter que inserir no request
                        request.setAttribute("idUser", user.getId());
                        //Segue viagem
                        filterChain.doFilter(request, response);
                    } else {
                        response.sendError(401);
                    }
                }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
