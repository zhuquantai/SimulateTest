
package com.jrdcom.simulatetest.utils;

import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * Hacky way to call the hidden SystemProperties class API
 */
public class SystemProperties {

    private static final String TAG = "SysProps";

    /**
     * Gets system properties set by <code>adb shell setprop <em>key</em> <em>value</em></code>
     *
     * @param key          the property key.
     * @param defaultValue the value to return if the property is undefined or empty (this parameter
     *                     may be {@code null}).
     * @return the system property value or the default value.
     */
    public static String get(String key, String defaultValue) {
        try {
            final Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            final Method get = systemProperties.getMethod("get", String.class, String.class);
            return (String) get.invoke(null, key, defaultValue);
        } catch (Exception e) {
            // This should never happen
            Log.e(TAG, "Exception while getting system property: ", e);
            return defaultValue;
        }
    }

    /**
     * Set system properties\
     * public static void set(String key, String val)
     *
     * @param key   the property key.
     * @param value the value to be set to the property key.
     * @return .
     */
    public static void set(String key, String value) {
        try {
            final Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            final Method set = systemProperties.getMethod("set", String.class, String.class);
            set.invoke(null, key, value);
        } catch (Exception e) {
            // This should never happen
            Log.e(TAG, "Exception while getting system property: ", e);
            return;
        }
    }

    /**
     * Get the value for the given key, returned as a boolean.
     * Values 'n', 'no', '0', 'false' or 'off' are considered false.
     * Values 'y', 'yes', '1', 'true' or 'on' are considered true.
     * (case sensitive).
     * If the key does not exist, or has any other value, then the default
     * result is returned.
     *
     * @param key the key to lookup
     * @param def a default value to return
     * @return the key parsed as a boolean, or def if the key isn't found or is
     * not able to be parsed as a boolean.
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    public static boolean getBoolean(String key, boolean def) {
        try {
            final Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            final Method get = systemProperties.getMethod("get", String.class, String.class);
            //return (Boolean) get.invoke(null, key, def);
            String value = (String) get.invoke(null, key, def ? "true" : "false");
            if (!TextUtils.isEmpty(value) && ("true".equals(value) || "1".equals(value) || "y".equals(value) || "on".equals(value))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // This should never happen
            Log.e(TAG, "Exception while getting system property: ", e);
            return def;
        }
    }

}
