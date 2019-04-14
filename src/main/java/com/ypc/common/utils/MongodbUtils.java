package com.ypc.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongodbUtils {
    private static final String MONGO_HOST = "192.168.188.130";
    private static final Integer MONGO_PORT = 27017;
    private static final String MONGO_USERNAME = "test";
    private static final String MONGO_PASSWORD = "1234";
    private static final String MONGO_DB_NAME = "DOClever";
    private static final String MONGO_COLLECTION_NAME = "interfaces";

    public static MongoCollection<Document> getCollection(String host){
        if(StringUtils.isEmpty(host)){
            host = MONGO_HOST;
        }
        MongoCollection<Document> collection = null;
        try {
            // To connect to mongodb server
            MongoClient mongoClient = new MongoClient(host, MONGO_PORT);
            // Now connect to your databases
            MongoDatabase mgdb = mongoClient.getDatabase(MONGO_DB_NAME);
            // If MongoDB in secure mode, authentication is required.
            // boolean auth = db.authenticate(myUserName, myPassword);
            // System.out.println("Authentication: "+auth);
            //MongoCollection coll = mgdb.getCollection(MONGO_COLLECTION_NAME);
            collection = mgdb.getCollection(MONGO_COLLECTION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return collection;
    }

    /**
     * 插入一条数据
     * @param jsonObject
     */
    public static void insert(JSONObject jsonObject,String host){
        if(jsonObject == null){
            return;
        }
        Document document = new Document(jsonObject);
        MongoCollection<Document> collection = getCollection(host);
        try {
            if(collection != null){
                collection.insertOne(document);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入多条数据
     * @param jsonArray
     */
    public static void insert(JSONArray jsonArray,String host){
        if(jsonArray == null){
            return;
        }
        List<Document> documents = new ArrayList<Document>();
        for (int i = 0; i < jsonArray.size(); i++) {
            Document document = new Document((JSONObject)jsonArray.get(i));
            documents.add(document);
        }

        MongoCollection<Document> collection = getCollection(host);
        try {
            if(collection != null){
                collection.insertMany(documents);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新
     * @param jsonObject
     */
    public static void update(JSONObject jsonObject,String host){
        if(jsonObject == null || StringUtils.isEmpty(jsonObject.getString("_id"))){
            return;
        }
        Document document = new Document(jsonObject);
        MongoCollection<Document> collection = getCollection(host);
        try {
            collection.updateOne(eq("_id", jsonObject.getString("_id")),new Document("$set", document));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除
     * @param id
     */
    public static void delete(String id,String host){
        if(StringUtils.isEmpty(id)){
            return;
        }
        MongoCollection<Document> collection = getCollection(host);
        try {
            collection.deleteOne(eq("_id",id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
