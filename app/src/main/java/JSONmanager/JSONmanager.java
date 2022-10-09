package JSONmanager;

import com.google.gson.Gson;

public class JSONmanager {
    Gson JSONmanager = new Gson();
    Exception error;
    public String getJSON(Object object){
        try{
            String json = JSONmanager.toJson(object);
            return json;
        } catch (Exception e) {
            error = e;
            return "";
        }
    }

    public Object getObject(String json, Object object){
        try{
            if(JSONmanager != null){
                return JSONmanager.fromJson(json, object.getClass());
            } else{
                return null;
            }
        } catch (Exception e){
            error = e;
            return null;
        }
    }

    public String getObject(String json){
        try{
            if(JSONmanager != null){
                return JSONmanager.fromJson(json, String.class).toString();
            } else{
                return "";
            }
        } catch (Exception e){
            error = e;
            return "";
        }
    }
}
