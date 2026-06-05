package model.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static Properties properties = new Properties();

    static {
        try {
            System.out.println("ClassLoader:");
            System.out.println(DatabaseConfig.class.getClassLoader());

            System.out.println("Recurso 1:");
            System.out.println(DatabaseConfig.class.getResource("/db.properties"));

            System.out.println("Recurso 2:");
            System.out.println(DatabaseConfig.class.getClassLoader().getResource("db.properties"));

            InputStream inputStream =
                    DatabaseConfig.class.getResourceAsStream("/db.properties");

            System.out.println("InputStream = " + inputStream);

            if (inputStream == null) {
                throw new IOException("Arquivo 'db.properties' não encontrado");
            }

            properties.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            InputStream inputStream = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties");
            if (inputStream == null) {
                throw new IOException("Arquivo 'db.properties' não encontrado");
            }

            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDbUrl() {
        return properties.getProperty("db.url");
    }

    public static String getDbUsername() {
        return properties.getProperty("db.username");
    }

    public static String getDbPassword() {
        return properties.getProperty("db.password");

    }
}