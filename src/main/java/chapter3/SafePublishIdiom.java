package chapter3;

/**
 * To publish an object safely, both the reference to the obj and the obj's `state` must be
 * visible to other threads at the same time, A properly constructed object can be safely published by:
 *
 * 1. Initializing an obj reference from static initializer.
 * 2. Storing a ref to it into a volatile field or AtomicReference.
 * 3. Storing a ref to it into a final field of a properly constructed obj.
 * 4. Stroring a ref to it into a field that is properly guarded by a lock.
 * */
public class SafePublishIdiom {
    // corresponding to @UnsafePublication, the `static` keyword
    // is the easiest and safest way to publish objects that can be
    // statistically constructed.
    public static Holder holder = new Holder(1);
}
