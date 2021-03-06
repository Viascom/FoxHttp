package ch.viascom.groundwork.foxhttp.interceptor;

import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author patrick.boesch@viascom.ch
 */
public interface FoxHttpInterceptorStrategy {

    Map<FoxHttpInterceptorType, HashMap<String, FoxHttpInterceptor>> getFoxHttpInterceptors();

    void setFoxHttpInterceptors(Map<FoxHttpInterceptorType, HashMap<String, FoxHttpInterceptor>> interceptors);

    void addInterceptor(FoxHttpInterceptorType type, FoxHttpInterceptor interceptor) throws FoxHttpException;

    void addInterceptor(FoxHttpInterceptorType type, FoxHttpInterceptor interceptor, String key) throws FoxHttpException;

    void removeInterceptorByKey(FoxHttpInterceptorType type, String key);

    void removeInterceptorByClass(FoxHttpInterceptorType type, Class<? extends FoxHttpInterceptor> clazz);

    void replaceInterceptor(FoxHttpInterceptorType type, FoxHttpInterceptor newInterceptor, String key);

    FoxHttpInterceptor getInterceptorByKey(FoxHttpInterceptorType type, String key);

    ArrayList<FoxHttpInterceptor> getInterceptorsByClass(FoxHttpInterceptorType type, Class<? extends FoxHttpInterceptor> clazz);

    HashMap<String, FoxHttpInterceptor> getAllInterceptorsFromType(FoxHttpInterceptorType type);

    ArrayList<FoxHttpInterceptor> getAllInterceptorsFromTypeAsArray(FoxHttpInterceptorType type, boolean sorted);

    boolean doesTypeExist(FoxHttpInterceptorType type);
}
