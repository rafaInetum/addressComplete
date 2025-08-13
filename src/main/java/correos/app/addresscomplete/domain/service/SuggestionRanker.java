package correos.app.addresscomplete.domain.service;

import correos.app.addresscomplete.domain.model.AddressInput;
import correos.app.addresscomplete.domain.model.AddressParts;
import correos.app.addresscomplete.domain.model.ScoredAddress;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class SuggestionRanker {

    private final JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();

    public List<String> sort(AddressInput input, List<ScoredAddress> suggestions) {
        return suggestions.stream()
                .sorted(Comparator.comparingInt(scored -> computeScore(input, scored.parts())))
                .map(ScoredAddress::formattedAddress)
                .toList();
    }

    private int computeScore(AddressInput input, AddressParts suggestion) {
        var fullLine = input.addressLines().stream().findFirst().orElse("").trim();
        String street = extractStreet(fullLine);
        String number = extractNumber(fullLine);

        int score = 0;

        double streetSim = similarity.apply(street, suggestion.street());
        if (streetSim > 0.9) score += AddressScoringWeights.STREET_EXACT;
        else if (streetSim > 0.75) score += AddressScoringWeights.STREET_HIGH_SIMILARITY;
        else if (streetSim > 0.5) score += AddressScoringWeights.STREET_MEDIUM_SIMILARITY;

        if (equalsIgnore(number, suggestion.number()))
            score += AddressScoringWeights.NUMBER_MATCH;

        if (equalsIgnore(input.postalCode(), suggestion.postalCode()))
            score += AddressScoringWeights.POSTAL_CODE_MATCH;

        if (equalsIgnore(input.locality(), suggestion.city()))
            score += AddressScoringWeights.CITY_MATCH;

        return -score;
    }

    private String extractStreet(String fullLine) {
        String[] tokens = fullLine.split(" ");
        return tokens.length > 1
                ? String.join(" ", List.of(tokens).subList(0, tokens.length - 1))
                : fullLine;
    }

    private String extractNumber(String fullLine) {
        String[] tokens = fullLine.split(" ");
        return tokens.length > 1 ? tokens[tokens.length - 1] : "";
    }

    private boolean equalsIgnore(String a, String b) {
        return a != null && b != null && a.equalsIgnoreCase(b);
    }
}
