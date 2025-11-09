package metro_maua_pi.bimtrack_backend;

public class AuthResponse {
    private String token;
    private UserDetails user;

    public AuthResponse(String token, User user) {
        this.token = token;
        this.user = new UserDetails(user); 
    }
    
    public String getToken() {
        return token;
    }

    public UserDetails getUser() {
        return user;
    }

    public static class UserDetails {
        private String id;
        private String name;
        private String email;
        private User.UserRole role;

        public UserDetails(User user) {
            this.id = user.getId().toString();
            this.name = user.getName();
            this.email = user.getEmail();
            this.role = user.getRole();
        }
        
        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public User.UserRole getRole() {
            return role;
        }
    }
}