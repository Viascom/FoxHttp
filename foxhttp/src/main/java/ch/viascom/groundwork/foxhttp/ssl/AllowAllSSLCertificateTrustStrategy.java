package ch.viascom.groundwork.foxhttp.ssl;

import ch.viascom.groundwork.foxhttp.exception.FoxHttpSSLTrustStrategyException;
import ch.viascom.groundwork.foxhttp.log.FoxHttpLogger;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * @author patrick.boesch@viascom.ch
 */
public class AllowAllSSLCertificateTrustStrategy implements FoxHttpSSLTrustStrategy {

    private FoxHttpLogger logger;

    TrustManager[] trustAllCertificates = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null; // Not relevant.
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    // Do nothing. Just allow them all.
                    logger.log("checkClientTrusted( !! ALLOW_ALL !! )");
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    // Do nothing. Just allow them all.
                    logger.log("checkServerTrusted( !! ALLOW_ALL !! )");
                }
            }
    };

    @Override
    public SSLSocketFactory getSSLSocketFactory(HttpsURLConnection httpsURLConnection, FoxHttpLogger logger) throws FoxHttpSSLTrustStrategyException {
        this.logger = logger;
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCertificates, new SecureRandom());
        } catch (Exception e) {
            throw new FoxHttpSSLTrustStrategyException(e);
        }

        return sc.getSocketFactory();
    }
}
