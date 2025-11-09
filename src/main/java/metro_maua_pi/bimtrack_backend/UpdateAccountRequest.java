package metro_maua_pi.bimtrack_backend;

import lombok.Data;

@Data 
public class UpdateAccountRequest {
    private String name;
    private String email;
}
