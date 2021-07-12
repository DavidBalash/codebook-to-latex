import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Primary implements Comparable<Primary> {
    private Code primaryCode;
    private Map<String, Code> secondaryCodebook;

    public Primary(final Code primaryCode) {
        this.primaryCode = primaryCode;
        this.secondaryCodebook = new HashMap<>();
    }

    public Primary(final Code primaryCode, Map<String, Code> secondaryCodebook) {
        this.primaryCode = primaryCode;
        this.secondaryCodebook = secondaryCodebook;
    }

    public Code getPrimaryCode() {
        return primaryCode;
    }

    public void setPrimaryCode(Code primaryCode) {
        this.primaryCode = primaryCode;
    }

    public Map<String, Code> getSecondaryCodebook() {
        return secondaryCodebook;
    }

    public void setSecondaryCodebook(Map<String, Code> secondaryCodebook) {
        this.secondaryCodebook = secondaryCodebook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Primary primary = (Primary) o;
        return Objects.equals(primaryCode, primary.primaryCode) &&
                Objects.equals(secondaryCodebook, primary.secondaryCodebook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryCode, secondaryCodebook);
    }

    @Override
    public String toString() {
        return "Primary{" +
                "primaryCode=" + primaryCode +
                ", secondaryCodebook=" + secondaryCodebook +
                '}';
    }

    @Override
    public int compareTo(Primary primary) {
        return this.getPrimaryCode().getFrequency().compareTo(primary.getPrimaryCode().getFrequency());
    }
}
