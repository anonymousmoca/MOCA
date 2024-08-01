package IoTSystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.ArrayList;
import java.util.List;

public class MessageQueue {
    private static final Logger LOGGER = LogManager.getLogger();
    private BlockingQueue<Message> queue;
    private List<String> history;

    public MessageQueue() {
        this.queue = new LinkedBlockingQueue<>();
        this.history = new ArrayList<>();
    }

    // Add a message to the queue
    public void addMessage(Message message) {
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Get a message from the queue
    public Message getMessage() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    // Add a record to the history
    public void addHistory(String record) {
        history.add(record);
    }

    // Get the history records
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    // Main function to test the basic functionalities
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue();

        // Test adding and getting messages
        Message message1 = new Message("CoffeeMachine", "turnOn", new String[]{});
        Message message2 = new Message("CoffeeMachine", "setTemperature", new String[]{"75"});

        messageQueue.addMessage(message1);
        messageQueue.addMessage(message2);

        Message retrievedMessage1 = messageQueue.getMessage();
        LOGGER.info("Retrieved message: " + retrievedMessage1.toString());
        Message retrievedMessage2 = messageQueue.getMessage();
        LOGGER.info("Retrieved message: " + retrievedMessage2.toString());

        // Verify the messages were added and retrieved correctly
        assert retrievedMessage1.getDeviceType().equals("CoffeeMachine");
        assert retrievedMessage1.getDeviceAPI().equals("turnOn");
        assert retrievedMessage2.getDeviceType().equals("CoffeeMachine");
        assert retrievedMessage2.getDeviceAPI().equals("setTemperature");

        // Test adding and getting history records
        messageQueue.addHistory("Processed: CoffeeMachine turnOn");
        messageQueue.addHistory("Processed: CoffeeMachine setTemperature");

        List<String> history = messageQueue.getHistory();
        // Verify the history records were added correctly
        assert history.size() == 2;
        assert history.get(0).equals("Processed: CoffeeMachine turnOn");
        assert history.get(1).equals("Processed: CoffeeMachine setTemperature");

        LOGGER.info("All tests passed!");
    }
}
