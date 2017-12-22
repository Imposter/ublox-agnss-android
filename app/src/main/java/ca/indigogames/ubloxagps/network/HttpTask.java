package ca.indigogames.ubloxagps.network;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CancellationException;

import ca.indigogames.ubloxagps.task.AsyncTask;
import ca.indigogames.ubloxagps.task.AsyncTaskResult;

/**
 * Used for asynchronous HTTP communication. Wrapper class for HttpRequest returning streams instead
 * of HTTP responses
 */
public abstract class HttpTask<TResult> extends AsyncTask<TResult> {
    private static final int DEFAULT_TIMEOUT = 1000;

    private String mUrl;
    private String mMethod;
    private Map<String, List<String>> mHeaders;
    private byte[] mData;
    private int mTimeout;
    private HttpResponse mResponse;

    /**
     * Default constructor
     */
    public HttpTask() {
        mUrl = "";
        mMethod = "GET";
        mHeaders = new TreeMap<>();
        mData = new byte[0];
        mTimeout = DEFAULT_TIMEOUT;
    }

    /**
     * Initializes task with HTTP url and data
     *
     * @param url  Request URL
     * @param data Request data as string
     */
    public HttpTask(String url, String data) {
        mUrl = url;
        mMethod = "POST";
        mHeaders = new TreeMap<>();
        mData = data.getBytes();
        mTimeout = DEFAULT_TIMEOUT;
    }

    /**
     * Initializes task with HTTP url and data
     *
     * @param url  Request URL
     * @param data Request data as byte array
     */
    public HttpTask(String url, byte[] data) {
        mUrl = url;
        mMethod = "POST";
        mHeaders = new TreeMap<>();
        mData = data;
        mTimeout = DEFAULT_TIMEOUT;
    }

    /**
     * Initializes task with HTTP url and response timeout
     *
     * @param url     Request URL
     * @param timeout Request timeout
     */
    public HttpTask(String url, int timeout) {
        mUrl = url;
        mMethod = "GET";
        mHeaders = new TreeMap<>();
        mTimeout = timeout;
    }

    /**
     * Initializes task with HTTP url and response timeout
     *
     * @param url Request URL
     */
    public HttpTask(String url) {
        mUrl = url;
        mMethod = "GET";
        mHeaders = new TreeMap<>();
        mTimeout = DEFAULT_TIMEOUT;
    }

    /**
     * Initializes task with HTTP url, data and response timeout
     *
     * @param url     Request URL
     * @param data    Request data as string
     * @param timeout Request timeout
     */
    public HttpTask(String url, String data, int timeout) {
        mUrl = url;
        mMethod = "GET";
        mHeaders = new TreeMap<>();
        if (data != null) {
            mData = data.getBytes();
            mMethod = "POST";
        }
        mTimeout = timeout;
    }

    /**
     * Initializes task with HTTP url, data and response timeout
     *
     * @param url     Request URL
     * @param data    Request data as byte array
     * @param timeout Request timeout
     */
    public HttpTask(String url, byte[] data, int timeout) {
        mUrl = url;
        mMethod = "POST";
        mHeaders = new TreeMap<>();
        mData = data;
        mTimeout = timeout;
    }

    /**
     * Gets request URL
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Sets request URL
     *
     * @param url HTTP request URL
     */
    public void setUrl(String url) {
        mUrl = url;
    }

    /**
     * Gets request method
     */
    public String getMethod() {
        return mMethod;
    }

    /**
     * Sets request method
     *
     * @param method HTTP request method
     */
    public void setMethod(String method) {
        mMethod = method;
    }

    /**
     * Gets HTTP request headers
     *
     * @return HTTP request headers
     */
    public Map<String, List<String>> getHeaders() {
        return mHeaders;
    }

    /**
     * Sets HTTP request headers
     *
     * @param headers HTTP request headers
     */
    public void setHeaders(Map<String, List<String>> headers) {
        mHeaders = headers;
    }

    /**
     * Sets HTTP request headers
     *
     * @param key Header key
     */
    public List<String> getHeader(String key) {
        return mHeaders.get(key);
    }

    /**
     * Sets HTTP request headers
     *
     * @param key   Header key
     * @param value Header value
     */
    public void setHeader(String key, List<String> value) {
        mHeaders.put(key, value);
    }

    /**
     * Sets request data
     *
     * @param data HTTP request data
     */
    public void setData(String data) {
        mData = data.getBytes();
    }

    /**
     * Sets request data
     *
     * @param data HTTP request data
     */
    public void setData(byte[] data) {
        mData = data;
    }

    /**
     * Gets timeout
     */
    public int getTimeout() {
        return mTimeout;
    }

    /**
     * Sets timeout
     *
     * @param timeout HTTP request timeout
     */
    public void setTimeout(int timeout) {
        mTimeout = timeout;
    }

    /**
     * Processes request asynchronously
     *
     * @return HTTP stream
     * @throws Exception Thrown if processing failed
     */
    @Override
    protected TResult process() throws Exception {
        // End if task was cancelled
        if (isCancelled())
            throw new CancellationException("Task cancelled");

        // Check if a request already exists
        if (mResponse != null) {
            throw new IllegalAccessException("Task already completed");
        }

        // Create request
        final HttpRequest request = new HttpRequest(mUrl, mData);
        request.setTimeout(mTimeout);
        request.setMethod(mMethod);
        request.setHeaders(mHeaders);

        // Process request
        mResponse = request.getResponse();

        // Return response
        return processResponse(mResponse);
    }

    /**
     * Must be implemented to process the response and convert the response data to the desired
     * format
     * @param response HTTP response sent by the server
     * @return A converted result object, using the data from the server response
     * @throws Exception Thrown if processing failed
     */
    protected abstract TResult processResponse(HttpResponse response) throws Exception;
}
