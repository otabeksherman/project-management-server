package projectManagement.utils;

import java.time.Instant;
import java.util.UUID;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class Utils {
    public static String generateUniqueToken() {
        StringBuilder token = new StringBuilder();
        long currentTimeInMillisecond = Instant.now().toEpochMilli();

        return token.append(currentTimeInMillisecond).append("-")
                .append(UUID.randomUUID()).toString();
    }

    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public static boolean verifyPassword(String passwordFromUser, String PasswordFromDB) {
        BCrypt.Result result = BCrypt.verifyer().verify(passwordFromUser.toCharArray(),
                PasswordFromDB.toCharArray());

        return result.verified;
    }
}
