package MOCO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Node {
    String nodeId;
    String content;
    public Node(){}

    public Node(String nodeId, String content) {
        this.nodeId = nodeId;
        this.content = content;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Node{" +
                "\"nodeId\":\"" + nodeId + '\"' +
                ", \"content\":\"" + content + '\"' +
                '}';
    }


    public static Node fromString(String input) {
//        System.out.println(input);
        JSONObject json = JSON.parseObject(input.replace("Node", ""));
        return new Node(json.get("nodeId").toString(), json.get("content").toString());
    }
}

