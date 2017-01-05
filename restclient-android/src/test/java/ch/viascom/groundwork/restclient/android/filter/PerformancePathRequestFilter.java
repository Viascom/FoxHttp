package ch.viascom.groundwork.restclient.android.filter;

import ch.viascom.groundwork.restclient.android.request.Request;
import ch.viascom.groundwork.restclient.android.request.filter.request.RequestFilter;

/**
 * @author patrick.boesch@viascom.ch
 */
public class PerformancePathRequestFilter implements RequestFilter {
    @Override
    public void filter(Request request) throws Exception {
        request.setPath("/links/" + request.getPath());
    }
}