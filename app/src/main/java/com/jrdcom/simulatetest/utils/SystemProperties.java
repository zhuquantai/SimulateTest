
package com.jrdcom.simulatetest.utils;

import java.lang.reflect.Method;

/**
 * Hacky way to call the hidden SystemProperties class API
 */
public class SystemProperties {

    private static final String TAG = "SysProps";

    /**
     * Gets system properties set by <code>adb shell setprop <em>key</em> <em>value</em></code>
     *
     * @param key the property key.
     * @param defaultValue the value to return if the property is undefined or empty (this parameter
     *            may be {@code null}).
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
     * @param key the property key.
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
}
