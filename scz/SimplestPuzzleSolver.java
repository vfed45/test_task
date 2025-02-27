import java.util.*;
import java.nio.file.*;

public class SimplestPuzzleSolver {
    public static void main(String[] args) throws Exception {
        List<String> fragments = Files.readAllLines(Paths.get("source.txt"));
        List<String> longestSequence = new ArrayList<>();

        for (String fragment : fragments) {
            List<String> usedFragments = new ArrayList<>();
            List<String> currentSequence = new ArrayList<>();
            usedFragments.add(fragment);
            currentSequence.add(fragment);
            buildSequence(fragment, fragments, usedFragments, currentSequence);

            if (currentSequence.size() > longestSequence.size()) {
                longestSequence = new ArrayList<>(currentSequence);
            }
        }

        String result = concatenateFragments(longestSequence);
        System.out.println("Найдовша послідовність: " + result);
    }

    static void buildSequence(String currentFragment, List<String> fragments, List<String> usedFragments, List<String> currentSequence) {
        boolean found = false;
        for (String fragment : fragments) {
            if (!usedFragments.contains(fragment)) {
                if (currentFragment.substring(currentFragment.length() - 2).equals(fragment.substring(0, 2))
                        || currentFragment.substring(0, 2).equals(fragment.substring(fragment.length() - 2))) {

                    usedFragments.add(fragment);
                    currentSequence.add(fragment);
                    buildSequence(fragment, fragments, usedFragments, currentSequence);
                    found = true;
                    break; // Жадібно беремо перший знайдений фрагмент
                }
            }
        }
        if (!found) {
            // Кінець послідовності
            return;
        }
    }

    static String concatenateFragments(List<String> sequence) {
        if (sequence.isEmpty()) return "";
        StringBuilder sb = new StringBuilder(sequence.get(0));

        for (int i = 1; i < sequence.size(); i++) {
            String prev = sequence.get(i - 1);
            String curr = sequence.get(i);

            if (prev.substring(prev.length() - 2).equals(curr.substring(0, 2))) {
                sb.append(curr.substring(2));
            } else if (prev.substring(0, 2).equals(curr.substring(curr.length() - 2))) {
                sb.insert(0, curr.substring(0, curr.length() - 2));
            } else {
                sb.append(curr);
            }
        }
        return sb.toString();
    }
}
