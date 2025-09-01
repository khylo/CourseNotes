package string;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {
    public Stream<String> getIds(String testString){
        return findIdsRegexStream(testString);
    }
    public List<String> getListIds(String testString){
        return findIdsRegex(testString);
    }

    private Stream<String> findIdsRegexStream(String testString) {
        Pattern p = Pattern.compile("ID\\d{4,6}");
        var m = p.matcher(testString);
        return m.results().map(mr -> mr.group());
    }

    private List<String> findIdsRegex(String testString) {
        Pattern p = Pattern.compile("ID\\d{4,6}");
        var m = p.matcher(testString);
        return m.results().map(mr-> mr.group()).collect(Collectors.toList());
        /*m.find();
        List<String> ret = new ArrayList<>();
        while (m.find()) {
            ret.add(m.group());
        }
        return ret;
        */
        /*
        int idCount = m.groupCount();
        List<String> ret = new ArrayList<>(idCount);
        for(int i=0;i<idCount;i++){
            ret.add(m.group(i));
        }
        return ret;
        */

    }
}
