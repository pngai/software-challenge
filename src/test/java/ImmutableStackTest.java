import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ImmutableStackTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void push() {
        ImmutableStack<Integer> a = new ImmutableStack<>();
        ImmutableStack<Integer> b = a.push(1);
        assertThat(b.peek(), is(1));
    }

    @Test
    public void pop() {
        ImmutableStack<Integer> a = new ImmutableStack<>();
        ImmutableStack<Integer> b = a.push(1);
        ImmutableStack<Integer> c = b.push(2);
        ImmutableStack<Integer> d = c.push(3);

        assertThat(d.pop().peek(), is(2));
    }

    @Test
    public void isEmpty() {
        ImmutableStack<Integer> a = new ImmutableStack<>();
        assertThat(a.isEmpty(), is(true));
    }

    @Test
    public void peek() {
        ImmutableStack<Integer> a = new ImmutableStack<>();
        ImmutableStack<Integer> b = a.push(11);
        assertThat(b.peek(), is(11));
    }

    @Test
    public void add_then_pop() {
        ImmutableStack<Integer> a = new ImmutableStack<>();
        ImmutableStack<Integer> b = a.push(1);
        ImmutableStack<Integer> c = b.push(2);
        assertThat(b.peek(), is(1));
        assertThat(c.peek(), is(2));
        ImmutableStack<Integer> d = c.pop();
        assertThat(d.peek(), is(1));
    }
}