package IoTSystem;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedMessage implements Delayed {
    private final Message message;
    private final long startTime;

    public DelayedMessage(Message message, long delay) {
        this.message = message;
        this.startTime = System.currentTimeMillis() + delay;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long delay = startTime - System.currentTimeMillis();
        return unit.convert(delay, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.startTime, ((DelayedMessage) o).startTime);
    }
}
