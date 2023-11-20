package com.exerciseapp.myapp.utils.system;

public class SecurityContextHolder {

    private static ThreadLocal<SystemContext> instant = new ThreadLocal<>();

    public static void create(String userId) {
        SystemContext holder = new SystemContext();
        holder.setUserId(userId);
        instant.set(holder);
    }

    public static ThreadLocal<SystemContext> getCurrentSystem() {
        return instant;
    }

    public static String getCurrentUserId() {
        SystemContext context = getCurrentSystem().get();
        if (context != null) {
            return context.getUserId();
        }
        return null;
    }
}
