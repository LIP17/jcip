package chapter16;

import net.jcip.annotations.NotThreadSafe;


/**
 * This looks correct when the resource is null, but not
 * guarantee the resource is initialized when published!
 */
@NotThreadSafe
public class UglyDoubleCheck {

    private static class Resource { }

    private static Resource resource;

    public static Resource getInstance() {
        if(resource == null) {
            synchronized (UglyDoubleCheck.class) {
                if(resource == null) {
                    resource = new Resource();
                }
            }
        }
        return resource;
    }
}
