
import com.example.demo.service.CounterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Counter service test.
 */
public class CounterServiceTest {

    private CounterService counterService;

    /**
     * Sets up.
     */
@BeforeEach
    public void setUp() {
        counterService = CounterService.getInstance();
    }

    /**
     * Test increment request count.
     */
@Test
    public void testIncrementRequestCount() {
        int initialCount = counterService.getRequestCount();
        counterService.incrementRequestCount();
        int countAfterIncrement = counterService.getRequestCount();
        assertEquals(initialCount + 1, countAfterIncrement);
    }

  /** Test get request count. */
  @Test
  public void testGetRequestCount() {
        int count = counterService.getRequestCount();
        assertEquals(count, counterService.getRequestCount());
    }
}
