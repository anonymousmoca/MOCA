package MOCO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Edge {
    String source;
    String target;
    String api;

    public Edge(String source, String target, String api) {
        this.source = source;
        this.target = target;
        this.api = api;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public String getApi() {
        return api;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", api='" + api + '\'' +
                '}';
    }

    public Edge fromString(String input){
        JSONObject json = JSON.parseObject(input.replace("Edge", ""));
        return new Edge(json.getString("source"), json.getString("target"), json.getString("api"));
    }
}

