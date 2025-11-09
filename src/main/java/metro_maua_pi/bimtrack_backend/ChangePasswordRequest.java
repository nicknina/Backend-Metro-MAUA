package metro_maua_pi.bimtrack_backend;

import lombok.Data;

@Data 
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

}