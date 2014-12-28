package pl.slowly.team.common.packets.helpers;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Credentials implements Serializable {
    private String user;
    private String passwordHash;

    public Credentials(String user, String password) throws UnsupportedEncodingException {
        this.user = user;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bytesOfPassword = password.getBytes("UTF-8");
            messageDigest.update(bytesOfPassword);
            byte[] digest = messageDigest.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String passwordHash = bigInt.toString(16);
            while (passwordHash.length() < 32) {
                passwordHash = "0" + passwordHash;
            }
            this.passwordHash = passwordHash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getUsername() {
        return user;
    }
}
