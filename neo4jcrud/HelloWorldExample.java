package bd2.neo4jcrud;

import java.io.File;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.neo4j.driver.internal.spi.Connection;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.graphdb.index.RelationshipIndex;
import org.neo4j.graphdb.factory.*;


public class HelloWorldExample {
    
	final private static File f = new File(System.getProperty("user.dir"));
	final private static GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(f);
	
	public static void createNode(Label label, String property) {
		String query = "CREATE(p:"+label.toString()+")";
		graphDb.execute(query);
	}
	
	public static void createNode(Label label, Map<String, Object> properties) {
		String query = "CREATE (var:"+label.toString()+"{";
		for (String entry:properties.keySet()) {
			String finalQuery = query;
			String value = "";
			finalQuery = query+entry+": '"+properties.get(entry)+"'})";
			System.out.println(finalQuery);
			graphDb.execute(finalQuery);
		}
	}
	
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<>();
		map.put("Nome", "HEY");
		map.put("Sobrenome", "JOOOOOOOOOOOOOOHN");
//		createNode(Label.label("Pessoa"), map);
//		Result result = graphDb.execute("MATCH (p:Pessoa) RETURN p");
//		System.out.println(result.resultAsString().toString());
//		createNode(Label.label("Pessoa"), "Mineirin",);
	}
}