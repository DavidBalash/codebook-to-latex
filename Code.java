import java.util.Objects;

public class Code implements Comparable<Code> {
    private String codeName;
    private Integer frequency;

    public Code(final String codeName, final Integer frequency) {
        this.codeName = codeName;
        this.frequency = frequency;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Code code = (Code) o;
        return Objects.equals(codeName, code.codeName) &&
                Objects.equals(frequency, code.frequency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeName, frequency);
    }

    @Override
    public String toString() {
        return "Code{" +
                "codeName='" + codeName + '\'' +
                ", frequency=" + frequency +
                '}';
    }

    @Override
    public int compareTo(Code code) {
        return this.getFrequency().compareTo(code.getFrequency());
    }
}
