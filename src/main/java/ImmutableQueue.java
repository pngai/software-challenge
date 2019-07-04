public class ImmutableQueue<T> implements Queue<T> {

    private ImmutableStack<T> forwards;  //ready for dequeue
    private ImmutableStack<T> backwards; //ready for enqueue

    public ImmutableQueue() {
        forwards = new ImmutableStack<>();
        backwards = new ImmutableStack<>();
    }

    private ImmutableQueue(ImmutableStack<T> forwards,
                           ImmutableStack<T> backwards) {
        this.forwards = forwards;
        this.backwards = backwards;
    }

    @Override
    public Queue<T> enQueue(T t) {
        //to make T head() O(1), need to ensure forward is never empty whenever possible
        if(forwards.isEmpty()) {
            return new ImmutableQueue<>(backwards.reverse().push(t), new ImmutableStack<>());
        } else {
            return new ImmutableQueue<>(forwards, backwards.push(t));
        }
    }

    @Override
    public Queue<T> deQueue() {
        if (!forwards.isEmpty()) {
            ImmutableStack<T> newForwards = forwards.pop();
            //to make T head() O(1), need to ensure forward is never empty whenever possible
            return newForwards.isEmpty() ?
                    new ImmutableQueue<>(backwards.reverse(), new ImmutableStack<>()) :
                    new ImmutableQueue<>(newForwards, backwards);
        }
        return new ImmutableQueue<>(backwards.reverse().pop(), new ImmutableStack<>());
    }

    @Override
    public T head() {
        return forwards.peek();
    }

    @Override
    public boolean isEmpty() {
        return forwards.isEmpty() && backwards.isEmpty();
    }
}
