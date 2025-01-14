package school.hei.identity.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @GetMapping("/")
    public ResponseEntity<Void> publicEndpoint() {
        String firebaseAuthUiUrl = "https://identity-983fa.firebaseapp.com";
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, firebaseAuthUiUrl)
                .build();
    }

    @GetMapping("/private")
    public ResponseEntity<String> privateEndpoint(@RequestParam("token") String token) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String uid = decodedToken.getUid();
            return ResponseEntity.ok("Hello, authenticated user! Your UID is: " + uid);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized: Invalid or expired token.");
        }
    }
}
