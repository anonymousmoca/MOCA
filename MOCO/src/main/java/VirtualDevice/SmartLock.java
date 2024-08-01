package VirtualDevice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SmartLock {
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean locked;
    private String password;

    public SmartLock() {
        this.locked = false; // default locked
        this.password = "1234"; // default password
    }

    public SmartLock(boolean locked, String password) {
        this.locked = locked;
        this.password = password;
    }

    public String lock() {
        if (!locked) {
            locked = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String unlock(String inputPassword) {
        if (locked && inputPassword.equals(password)) {
            locked = false;
            return "success";
        } else {
            return "skip";
        }
    }

    public String setPassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(password)) {
            this.password = newPassword;
            return "success";
        } else {
            return "skip";
        }
    }

    public String toSystemString() {
        return "SmartLock{" +
                "'locked':" + locked +
                "}";
    }

    @Override
    public String toString() {
        return "SmartLock{" +
                "'locked':" + locked +
                ", 'password':'" + password +
                "'}";
    }

    public static SmartLock fromString(String state) {
        JSONObject json = JSON.parseObject(state.replace("SmartLock", ""));
        return new SmartLock(json.getBoolean("locked"), json.getString("password"));
    }

    public static void main(String[] args) {
        SmartLock lock = new SmartLock();
        LOGGER.info("Initial State: " + lock.toString());

        // Test locking
        LOGGER.info("Lock: " + lock.lock());
        LOGGER.info("State after locking: " + lock.toString());

        // Test unlocking
        LOGGER.info("Unlock with wrong password: " + lock.unlock("wrongpassword"));
        LOGGER.info("Unlock with correct password: " + lock.unlock("1234"));
        LOGGER.info("State after unlocking: " + lock.toString());

        // Test setting new password
        LOGGER.info("Set new password with wrong old password: " + lock.setPassword("wrongpassword", "5678"));
        LOGGER.info("Set new password with correct old password: " + lock.setPassword("1234", "5678"));
        LOGGER.info("State after setting new password: " + lock.toString());

        // Test re-locking
        lock.lock();
        LOGGER.info("State after re-locking: " + lock.toString());
        LOGGER.info("Unlock with new password: " + lock.unlock("5678"));
        LOGGER.info("State after unlocking with new password: " + lock.toString());

        // Test serialization
        String content = lock.toString();
        SmartLock restoredLock = SmartLock.fromString(content);
        LOGGER.info("Restored SmartLock: " + restoredLock.toString());
    }
}
