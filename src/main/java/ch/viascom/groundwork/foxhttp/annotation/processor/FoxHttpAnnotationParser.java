package ch.viascom.groundwork.foxhttp.annotation.processor;

import ch.viascom.groundwork.foxhttp.FoxHttpClient;
import ch.viascom.groundwork.foxhttp.builder.CachableRequestBuilder;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpRequestException;
import ch.viascom.groundwork.foxhttp.response.FoxHttpResponseParser;
import ch.viascom.groundwork.foxhttp.response.serviceresult.DefaultServiceResultHasher;
import ch.viascom.groundwork.foxhttp.response.serviceresult.FoxHttpServiceResultResponse;
import ch.viascom.groundwork.foxhttp.response.serviceresult.ServiceResult;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import lombok.Getter;

/**
 * @author patrick.boesch@viascom.ch
 */
public class FoxHttpAnnotationParser {

    private HashMap<Method, CachableRequestBuilder> requestCache = new HashMap<>();
    @Getter
    private HashMap<Class<? extends Annotation>, FoxHttpResponseParser> responseParsers = new HashMap<>();

    public FoxHttpAnnotationParser() {
        //Register @ServiceResult
        addResponseParser(ServiceResult.class, new FoxHttpServiceResultResponse(new DefaultServiceResultHasher()));
    }

    /**
     * Add a new response parser to the annotation parser
     *
     * @param annotation a annotation
     * @param responseParser a response parser
     */
    public void addResponseParser(Class<? extends Annotation> annotation, FoxHttpResponseParser responseParser) {
        getResponseParsers().put(annotation, responseParser);
    }

    /**
     * Parse the given interface for the use of FoxHttp
     *
     * @param serviceInterface interface to parse
     * @param foxHttpClient FoxHttpClient to use
     * @param <T> interface class to parse
     * @return Proxy of the interface
     */
    @SuppressWarnings("unchecked")
    public <T> T parseInterface(final Class<T> serviceInterface, FoxHttpClient foxHttpClient) throws FoxHttpException {

        try {
            Method[] methods = serviceInterface.getDeclaredMethods();

            for (Method method : methods) {
                FoxHttpMethodParser foxHttpMethodParser = new FoxHttpMethodParser();
                foxHttpMethodParser.parseMethod(method, foxHttpClient);

                CachableRequestBuilder cachableRequestBuilder = new CachableRequestBuilder(foxHttpMethodParser.getUrl(), foxHttpMethodParser.getHeaderFields(),
                                                                                           foxHttpMethodParser.getRequestType(), foxHttpMethodParser.isSkipResponseBody(),
                                                                                           foxHttpMethodParser.isFollowRedirect(), foxHttpClient);

                requestCache.put(method, cachableRequestBuilder);
            }

            return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class[]{serviceInterface},
                                              new FoxHttpAnnotationInvocationHandler(requestCache, responseParsers));
        } catch (FoxHttpException e) {
            throw e;
        } catch (Exception e) {
            throw new FoxHttpRequestException(e);
        }
    }

}
