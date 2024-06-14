package gyuho.triptogether.domain.user.entity;

public enum Role {
    USER("USER"), ADMIN("ADMIN");
    private String name;
    private Role(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
