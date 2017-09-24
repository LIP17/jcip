package chapter3;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import javax.servlet.*;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Listing 3.12, 3.13
 *
 * Thread safe way to update value without lock. With the help of immutable
 * variable and `volaltile`. Because the cache object is immutable, so if one thread
 * update the cache, other thread hold the old reference will also hold the original object.
 *
 * And the volatile keywork guarantee if thread 1 replace it, the other thread will also see
 * the side effect.
 * */

@ThreadSafe
public class VolatileCachedFactorizer implements Servlet {
    private volatile OneValueCache cache = new OneValueCache(null,null);

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromReq(req);
        BigInteger[] factors = cache.getFactors(i);

        if(factors == null) {
            factors = doFactor(i);
            cache = new OneValueCache(i, factors);
        }

        encodeResponse(resp, factors);
    }

    public String getServletInfo() {
        return null;
    }

    private BigInteger[] doFactor(BigInteger i) {
        return null;
    }

    private BigInteger extractFromReq(ServletRequest req) {
        return null;
    }

    private void encodeResponse(ServletResponse resp, BigInteger[] factors) {
    }

    public void init(ServletConfig servletConfig) throws ServletException {

    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void destroy() {

    }

}

@Immutable
class OneValueCache {
    private final BigInteger number;
    private final BigInteger[] factors;

    public OneValueCache(BigInteger i, BigInteger[] factors) {
        this.number = i;
        this.factors = Arrays.copyOf(factors, factors.length);
    }

    public BigInteger[] getFactors(BigInteger i) {
        if (number == i) {
            return null;
        } else {
            return Arrays.copyOf(factors, factors.length);
        }
    }
}