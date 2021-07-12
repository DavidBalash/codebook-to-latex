import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProcessCodebook {

    private static final String codebookFile = "codebook.csv";

    public static void main(String[] args) {
        ProcessCodebook processCodebook = new ProcessCodebook();
        processCodebook.process();
    }

    public void process() {
        Map<String, Primary> codebook = new HashMap<>();

        try (Stream<String> stream = Files.lines(Paths.get(codebookFile), StandardCharsets.UTF_8)) {
            // For each line in the codebook file.
            for (String line : stream.collect(Collectors.toList())) {

                String[] split = line.trim().split(",");
                String codeName = split[0];
                //System.out.println(codeName);
                int frequency = Integer.parseInt(split[1]);

                // Check if secondary code
                if (codeName.contains("->")) {
                    String[] splitCodes = codeName.trim().split("->");
                    if (splitCodes.length > 2) {
                        continue;
                    }
                    String primaryCodeName = splitCodes[0];
                    String secondaryCodeName = splitCodes[1];
                    Primary primary = codebook.get(primaryCodeName);
                    if (primary == null) {
                        System.out.println(primaryCodeName + " IS NULL");
                    }
                    Map<String, Code> secondaryCodebook = primary.getSecondaryCodebook();
                    if (secondaryCodebook.containsKey(secondaryCodeName)) {
                        Code code = secondaryCodebook.get(secondaryCodeName);
                        code.setFrequency(code.getFrequency() + frequency);
                    } else {
                        secondaryCodebook.put(secondaryCodeName, new Code(secondaryCodeName, frequency));
                    }

                } else {
                    if (codebook.containsKey(codeName)) {
                        Primary primary = codebook.get(codeName);
                        Code code = primary.getPrimaryCode();
                        code.setFrequency(code.getFrequency() + frequency);
                    } else {
                        codebook.put(codeName, new Primary(new Code(codeName, frequency)));
                    }
                }
            }
        } catch (final IOException ioException) {
            ioException.printStackTrace();
        }



        Set<String> codeSet = codebook.keySet();

        ArrayList<Primary> primaryList = new ArrayList<>();

        for (String code : codeSet) {
            primaryList.add(codebook.get(code));
        }

        Collections.sort(primaryList, Collections.reverseOrder());


        for (Primary primary : primaryList) {
            System.out.println("\\item\\textbf{" + primary.getPrimaryCode().getCodeName().replace("_", "-") + " (" + primary.getPrimaryCode().getFrequency() + ")}");
            Set<String> secondaryCodeSet = primary.getSecondaryCodebook().keySet();

            ArrayList<Code> secodaryList = new ArrayList<>();
            for (String code : secondaryCodeSet) {
                secodaryList.add(primary.getSecondaryCodebook().get(code));
            }

            Collections.sort(secodaryList, Collections.reverseOrder());

            StringBuffer stringBuffer = new StringBuffer();
            for (Code secondary : secodaryList) {
                stringBuffer.append(secondary.getCodeName().replace("_", "-") + " (" + secondary.getFrequency() + "), ");
            }
            if (stringBuffer.toString().length() > 0) {
                System.out.println();
                System.out.println("\\emph{" + stringBuffer.toString().substring(0, stringBuffer.toString().lastIndexOf(',')) + "}");
            }
            System.out.println();
        }
    }
}
