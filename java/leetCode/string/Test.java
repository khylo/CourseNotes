package string;

import java.util.List;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {

        String testString = "jvbae7y5 234$%!%ID56747823423423ljk423i4wkfdslbvsfg ID23423423sdf;lakdnfan;anv;zn  234jo4h5jID435345634563lsdkna; bna;n n ID23423423dakjaf bwf;jb wpnd;jnb awebn;kdjbvb\skdjbnek;jbrID23234323sdfas 234j2n54t8^&UFDaer56$% ";

        Solution sol = new Solution();
        var ans = sol.getIds(testString);
        System.out.println(String.format("Called getIds, size = %d, ans = %s",ans.count(), l2s(ans)));
        assert ans instanceof List;
        assert 1==2;
    }
    public static void mainList(String[] args) {

        String testString = "jvbae7y5 234$%!%ID56747823423423ljk423i4wkfdslbvsfg ID23423423sdf;lakdnfan;anv;zn  234jo4h5jID435345634563lsdkna; bna;n n ID23423423dakjaf bwf;jb wpnd;jnb awebn;kdjbvb\skdjbnek;jbrID23234323sdfas 234j2n54t8^&UFDaer56$% ";

        Solution sol = new Solution();
        var ans = sol.getListIds(testString);
        System.out.println(String.format("Called getIds, size = %d, ans = %s",ans.size(),l2s(ans)));
        assert ans instanceof List;
        assert 1==2;
    }
    static String l2s(List<String> s){
        StringBuilder sb = new StringBuilder();
        s.forEach(c -> sb.append(c+" "));
        return sb.toString();
    }
    static String l2s(Stream<String> s){
        StringBuilder sb = new StringBuilder();
        s.forEach(c -> sb.append(c+" "));
        return sb.toString();
    }
}
