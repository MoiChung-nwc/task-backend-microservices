package com.chung.gateway.filter;

import com.chung.gateway.constants.MessageConstant;
import com.chung.gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * Global filter dùng để kiểm tra quyền truy cập các API private của các service thông qua JWT token.
 * <p>
 * Quy trình kiểm tra như sau:
 * <ul>
 *     <li>Bỏ qua nếu đường dẫn là public (được định nghĩa trong {@code PUBLIC_PATHS})</li>
 *     <li>Trích xuất token từ header Authorization</li>
 *     <li>Giải mã token để lấy danh sách vai trò (roles)</li>
 *     <li>Xác định service name dựa theo path thông qua cấu hình {@code pathServiceMap}</li>
 *     <li>Gửi request tới AuthService để xác minh vai trò có quyền truy cập không</li>
 * </ul>
 *
 * Nếu vai trò có quyền, request được tiếp tục chuyển tiếp tới service đích;
 * nếu không, trả về mã lỗi 403 FORBIDDEN.
 *
 * @author YourName
 * @see GlobalFilter
 * @see JwtUtil
 * @see WebClient
 */
@Component
public class AuthPermissionFilter implements GlobalFilter, Ordered {

    private final WebClient webClient;
    private final Map<String, String> pathServiceMap;
    private final JwtUtil jwtUtil;

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/v1/auth/register",
            "/api/v1/auth/authenticate",
            "/api/v1/auth/refresh-token"
    );

    public AuthPermissionFilter(
            JwtUtil jwtUtil,
            @Value("${auth.service.url}") String authServiceUrl,
            // Xác định service
            @Value("#{${gateway.path-service-map}}") Map<String, String> pathServiceMap) {

        this.jwtUtil = jwtUtil;
        this.webClient = WebClient.builder().baseUrl(authServiceUrl).build();
        this.pathServiceMap = pathServiceMap;
    }

    /**
     * Xử lý chính cho luồng filter toàn cục của Gateway.
     * Kiểm tra quyền của vai trò truy cập tài nguyên. Nếu đường dẫn là public thì bỏ qua,
     * nếu không thì kiểm tra quyền qua AuthService.
     *
     * @param exchange Đối tượng exchange chứa thông tin request/response
     * @param chain Chuỗi filter tiếp theo trong Gateway
     * @return {@code Mono<Void>} hoàn thành nếu request được phép tiếp tục, ngắt nếu không có quyền
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        HttpMethod method = request.getMethod();

        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        List<String> authHeaders = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (authHeaders == null || authHeaders.isEmpty()) {
            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeaders.get(0).replace("Bearer ", MessageConstant.EMPTY_STRING);
        List<String> roles = jwtUtil.getRolesFromToken(token);

        if (roles == null || roles.isEmpty()) {
            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        String serviceName = determineServiceName(path);

        return checkRolesPermission(serviceName, path, method.name(), roles)
                .flatMap(hasPermission -> {
                    if (hasPermission) {
                        return chain.filter(exchange);
                    } else {
                        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.FORBIDDEN);
                        return exchange.getResponse().setComplete();
                    }
                });
    }

    /**
     * Kiểm tra xem đường dẫn hiện tại có nằm trong danh sách public (không cần xác thực).
     *
     * @param path Đường dẫn request
     * @return {@code true} nếu là public path, {@code false} nếu là private
     */
    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

    /**
     * Dò tìm tên service tương ứng với request path dựa vào cấu hình pattern map.
     *
     * @param path Đường dẫn request
     * @return Tên service hoặc {@code MessageConstant.UNKNOWN_SERVICE} nếu không tìm thấy
     */
    private String determineServiceName(String path) {
        for (var entry : pathServiceMap.entrySet()) {
            String pattern = entry.getKey();
            if (pathMatcher.match(pattern, path)) {
                return entry.getValue();
            }
        }
        return MessageConstant.UNKNOWN_SERVICE;
    }

    /**
     * Chuẩn hóa tên vai trò bằng cách loại bỏ tiền tố "ROLE_" nếu tồn tại.
     *
     * @param role Tên vai trò thô
     * @return Vai trò sau khi chuẩn hóa
     */
    private String normalizeRole(String role) {
        if (role == null) return MessageConstant.EMPTY_STRING;
        return role.startsWith(MessageConstant.ROLE_PREFIX) ? role.substring(MessageConstant.ROLE_PREFIX_LENGTH) : role;
    }

    /**
     * Kiểm tra xem ít nhất một vai trò trong danh sách có quyền truy cập API không.
     *
     * @param serviceName Tên service đang yêu cầu
     * @param apiPath Đường dẫn API
     * @param httpMethod Phương thức HTTP (GET, POST, ...)
     * @param roles Danh sách vai trò người dùng
     * @return {@code Mono<Boolean>} {@code true} nếu có ít nhất một vai trò được phép
     */
    private Mono<Boolean> checkRolesPermission(String serviceName, String apiPath, String httpMethod, List<String> roles) {
        return Flux.fromIterable(roles)
                .map(this::normalizeRole)
                .flatMap(role -> callAuthService(serviceName, apiPath, httpMethod, role))
                .filter(Boolean::booleanValue)
                .hasElements();
    }

    /**
     * Gửi yêu cầu POST tới AuthService để kiểm tra quyền truy cập của một vai trò cụ thể.
     *
     * @param serviceName Tên service
     * @param apiPath Đường dẫn API
     * @param httpMethod Phương thức HTTP
     * @param role Vai trò đang được kiểm tra
     * @return {@code Mono<Boolean>} kết quả phản hồi từ AuthService: {@code true} nếu được phép
     */
    private Mono<Boolean> callAuthService(String serviceName, String apiPath, String httpMethod, String role) {
        var body = Map.of(
                MessageConstant.FIELD_SERVICE_NAME, serviceName,
                MessageConstant.FIELD_API_PATH, apiPath,
                MessageConstant.FIELD_HTTP_METHOD, httpMethod,
                MessageConstant.FIELD_ROLE, role
        );

        return webClient.post()
                .uri(MessageConstant.AUTH_CHECK_PERMISSION_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(e -> Mono.just(false));
    }

    /**
     * Xác định thứ tự chạy của filter trong chuỗi filter của Gateway.
     * Giá trị âm để chạy sớm hơn các filter khác.
     *
     * @return thứ tự ưu tiên (ví dụ: -1)
     */
    @Override
    public int getOrder() {
        return MessageConstant.FILTER_ORDER;
    }
}
