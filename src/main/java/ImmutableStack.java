public class ImmutableStack<T> {
    private T head;
    private ImmutableStack<T> tail;
    public ImmutableStack() {
        this.head = null;
        this.tail = null;
    }
    private ImmutableStack(T head, ImmutableStack<T> tail) {
        this.head = head;
        this.tail = tail;
    }
    public ImmutableStack<T> push(T obj) {
        return new ImmutableStack<>(obj, this);
    }
    public ImmutableStack<T> pop() {
        return tail;
    }
    public ImmutableStack<T> reverse() {
        ImmutableStack<T> ret = new ImmutableStack<>();
        ImmutableStack<T> tmp = this;
//        for(ImmutableStack<T> item = this ; !item.isEmpty(); item = item.pop()) {
        while(!tmp.isEmpty()) {
            ret = ret.push(this.peek());
            tmp = tmp.pop();
        }
        return ret;
    }
    public boolean isEmpty() {
        return head == null && tail == null;
    }
    public T peek() {
        return head;
    }

}