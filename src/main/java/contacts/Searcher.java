package contacts;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Searcher {
    List<Contacts> contacts;
    String query;

    Searcher(List<Contacts> contacts, String query) {
        this.contacts = contacts;
        this.query = query;
    }

    LinkedHashMap<String, Integer> search() {
        LinkedHashMap<String, Integer> searchResults = new LinkedHashMap<>();
//        MatchResult matchResult;
        for (Contacts contact : contacts) {
            Pattern pattern = Pattern.compile(query, Pattern.CASE_INSENSITIVE);
            String[] allInfo = contact.toSearch().split(",\\s+");
            for (int i = 0; i < allInfo.length; i++) {
                String s = allInfo[i];
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
//                    matchResult = matcher.toMatchResult();
                    searchResults.put(contact.getFullName(), i);
                }
            }
        }
        return searchResults;
    }
}
