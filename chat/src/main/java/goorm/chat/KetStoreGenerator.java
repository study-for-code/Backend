//package goorm.chat;
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.IOException;
//
//@Component
//public class KeyStoreGenerator {
//
//    @PostConstruct
//    public void init() {
//        try {
//            String keyStorePath = "src/main/resources/keystore.p12";
//            File keyStoreFile = new File(keyStorePath);
//
//            // 키스토어 파일이 존재하지 않으면 생성
//            if (!keyStoreFile.exists()) {
//                ProcessBuilder processBuilder = new ProcessBuilder(
//                        "keytool",
//                        "-genkeypair",
//                        "-alias", "tomcat",
//                        "-keyalg", "RSA",
//                        "-keysize", "2048",
//                        "-storetype", "PKCS12",
//                        "-keystore", keyStorePath,
//                        "-validity", "3650",
//                        "-dname", "CN=Your Name, OU=Your Organizational Unit, O=Your Organization, L=Your City, ST=Your State, C=Your Country Code",
//                        "-storepass", "password"
//                );
//                processBuilder.inheritIO();
//                Process process = processBuilder.start();
//                process.waitFor();
//                System.out.println("KeyStore generated successfully.");
//            } else {
//                System.out.println("KeyStore already exists, skipping generation.");
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}