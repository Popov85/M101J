package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * HW 2.3
 */
public class App {

        public static void main( String[] args ) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("school");
        MongoCollection<Document> collection = database.getCollection("students");
                int counter = 0;
                //List<Document> sample = collection.find().into(new ArrayList<Document>());
                /*Bson filter = eq("type", "homework");
                Bson sort = ascending("student_id", "score");*/
                //List<Document> sample = collection.find(filter).sort(sort).into(new ArrayList<Document>());
                //Map<Integer, Double> map = new HashMap<>();
                List<Document> sample = collection.find().into(new ArrayList<Document>());
                Double students[] = new Double[200];
                Double arr[] = new Double[2];
                for (Document cur : sample) {
                        counter++;
                        Integer id = cur.getInteger("_id");
                        List<Document> courses = (List<Document>) cur.get("scores");
                        int arrCounter = 0;
                        double sum = 0;
                        for (Document cour : courses) {
                                String type = cour.getString("type");
                                if (type.equals("homework")) {
                                        arr[arrCounter] = cour.getDouble("score");
                                        arrCounter++;
                                } else {
                                        System.out.println("type: "+cour.getString("type")+" score"+cour.getDouble("score"));
                                        sum+=cour.getDouble("score");
                                        System.out.println("sum= "+sum);
                                }
                        }
                        if (arr[0]>arr[1]) {
                                sum+=arr[0];
                                System.out.println("Max="+arr[0]);
                        } else {
                                sum+=arr[1];
                                System.out.println("Min="+arr[1]);
                        }
                        System.out.println("sum= "+sum);
                        System.out.println("id = "+id+" avg = "+sum/3);
                        students[id]=sum/3;
                        System.out.println("-----next student-----");
                }
                // Calculate here the max
                Double max = students[0];
                int stId = 0;
                for (int i = 1; i < students.length; i++) {
                        if (students[i]>max) {
                                max = students[i];
                                stId = i;
                        }
                }
                System.out.println("Max= "+max+" id = "+stId);
        }

        public static void printJson(Document document) {
                JsonWriter jsonWriter = new JsonWriter(new StringWriter(),
                        new JsonWriterSettings(JsonMode.SHELL, true));
                new DocumentCodec().encode(jsonWriter, document,
                        EncoderContext.builder()
                                .isEncodingCollectibleDocument(true)
                                .build());
                System.out.println(jsonWriter.getWriter());
                System.out.flush();
        }
}
