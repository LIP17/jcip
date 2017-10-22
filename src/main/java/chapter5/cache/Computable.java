package chapter5.cache;

/**
 */
public interface Computable <A, V>{
    public V compute(A arg) throws InterruptedException;
}


