package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.conversions.Bson;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;

/**
 * HW 2.3
 */
public class App {

        public static void main( String[] args ) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("students");
        MongoCollection<Document> collection = database.getCollection("grades");
                int counter = 0;
                //List<Document> sample = collection.find().into(new ArrayList<Document>());
                Bson filter = eq("type", "homework");
                Bson sort = ascending("student_id", "score");
                List<Document> sample = collection.find(filter).sort(sort).into(new ArrayList<Document>());
                for (Document cur : sample) {
                        counter++;
                        if ( (counter & 1) != 0 ) {
                                //System.out.println("To be deleted");
                                //printJson(cur);
                                collection.deleteOne(cur);
                                //odd 1-3
                        } else {
                                printJson(cur);
                                //even чётный 2-4
                        }
                }
                System.out.println("Total of homework= "+counter);
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
