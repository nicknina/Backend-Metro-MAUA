package metro_maua_pi.bimtrack_backend;

import metro_maua_pi.bimtrack_backend.ChangePasswordRequest;
import metro_maua_pi.bimtrack_backend.UpdateAccountRequest;
import metro_maua_pi.bimtrack_backend.UserRepository;
import metro_maua_pi.bimtrack_backend.AuthResponse; 
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user") 
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PutMapping("/update-account")
    public ResponseEntity<?> updateAccount(
            @RequestBody UpdateAccountRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Optional<User> userWithNewEmail = userRepository.findByEmail(request.getEmail());
        if (userWithNewEmail.isPresent() && !userWithNewEmail.get().getId().equals(user.getId())) {
            return ResponseEntity.status(400).body("Este email já está em uso.");
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        User updatedUser = userRepository.save(user);

        return ResponseEntity.ok(new AuthResponse(null, updatedUser));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.status(400).body("Senha atual incorreta");
        }

    
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.status(400).body("As novas senhas não coincidem");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Senha alterada com sucesso");
    }
}