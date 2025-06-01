package com.chung.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lớp tiện ích để xử lý các thao tác liên quan đến JWT như trích xuất claims (thông tin trong token).
 */
@Component
public class JwtUtil {

    private SecretKey secretKey;

    /**
     * Tên của claim trong JWT chứa thông tin quyền (roles/authorities).
     */
    private static final String CLAIM_AUTHORITIES = "authorities";

    /**
     * Nhận giá trị khóa bí mật (secret key) từ file cấu hình ứng dụng.
     * Khóa này phải được mã hóa dưới dạng chuỗi hexa.
     *
     * @param hexKey khóa bí mật ở định dạng chuỗi hexa
     */
    @Value("${application.security.jwt.secret-key}")
    public void setSecretKey(String hexKey) {
        byte[] keyBytes = hexStringToByteArray(hexKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Trích xuất danh sách quyền (roles hoặc authorities) từ token JWT.
     *
     * @param token chuỗi JWT token
     * @return danh sách quyền, hoặc danh sách rỗng nếu không tìm thấy
     */
    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Object rolesClaim = claims.get(CLAIM_AUTHORITIES);

        if (rolesClaim instanceof List<?>) {
            return ((List<?>) rolesClaim).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }

        return List.of(); // Trả về danh sách rỗng nếu không có hoặc không đúng kiểu
    }

    /**
     * Chuyển đổi chuỗi hexa thành mảng byte.
     *
     * @param s chuỗi hexa
     * @return mảng byte tương ứng
     */
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) (
                    (Character.digit(s.charAt(i), 16) << 4)
                            + Character.digit(s.charAt(i + 1), 16)
            );
        }

        return data;
    }
}
