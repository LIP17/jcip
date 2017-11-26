package chapter16;

/**
 * Listing 16.4
 */
public class SafeLazyInitialization {
    private static class Resource {
        // a resource very expensive to init.
    }

    private static Resource resource;

    // use synchronized keyword to guarantee a happens-before
    // relationship
    private synchronized static Resource getInstance() {
        if(resource == null)
            resource = new Resource();
        return resource;
    }

}
