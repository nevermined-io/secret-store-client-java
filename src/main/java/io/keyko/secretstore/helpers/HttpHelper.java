package io.keyko.secretstore.helpers;

import io.keyko.secretstore.models.HttpResponse;
import org.apache.commons.codec.Charsets;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * HTTP Helper functions
 */
public abstract class HttpHelper {

    protected static final Logger log = LogManager.getLogger(HttpHelper.class);

    private HttpHelper() {
    }

    /**
     * Send a HTTP POST request and return the body
     * @param url url to call
     * @return returned http body
     * @throws HttpException Http error
     */
    public static final String httpClientPostBody(String url) throws HttpException {
        return httpClientPost(url, new ArrayList<>()).getBody();
    }

    /**
     * Send a HTTP POST request with parameters and return the body
     * @param url url to call
     * @param list parameters
     * @return returned http body
     * @throws HttpException Http error
     */
    public static final String httpClientPostBody(String url, ArrayList<NameValuePair> list) throws HttpException {
        return httpClientPost(url, list).getBody();
    }

    /**
     * Send a HTTP POST request and return the HttpResponse object
     * @param url url to call
     * @return HttpResponse returned
     * @throws HttpException Http error
     */
    public static final HttpResponse httpClientPost(String url) throws HttpException {
        return httpClientPost(url, new ArrayList<>());
    }

    /**
     * Send a HTTP POST request with parameters and return the HttpResponse object
     * @param url url to call
     * @param list parameters
     * @return HttpResponse returned
     * @throws HttpException Http error
     */
    public static final HttpResponse httpClientPost(String url, ArrayList<NameValuePair> list) throws HttpException {

        HttpResponse response;
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        try {
            if (list.size() >0) {
                NameValuePair[] params = new NameValuePair[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    params[i] = list.get(i);
                }
                postMethod.addParameters(params);
            }

            client.executeMethod(postMethod);
            response = new HttpResponse(
                    postMethod.getStatusCode(),
                    IOUtils.toString(postMethod.getResponseBodyAsStream(), Charsets.UTF_8),
                    postMethod.getResponseCharSet(),
                    postMethod.getResponseContentLength()
            );
        } catch (Exception e) {
            log.error("Error in HTTP POST request " + e.getMessage());
            throw new HttpException("Error in HTTP POST request");
        } finally {
            postMethod.releaseConnection();
        }
        return response;
    }

    /**
     * Send a HTTP GET request and return the HttpResponse object
     * @param url url to call
     * @return HttpResponse returned
     * @throws HttpException Http error
     */
    public static final HttpResponse httpClientGet(String url) throws HttpException {

        HttpResponse response;
        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        try {

            client.executeMethod(getMethod);
            response = new HttpResponse(
                    getMethod.getStatusCode(),
                    IOUtils.toString(getMethod.getResponseBodyAsStream(), Charsets.UTF_8),
                    getMethod.getResponseCharSet(),
                    getMethod.getResponseContentLength()
            );

        } catch (Exception e) {
            log.error("Error in HTTP GET request " + e.getMessage());
            throw new HttpException("Error in HTTP GET request");
        } finally {
            getMethod.releaseConnection();
        }
        return response;
    }

}
