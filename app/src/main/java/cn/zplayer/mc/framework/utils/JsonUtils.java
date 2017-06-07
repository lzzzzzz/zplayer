package cn.zplayer.mc.framework.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/3 0003.
 */
public class JsonUtils {

    /**
     * 是否jsonArray
     *
     * @param jsonString
     * @return
     */
    public static boolean isJsonArray(String jsonString) {
        if (jsonString != null) {
            jsonString = jsonString.trim().replaceAll("&quot;", "\"");
            if (jsonString.startsWith("[") && jsonString.endsWith("]")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否json
     *
     * @param jsonString
     * @return
     */
    public static boolean isJsonObject(String jsonString) {
        if (jsonString != null) {
            jsonString = jsonString.trim().replaceAll("&quot;", "\"");
            if (jsonString.startsWith("{") && jsonString.endsWith("}")) {
                return true;
            }
        }
        return false;
    }


    /**
     * Map 转化 json
     *
     * @param object
     * @return
     * @throws JSONException
     */
    public static Object toJSON(Object object) throws JSONException {
        if (object instanceof Map) {
            JSONObject json = new JSONObject();
            Map map = (Map) object;
            for (Object key : map.keySet()) {
                json.put(key.toString(), toJSON(map.get(key)));
            }
            return json;
        } else if (object instanceof Iterable) {
            JSONArray json = new JSONArray();
            for (Object value : ((Iterable) object)) {
                json.put(value);
            }
            return json;
        } else {
            return object;
        }
    }

    /**
     * JSONObject 是否为空
     *
     * @param object
     * @return
     */
    public static boolean isEmptyObject(JSONObject object) {

        return object.names() == null;
    }

    public static Map<String, Object> getMap(JSONObject object, String key)
            throws JSONException {
        return toMap(object.getJSONObject(key));
    }

    public static Map<String, Object> toMap(JSONObject object)
            throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();
        Iterator keys = object.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            map.put(key, fromJson(object.get(key)));
        }
        return map;
    }

    /**
     * ObjectJson转化List
     *
     * @param array
     * @return
     * @throws JSONException
     */
    public static List<Object> toList(JSONArray array) throws JSONException {

        List<Object> list = new ArrayList<Object>();
        int length = array.length();
        for (int i = 0; i < length; i++) {
            list.add(fromJson(array.get(i)));
        }
        return list;
    }

    private static Object fromJson(Object json) throws JSONException {
        if (json == JSONObject.NULL) {
            return null;
        } else if (json instanceof JSONObject) {
            return toMap((JSONObject) json);
        } else if (json instanceof JSONArray) {
            return toList((JSONArray) json);
        } else {
            return json;
        }
    }
}
