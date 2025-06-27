package com.rhsystem.api.rhsystemapi.infrastructure.filters;

// ... outras importações ...

import com.rhsystem.api.rhsystemapi.infrastructure.security.TokenExtractor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    // Lista de rotas públicas (pode ser injetada de um arquivo de propriedades ou definida aqui)
    private static final String[] PUBLIC_ROUTES = {
            "/api/auth/**",
            "/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**"
    };

    private final UserDetailsService userDetailsService;
    private final TokenExtractor extractor;
    private final AntPathMatcher pathMatcher = new AntPathMatcher(); // Para comparar os padrões de rota

    public AuthenticationFilter(UserDetailsService userDetailsService, TokenExtractor extractor) {
        this.userDetailsService = userDetailsService;
        this.extractor = extractor;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // **INÍCIO DA CORREÇÃO**
        // Se a rota da requisição for uma das rotas públicas, pula o filtro e continua.
        if (isPublicRoute(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        // **FIM DA CORREÇÃO**

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // O resto do seu código permanece igual...
        final String jwt = authHeader.substring(7);
        try {
            final String username = extractor.extractUserName(jwt);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            // Este erro agora só acontecerá em rotas protegidas com token inválido,
            // e será tratado pelo seu ApplicationAuthenticationEntryPoint.
            // Para simplificar, podemos deixar o Spring Security lidar com isso.
            SecurityContextHolder.clearContext(); // Limpa o contexto em caso de erro.
        }

        filterChain.doFilter(request, response);
    }


    private boolean isPublicRoute(String uri) {
        return Arrays.stream(PUBLIC_ROUTES).anyMatch(route -> pathMatcher.match(route, uri));
    }
}