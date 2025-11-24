package com.doubleshan.scenery.security;

public class SecurityContext {
    private static final ThreadLocal<JwtUtil.JwtUser> CTX = new ThreadLocal<>();

    public static void set(JwtUtil.JwtUser user) {
        CTX.set(user);
    }

    public static JwtUtil.JwtUser get() {
        return CTX.get();
    }

    public static String getUserId() {
        JwtUtil.JwtUser u = CTX.get();
        return u == null ? null : u.userId;
    }

    public static String getRole() {
        JwtUtil.JwtUser u = CTX.get();
        return u == null ? null : u.role;
    }

    public static void clear() {
        CTX.remove();
    }
}
