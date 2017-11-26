package chapter16;

/**
 * Listing 16.5
 */
public class EagerInitialization {
    private static class Resource { }

    // static keyword guarantee resource is initialized when
    // class load. This is implemented by a lock acquired by JVM. and
    // promised to happens-before class load finished.
    private static Resource resource = new Resource();

    public static Resource getResource() { return resource; }
}
