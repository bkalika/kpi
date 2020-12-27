package mongodbtest;

import com.mongodb.client.*;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shop {

    private static void printCollection(String name, MongoDatabase database){
        MongoCollection<Document> coll = database.getCollection(name);
        System.out.println("Collection ["+name+ "] has" +coll.countDocuments()+" elements:");

        FindIterable<Document> cursor = coll.find();

        for(Document s: cursor) {
            System.out.println(s);
        }
        System.out.println();
    }

    private static void keyValueQuery(MongoDatabase database){
        System.out.println("All smartphones:");
        MongoCollection<Document> coll = database.getCollection("products");

        Document query = new Document("type", "Smartphone");
        Document options = new Document("_id", 0).append("name", 1).append("price", 1);
        Document sort = new Document("price", 1);

        FindIterable<Document> cursor = coll.find(query).projection(options).sort(sort);

        for(Document s: cursor){
            System.out.println(s);
        }
        System.out.println();
    }

    private static void aggregationQuery(MongoDatabase database){
        System.out.println("Get total sum transactions:");
        MongoCollection<Document> coll = database.getCollection("orders");

        Document unwind1 = new Document("$unwind", new Document("path", "$products"));
        Document addFields = new Document("$addFields", new Document("productid", "$products.product").append("productqty", "$products.qty"));
        Document project = new Document("$project", new Document("productid", 1).append("productqty", 1).append("deliveryDate", 1).append("_id", 0));
        Document lookup = new Document("$lookup", new Document("from", "products").append("localField", "productid").append("foreignField", "_id").append("as", "desc"));
        Document unwind2 = new Document("$unwind", new Document("path", "$desc"));
        Document group = new Document("$group", new Document("_id", "$deliveryDate").
                append("sum", new Document("$sum", new Document("$multiply", Arrays.asList("$productqty", "$desc.price")))));
        Document sort = new Document("$sort", new Document("sum", -1));

        AggregateIterable<Document> results = coll.aggregate(Arrays.asList(unwind1, addFields, project, lookup, unwind2, group, sort));

        for(Document s: results){
            System.out.println(s);
        }
    }

    private static void createCollection(MongoDatabase database){
        MongoCollection<Document> coll = database.getCollection("customers");
        ObjectId customer = new ObjectId();
        Document info1 = new Document("_id", customer)
                .append("name", "Ivan")
                .append("surname", "Kozak")
                .append("discounts", Arrays.asList("5%", "7%"))
                .append("interests", new Document("hobby", "Dog")
                                    .append("car", false)
                                    .append("status", "single")
        );

        Document info2 = new Document("_id", new ObjectId())
                .append("name", "Taras")
                .append("suname", "Kitsmey")
                .append("discounts", "10%")
                .append("phone", "0998877441");

        List<Document> information = new ArrayList<Document>();
        information.add(info1);
        information.add(info2);
        coll.insertMany(information);

        coll = database.getCollection("products");
        ObjectId product1 = new ObjectId();
        Document characteristics1 = new Document("_id", product1)
                .append("brand", "Samsung")
                .append("model", "S20")
                .append("type", "Smartphone")
                .append("price", 19999)
                .append("description", "Super smartphone, for business person");
        ObjectId product2 = new ObjectId();
        Document characteristics2 = new Document("_id", product2)
                .append("brand", "iPhone")
                .append("model", "X")
                .append("type", "Smartphone")
                .append("price", 25999)
                .append("description", "Super smartphone, for making a good photo");
        ObjectId product3 = new ObjectId();
        Document characteristics3 = new Document("_id", product3)
                .append("brand", "Xiaomi")
                .append("model", "SmartTV")
                .append("type", "TV")
                .append("price", 5499)
                .append("description", "TV for watching 'Home alone'");
        ObjectId product4 = new ObjectId();
        Document characteristics4 = new Document("_id", product4)
                .append("brand", "HP")
                .append("model", "ProBook 4540s")
                .append("type", "Laptop")
                .append("price", 10499)
                .append("description", "Laptop as faster, as you can imagine");
        List<Document> characteristics = new ArrayList<>();
        characteristics.add(characteristics1);
        characteristics.add(characteristics2);
        characteristics.add(characteristics3);
        characteristics.add(characteristics4);
        coll.insertMany(characteristics);

        coll = database.getCollection("point_of_sales");
        Document point_of_sale1 = new Document("_id", new ObjectId())
                .append("Name", "Rozetka")
                .append("products", Arrays.asList(product1, product2, product3, product4));
        Document point_of_sale2 = new Document("_id", new ObjectId())
                .append("Name", "Comfy")
                .append("products", Arrays.asList(product1, product4))
                .append("cities", Arrays.asList("Kyiv", "Lviv", "Kharkiv"));
        Document point_of_sale3 = new Document("_id", new ObjectId())
                .append("Name", "Citrus")
                .append("products", Arrays.asList(product1, product2))
                .append("cities", Arrays.asList("Kyiv"));
        List<Document> point_of_sales = new ArrayList<>();
        point_of_sales.add(point_of_sale1);
        point_of_sales.add(point_of_sale2);
        point_of_sales.add(point_of_sale3);
        coll.insertMany(point_of_sales);

        coll = database.getCollection("orders");
        Document order1 = new Document("_id", new ObjectId())
                .append("transaction", "#23444")
                .append("deliveryDate", "26.12.2020")
                .append("customer", customer)
                .append("products", Arrays.asList(new Document("product", product1).append("qty", 1),
                                                new Document("product", product4).append("qty", 1)));
        Document order2 = new Document("_id", new ObjectId())
                .append("transaction", "#55322")
                .append("deliveryDate", "30.12.2020")
                .append("customer", customer)
                .append("products", Arrays.asList(new Document("product", product2).append("qty", 2)));
        Document order3 = new Document("_id", new ObjectId())
                .append("transaction", "#55134")
                .append("deliveryDate", "29.12.2020")
                .append("customer", customer)
                .append("products", Arrays.asList(new Document("product", product1).append("qty", 1),
                                                new Document("product", product2).append("qty", 1),
                                                new Document("product", product3).append("qty", 1),
                                                new Document("product", product4).append("qty", 1)));
        List<Document> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        coll.insertMany(orders);
    }

    public static void main(String[] args){
        System.out.println("Shop was opened");

        MongoClient mongoClient = MongoClients.create("mongodb://localhost");
        MongoDatabase database = mongoClient.getDatabase("shop_db");

//        createCollection(database);
        printCollection("customers", database);
        printCollection("products", database);
        printCollection("point_of_sales", database);
        printCollection("orders", database);

        keyValueQuery(database);
        aggregationQuery(database);

        mongoClient.close();
    }
}
