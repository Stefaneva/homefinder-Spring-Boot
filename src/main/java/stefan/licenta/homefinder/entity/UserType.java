package stefan.licenta.homefinder.entity;

public enum UserType {
    ADMIN ("ADMIN"),
    USER("CUMPARATOR/CHIRIAS"),
    AGENT_IMOBILIAR("AGENT IMOBILIAR");

    private final String name;

    UserType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
