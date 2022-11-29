package security;

public enum Permission {
    FOOCLESCOUT(Types.FOOCLESCOUT),
    FOOCLEBUSINESS(Types.FOOCLEBUSINESS),
    BUSINESSADMIN(Types.BUSINESSADMIN);

    public class Types{
        public static final String FOOCLESCOUT = "FOOCLESCOUT";
        public static final String FOOCLEBUSINESS = "FOOCLEBUSINESS";
        public static final String BUSINESSADMIN = "BUSINESSADMIN";
    }

    private final String value;

    Permission(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
