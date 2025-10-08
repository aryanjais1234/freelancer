package com.gateway.api_gateway.security;
import com.gateway.api_gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilter;

import java.util.Objects;


@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

//    @Autowired
//    private RestTemplate template;

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthFilter(){
        super(Config.class);
        System.out.println("AuthenticationFilter triggered for: ");
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            if (path.startsWith("/users/register") || path.startsWith("/users/login")) {
                System.out.println("===========Auth hit============");
                return chain.filter(exchange);
            }
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Missing authorization header");
            }

            if (validator.isSecured.test(exchange.getRequest())) {

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing authorization header");
                }

                String authHeader = Objects.requireNonNull(
                        exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)
                ).get(0);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                    System.out.println("token : "+ authHeader);
                }

                try {
                    jwtUtil.validateToken(authHeader);

                    // ✅ Extract the role and username from the token
                    String role = jwtUtil.extractRole(authHeader);
                    String username = jwtUtil.extractUsername(authHeader);
                    Long userId = jwtUtil.extractUserId(authHeader);
                    System.out.println("======"+role);
                    System.out.println("User Role from JWT: " + role);
                    System.out.println("Username from JWT: " + username);
                    System.out.println("UserId from JWT: " + userId);

                    // Add username to request headers for downstream services
                    exchange = exchange.mutate()
                            .request(r -> r.headers(httpHeaders -> {
                                httpHeaders.add("username", username);
                                httpHeaders.add("userId", String.valueOf(userId));
                            }))
                            .build();


                    // ✅ Optional: restrict endpoint based on role
                    if (path.startsWith("/clients/") || path.startsWith("/clients/createProject/")) {
                        System.out.println("role: {}"+role);
                        if (!"ROLE_CLIENT".equalsIgnoreCase(role)) {
                            throw new RuntimeException("Access Denied: Only Clients can access this route");
                        }
                    }

                    if (path.startsWith("/freelancers/")) {
                        if (!"ROLE_FREELANCER".equalsIgnoreCase(role)) {
                            throw new RuntimeException("Access Denied: Only freelancer can access this route");
                        }
                    }
                    if (path.startsWith("/projects/getProject/")) {
                        if (!"ROLE_FREELANCER".equalsIgnoreCase(role)) {
                            throw new RuntimeException("Access Denied: Only freelancer can access this route");
                        }
                    }
                    if (path.startsWith("/quiz/create")) {
                        if (!"ROLE_TEACHER".equalsIgnoreCase(role)) {
                            throw new RuntimeException("Access Denied: Only teachers can create quizzes");
                        }
                    }

                } catch (Exception e) {
                    throw new RuntimeException("Unauthorized access: " + e.getMessage());
                }
            }

            return chain.filter(exchange);
        });
    }


    public static class Config{

    }


}