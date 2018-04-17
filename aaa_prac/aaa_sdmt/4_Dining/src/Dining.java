import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dining {
	public static void main(String[] args) {
		Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
		mongoLogger.setLevel(Level.SEVERE);
		Lock forks[] = new ReentrantLock[5];

		try {
			MongoClient mongoClient = new MongoClient("localhost");
			System.out.println("Connection to mongodb successful.");
			MongoDatabase db = mongoClient.getDatabase("dining");
			System.out.println("Database 'dining' created.");
			db.createCollection("mycol");
			System.out.println("Collection 'mycol' created.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < 5; i++) {
			forks[i] = new ReentrantLock();
		}
		Thread p1 = new Thread(new Philosopher(forks[4], forks[0], "first"));
		Thread p2 = new Thread(new Philosopher(forks[0], forks[1], "second"));
		Thread p3 = new Thread(new Philosopher(forks[1], forks[2], "third"));
		Thread p4 = new Thread(new Philosopher(forks[2], forks[3], "fourth"));
		Thread p5 = new Thread(new Philosopher(forks[3], forks[4], "fifth"));
		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();
	}
}

class Philosopher implements Runnable
{
	Lock leftFork = new ReentrantLock();
	Lock rightFork = new ReentrantLock();
	String name;

	public Philosopher(Lock leftFork, Lock rightFork, String name)
	{
		this.leftFork = leftFork;
		this.rightFork = rightFork;
		this.name = name;
	}

	@Override
	
	public void run() 
	{
		try
		{
			think(name);
			eat(leftFork, rightFork, name);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void eat(Lock leftFork, Lock rightFork, String name) throws Exception 
	{
		leftFork.lock();
		rightFork.lock();
		try 
		{
			MongoClient mongoClient = new MongoClient("localhost");
			MongoDatabase db = mongoClient.getDatabase("dining");
			MongoCollection<Document> coll = db.getCollection("mycol");
			System.out.println(name + " eating...");
			Document doc1 = new Document(name, " eating...");
			coll.insertOne(doc1);
			Thread.sleep(1000);
		}
		catch (Exception e) 
		{
			
			e.printStackTrace();
		} 
		finally
		{
			System.out.println(name + " done eating and now thinking...");
			MongoClient mongoClient = new MongoClient("localhost");
			MongoDatabase db = mongoClient.getDatabase("dining");
			MongoCollection<Document> coll = db.getCollection("mycol");
			Document doc2 = new Document(name, " done eating and now thinking...");
			coll.insertOne(doc2);
			leftFork.unlock();
			rightFork.unlock();
			
		}
	}

	public void think(String name) throws Exception 
	{
		try 
		{
			MongoClient mongoClient = new MongoClient("localhost");
			MongoDatabase db = mongoClient.getDatabase("dining");
			MongoCollection<Document> coll = db.getCollection("mycol");
			System.out.println(name + " thinking...");
			Document doc = new Document(name, " thinking...");
			coll.insertOne(doc);
			Thread.sleep(1000);
		} 
		catch (InterruptedException e) 
		{
			
			e.printStackTrace();
		}
	}
}
