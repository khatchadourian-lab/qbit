package io.advantageous.qbit;

import io.advantageous.qbit.client.Client;
import io.advantageous.qbit.events.EventManager;
import io.advantageous.qbit.http.HttpClient;
import io.advantageous.qbit.http.HttpServer;

import io.advantageous.qbit.json.JsonMapper;
import io.advantageous.qbit.message.MethodCall;
import io.advantageous.qbit.message.Request;
import io.advantageous.qbit.message.Response;
import io.advantageous.qbit.queue.Queue;
import io.advantageous.qbit.queue.QueueBuilder;
import io.advantageous.qbit.sender.Sender;
import io.advantageous.qbit.server.ServiceServer;
import io.advantageous.qbit.service.BeforeMethodCall;
import io.advantageous.qbit.service.Service;
import io.advantageous.qbit.service.ServiceBundle;
import io.advantageous.qbit.service.ServiceMethodHandler;
import io.advantageous.qbit.spi.FactorySPI;
import io.advantageous.qbit.spi.ProtocolEncoder;
import io.advantageous.qbit.spi.ProtocolParser;
import io.advantageous.qbit.transforms.Transformer;
import io.advantageous.qbit.util.MultiMap;

import java.util.List;

/**
 * Main factory for QBit. This gets used internally to create / parse methods.
 * @author rhightower
 */
public interface Factory {

    /**
     * Create a method call based on a body that we are parsing from  a POST body or WebSocket message for example.
     * @param address address of method (this can override what is in the body)
     * @param returnAddress return address, which is a moniker for where we want to return the results
     * @param objectName name of the object (optional)
     * @param methodName name of the method (optional)
     * @param args arguments and possibly more (could be whole message encoded)
     * @param params params, usually request parameters
     * @return new method call object returned.
     */
    default MethodCall<Object> createMethodCallToBeParsedFromBody(String address,
                                                          String returnAddress,
                                                          String objectName,
                                                          String methodName,
                                                          Object args,
                                                          MultiMap<String, String> params) {
       throw new UnsupportedOperationException();
    }

    /**
     * Create a method call based on a body that we are parsing from  a POST body or WebSocket message for example.
     * @param address address of method (this can override what is in the body)
     * @param returnAddress return address, which is a moniker for where we want to return the results
     * @param args arguments and possibly more (could be whole message encoded)
     * @param params params, usually request parameters
     * @return new method call object returned.
     */
    default MethodCall<Object> createMethodCallByAddress(String address,
                                                 String returnAddress,
                                                 Object args,
                                                 MultiMap<String, String> params) {
        throw new UnsupportedOperationException();
    }

    /**
     * Create a method call based on a body that we are parsing from  a POST body or WebSocket message for example.
     * @param objectName name of the object (optional)
     * @param methodName name of the method (optional)
     * @param returnAddress return address, which is a moniker for where we want to return the results
     * @param args arguments and possibly more (could be whole message encoded)
     * @param params params, usually request parameters
     * @return new method call object returned.
     */
    default MethodCall<Object> createMethodCallByNames(
            String methodName, String objectName, String returnAddress, Object args,
            MultiMap<String, String> params) {
        throw new UnsupportedOperationException();
    }


    /**
     * String address, final int batchSize, final int pollRate,
     final Factory factory, final boolean asyncCalls,
     final BeforeMethodCall beforeMethodCall,
     final BeforeMethodCall beforeMethodCallAfterTransform,
     final Transformer<Request, Object> argTransformer
     */

    /**
     * Create a service bundle.
     * @param address service path to bundle (base URI really)
     *
     * @param asyncCalls service calls
     * @return new client bundle
     */
    default ServiceBundle createServiceBundle(String address, final QueueBuilder queueBuilder,
                                              final Factory factory, final boolean asyncCalls,
                                              final BeforeMethodCall beforeMethodCall,
                                              final BeforeMethodCall beforeMethodCallAfterTransform,
                                              final Transformer<Request, Object> argTransformer, boolean invokeDynamic){
        throw new UnsupportedOperationException();
    }


    default ServiceMethodHandler createServiceMethodHandler(boolean invokeDynamic) {
        throw new UnsupportedOperationException();
    }

    /**
     * Create a client
     * @param rootAddress base URI
     * @param serviceAddress client address URI
     * @param object object that implements the client
     * @param responseQueue the response queue.
     * @return new Service that was created
     *
     *
     *
     */
    default Service createService(String rootAddress, String serviceAddress,
                                  Object object,
                                  Queue<Response<Object>> responseQueue,
                                  final  QueueBuilder queueBuilder,
                                  boolean asyncCalls, boolean invokeDynamic, boolean handleCallbacks){
        throw new UnsupportedOperationException();
    }


    /**
     * Create a client
     * @param rootAddress base URI
     * @param serviceAddress client address URI
     * @param object object that implements the client
     * @param responseQueue the response queue.
     * @return new Service that was created
     *
     *
     *
     */
    default Service createService(String rootAddress, String serviceAddress,
                                  Object object,
                                  Queue<Response<Object>> responseQueue){
        throw new UnsupportedOperationException();
    }

    /**
     * Create an encoder.
     * @return encoder.
     */
    default ProtocolEncoder createEncoder(){
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a method call to be encoded and sent. This is usually called by a client (local or remote proxy).
     * @param id id of method call
     * @param address address of method
     * @param returnAddress return address, which is a moniker for where we want to return the results
     * @param objectName name of the object (optional)
     * @param methodName name of the method (optional)
     * @param timestamp when we sent this message
     * @param body arguments (could be a list or an array)
     * @param params additional parameters associated with this method call.
     * @return method call that we are sending
     */
    default MethodCall<Object> createMethodCallToBeEncodedAndSent(long id, String address,
                                                          String returnAddress,
                                                          String objectName,
                                                          String methodName,
                                                          long timestamp,
                                                          Object body,
                                                          MultiMap<String, String> params){
        throw new UnsupportedOperationException();
    }


    /**
     * Create a local client proxy
     * @param serviceInterface client interface to client
     * @param serviceName name of the client that we are proxying method calls to.
     * @param serviceBundle name of client bundle
     * @param <T> type of proxy
     * @return new proxy object
     */
    default <T> T createLocalProxy(Class<T> serviceInterface, String serviceName, ServiceBundle serviceBundle){
        throw new UnsupportedOperationException();
    }

    /**
     * Create a response object from a string (HTTP response, Websocket body)
     * @param text of response message
     * @return response object
     */
    default Response<Object> createResponse(String text){
        throw new UnsupportedOperationException();
    }

    /**
     * Create a remote proxy using a sender that knows how to send method body over wire
     * @param serviceInterface client view of client
     * @param uri uri of client
     * @param serviceName name of the client that we are proxying method calls to.
     * @param returnAddressArg return address
     * @param sender how we are sending the message over the wire
     * @param beforeMethodCall before method call
     * @param <T> type of client
     * @return remote proxy
     */
    default <T> T createRemoteProxyWithReturnAddress(Class<T> serviceInterface, String uri, String serviceName, String returnAddressArg,
                                             Sender<String> sender,
                                             BeforeMethodCall beforeMethodCall,
                                             int requestBatchSize){
        throw new UnsupportedOperationException();
    }

    /**
     * Parses a method call using an address prefix and a body.
     * Useful for Websocket calls and POST calls (if you don't care about request params).
     * @param addressPrefix prefix of the address
     * @param message message that we are sending
     * @param originatingRequest the request that caused this method to be created
     * @return method call that we just created
     */
    default MethodCall<Object> createMethodCallToBeParsedFromBody(String addressPrefix,
                                                                  Object message,
                                                                  Request<Object> originatingRequest){
        throw new UnsupportedOperationException();
    }


    default List<MethodCall<Object>> createMethodCallListToBeParsedFromBody(
            String addressPrefix,
            Object body,
            Request<Object> originatingRequest){
        throw new UnsupportedOperationException();
    }

    /**
     * Request request
     * @param request incoming request that we want to create a MethodCall from.
     * @param args args
     * @return request
     */
    default MethodCall<Object> createMethodCallFromHttpRequest(
            Request<Object> request, Object args){
        throw new UnsupportedOperationException();
    }


    /**
     * Creates a JSON Mapper.
     * @return json mapper
     */
    default JsonMapper createJsonMapper(){
        throw new UnsupportedOperationException();
    }



    default HttpServer createHttpServer(String host, int port, boolean manageQueues,
                      int pollTime,
                      int requestBatchSize,
                      int flushInterval, int maxRequests
                      ){
        throw new UnsupportedOperationException();
    }


    default HttpServer createHttpServer(String host, int port, boolean manageQueues,
                                        int pollTime,
                                        int requestBatchSize,
                                        int flushInterval, int maxRequests, int httpWorkers, Class handlerClass
    ){
        throw new UnsupportedOperationException();
    }

    default HttpClient createHttpClient(
                String host,
                int port,
                int pollTime,
                int requestBatchSize,
                int timeOutInMilliseconds,
                int poolSize,
                boolean autoFlush,
                boolean keepAlive,
                boolean pipeline){
        throw new UnsupportedOperationException();
    }



    default EventManager systemEventManager() {
        throw new IllegalStateException("Not implemented");
    }


    default EventManager createEventManager() {
        return FactorySPI.getEventManagerFactory().createEventManager();
    }

    default ServiceServer createServiceServer(final HttpServer httpServer,
                                      final ProtocolEncoder encoder,
                                      final ProtocolParser protocolParser,
                                      final ServiceBundle serviceBundle,
                                      final JsonMapper jsonMapper,
                                      final int timeOutInSeconds,
                                      final int numberOfOutstandingRequests,
                                      final int batchSize){
        throw new UnsupportedOperationException();
    }




    default Client createClient(String uri, HttpClient httpClient, int requestBatchSize){
        throw new UnsupportedOperationException();
    }


    default ProtocolParser createProtocolParser(){
        throw new UnsupportedOperationException();
    }


    default EventManager eventManagerProxy() {
        return null;
    }


    default void clearEventManagerProxy() {
    }


}
