import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class ImmutableQueueTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void enQueue() {
        Queue<Integer> a = new ImmutableQueue<>();
        Queue<Integer> b = a.enQueue(1);
        assertThat(b.head(), is(1));
    }

    @Test
    public void deQueue() {
        Queue<Integer> a = new ImmutableQueue<>();
        Queue<Integer> b = a.enQueue(1);
        Queue<Integer> c = b.enQueue(2);
        assertThat(c.deQueue().head(), is(2));
    }

    @Test
    public void head() {
        Queue<Integer> a = new ImmutableQueue<>();
        Queue<Integer> b = a.enQueue(1);
        Queue<Integer> c = b.enQueue(2);
        assertThat(b.head(), is(1));
        assertThat(c.head(), is(1));
    }

    @Test
    public void isEmpty() {
        Queue<Integer> a = new ImmutableQueue<>();
        assertThat(a.isEmpty(), is(true));
    }

    @Test
    public void immutability() {
        Queue<Integer> a = new ImmutableQueue<>();
        Queue<Integer> b = a.enQueue(1);
        b.enQueue(123);
        assertThat(b.head(), is(1));
    }

    @Test
    public void enqueue_then_dequeue() {
        Queue<Integer> a = new ImmutableQueue<>();
        assertThat(a.head(), is(nullValue()));
        Queue<Integer> b = a.enQueue(1);
        Queue<Integer> c = b.enQueue(2);
        assertThat(b.head(), is(1));
        Queue<Integer> d = c.deQueue();
        assertThat(d.head(), is(2));
    }
}