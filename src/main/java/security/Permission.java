package security;

public enum Permission {
    BUSINESSADMIN(Types.BUSINESSADMIN),
    BUSINESSACCOUNT(Types.BUSINESSACCOUNT),
    FOOCLESCOUT(Types.FOOCLESCOUT);

    public class Types{
        public static final String BUSINESSADMIN = "BUSINESSADMIN";
        public static final String BUSINESSACCOUNT = "BUSINESSACCOUNT";
        public static final String FOOCLESCOUT = "FOOCLESCOUT";
    }

    private final String value;

    Permission(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
