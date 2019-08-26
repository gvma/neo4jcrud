package bd2.neo4jcrud;

import java.io.File;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.neo4j.driver.internal.spi.Connection;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.QueryExecutionException;
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
	
	public static Result deleteNode(Map<String, Object> properties) {
		String finalQuery = "MATCH (n {";
		int count = 0;
		for (String entry:properties.keySet()) {
			count++;
			if (count == properties.size()) {
				finalQuery += entry+": '"+properties.get(entry)+"'})";
			} else {
				finalQuery += entry+": '"+properties.get(entry)+"', ";
			}
		}
		finalQuery += " DELETE n";
		System.out.println(finalQuery);
		Result result = null;
		try (Transaction tx = graphDb.beginTx()){
			result = graphDb.execute(finalQuery);
			System.out.println("Node deleted with success with result:");
			System.out.println(result.resultAsString().toString());
			tx.success();
			return result;
		} catch (QueryExecutionException exception) {
			System.out.println("Error while trying to find the node from the database!");
			return null;
		}
	}
	
	public static Result findNode(Map<String, Object> properties) {
		String finalQuery = "MATCH (n {";
		int count = 0;
		for (String entry:properties.keySet()) {
			count++;
			if (count == properties.size()) {
				finalQuery += entry+": '"+properties.get(entry)+"'})";
			} else {
				finalQuery += entry+": '"+properties.get(entry)+"', ";
			}
		}
		finalQuery += " RETURN DISTINCT n";
		Result result = null;
		try (Transaction tx = graphDb.beginTx()){
			result = graphDb.execute(finalQuery);
			System.out.println("Node found with success with result:");
			System.out.println(result.resultAsString().toString());
			tx.success();
			return result;
		} catch (QueryExecutionException exception) {
			System.out.println("Error while trying to find the node from the database!");
			return null;
		}
	}
		
	public static void createNode(Map<String, Object> properties, Label... labels) {
		String query = "CREATE (var";
		for (int i = 0; i < labels.length; i++) {
			System.out.println(labels[i].toString());
			query += ":"+labels[i].toString();
		}
		query += "{";
		String finalQuery = query;
		int count = 0;
		for (String entry:properties.keySet()) {
			count++;
			if (count == properties.size()) {
				finalQuery += entry+": '"+properties.get(entry)+"'})";
			} else {
				finalQuery += entry+": '"+properties.get(entry)+"', ";
			}
			System.out.println(finalQuery);
		}
		try (Transaction tx = graphDb.beginTx()){
			Result result = graphDb.execute(finalQuery);
			System.out.println(result.resultAsString());
			tx.success();
		} catch (QueryExecutionException exception) {
			System.out.println("Error while trying to create the node from the database");
			return;
		}
		System.out.println("Node created with success!!");
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Bem-vindo ao CRUD do Neo4J!");
			System.out.println("-------------------");
			System.out.println("| (1) Create Node |");
			System.out.println("| (2) Delete Node |");
			System.out.println("| (3) Find Node   |");
			System.out.println("| (4) Update Node |");
			System.out.println("| (5) Exit CRUD   |");
			System.out.println("-------------------");
			String choice = scanner.nextLine();
			try (Transaction tx = graphDb.beginTx()) {				
				if (choice.equals("1")) {
					System.out.println("------> Digite a quantidade de labels para seu novo no:");
					int quantityOfLabels = scanner.nextInt();
					scanner.nextLine();
					Label[] labels = new Label[quantityOfLabels];
					for (int i = 0; i < quantityOfLabels; i++) {
						System.out.println("------> Digite a sua label: ");
						String labelString = scanner.nextLine();
						labels[i] = Label.label(labelString);
					}
					System.out.println("------> Digite as propriedades (chave, valor) para seu novo no: ");
					int quantityOfEntries = scanner.nextInt();
					scanner.nextLine();
					Map<String, Object> properties = new HashMap<String, Object>();
					for (int i = 0; i < quantityOfEntries; i++) {
						System.out.println("------> Digite a (chave, valor):");
						String key = scanner.nextLine(), value = scanner.nextLine();
						properties.put(key, value);
					}
					createNode(properties, labels);
				} else if (choice.equals("2")) {
					
				} else if (choice.equals("3")) {
					
				} else if (choice.equals("4")) {
					
				} else if (choice.equals("5")) {
					return;
				}
				tx.success();
			} catch (QueryExecutionException exception) {
				System.out.println("An error has occurred, please, try again!!");
				continue;
			}
		}
//		System.out.println(result.resultAsString().toString());
	}
}