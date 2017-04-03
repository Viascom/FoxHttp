package ch.viascom.groundwork.foxhttp.authorization;

import ch.viascom.groundwork.foxhttp.FoxHttpClient;
import ch.viascom.groundwork.foxhttp.placeholder.FoxHttpPlaceholderStrategy;

import java.net.URLConnection;
import java.util.List;

/**
 * FoxHttpAuthorizationStrategy interface
 *
 * @author patrick.boesch@viascom.ch
 */
public interface FoxHttpAuthorizationStrategy {
    List<FoxHttpAuthorization> getAuthorization(URLConnection connection, FoxHttpAuthorizationScope searchScope, FoxHttpClient foxHttpClient, FoxHttpPlaceholderStrategy foxHttpPlaceholderStrategy);

    void addAuthorization(FoxHttpAuthorizationScope foxHttpAuthorizationScope, FoxHttpAuthorization foxHttpAuthorization);

    void addAuthorization(List<FoxHttpAuthorizationScope> foxHttpAuthorizationScopes, FoxHttpAuthorization foxHttpAuthorization);

    void removeAuthorization(FoxHttpAuthorizationScope foxHttpAuthorizationScope, FoxHttpAuthorization foxHttpAuthorization);
}
