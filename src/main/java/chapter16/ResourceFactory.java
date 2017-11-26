package chapter16;

import net.jcip.annotations.ThreadSafe;

/**
 * Listing 16.7 combine lazy initialization and static getter
 */
@ThreadSafe
public class ResourceFactory {

    private static class Resource { }

    private static class ResourceHolder {
        public static Resource resource = new Resource();
    }

    public static Resource getResource() { return ResourceHolder.resource; }

}
