import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.*;


class LoadFromDB {

    private String path;
    private Category[] categories = Category.getCategories();
    private Map<Category, JSONObject> categContentRaw;

    public LoadFromDB(String path) {
        this.path = path;
        categContentRaw = new HashMap<>();
        JSONParser parser = new JSONParser();
        try { 
            Object obj = parser.parse(new FileReader(path));
            JSONObject content = new JSONObject();
            content = (JSONObject) obj;
            for (Object key : content.keySet()) {
                JSONObject value = (JSONObject) content.get(key);
                // out.println("key: "+key+" value: "+value);
                // out.println("");
                Category c = Category.getCategoryFromFileName(key.toString());
                categContentRaw.put(c, value);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }   
    }

    public Map<Integer, List<String>> getCategoryContentMap(Category c) {
        Map<Integer, List<String>> aux = new HashMap<>();
        JSONObject content = categContentRaw.get(c);

        for (Object key : content.keySet()) {
            List<String> auxList = new ArrayList<>();
            JSONArray bookInfo = (JSONArray) content.get(key);
            for (int i=0; i<bookInfo.size(); i++) {
                auxList.add(bookInfo.get(i).toString());
            }
            aux.put(Integer.valueOf((String) key), auxList);
        }
        return aux;
    }
}