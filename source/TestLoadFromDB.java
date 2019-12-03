import java.util.List;
import java.util.Map;

public class TestLoadFromDB {

    public static void main(String[] args) {
        LoadFromDB loader = new LoadFromDB("../data_base.json");
        Map<Integer, List<String>> map = loader.getCategoryContentMap(Category.KIDS);
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            int id = entry.getKey();
            List<String> info = entry.getValue();
            System.out.println("ID: "+id);
            System.out.println("Content:");
            for (int i=0; i<info.size(); i++) {
                System.out.println("->"+info.get(i));
            }
        }
    }
}