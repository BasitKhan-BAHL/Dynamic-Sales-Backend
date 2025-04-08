import org.mindrot.jbcrypt.BCrypt;

public class Main {
    public static void main(String[] args) {
        String plainPassword = "password123";
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        System.out.println("Hashed-password: " + hashedPassword);
    }
}