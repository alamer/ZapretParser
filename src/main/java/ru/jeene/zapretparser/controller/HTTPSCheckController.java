/*
 $Author$
 $Date$
 $Revision$
 $Source$
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jeene.zapretparser.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLProtocolException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import ru.jeene.zapretparser.models.ResponseResult;

/**
 *
 * @author ivc_ShherbakovIV
 */
public class HTTPSCheckController {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(HTTPCheckController.class);

    public String getContent(String url_string) throws KeyManagementException, NoSuchAlgorithmException, MalformedURLException, IOException {
        HttpsURLConnection con = (HttpsURLConnection) openConnection(url_string);
        StringBuilder b = new StringBuilder();
        Reader reader = new InputStreamReader(con.getInputStream());
        while (true) {
            int ch = reader.read();
            if (ch == -1) {
                break;
            }
            //System.out.print((char)ch);
            b.append((char) ch);
        }
        //System.out.print(b.toString());
        return b.toString();
    }

    public ResponseResult checkUrl(String url_string) {
        ResponseResult res = ResponseResult.UNKNOWN;
        try {
            HttpsURLConnection connection = (HttpsURLConnection) openConnection(url_string);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                res = ResponseResult.getById(responseCode);

            } else {
                //
                String current_url = connection.getURL().toString();
                if (!current_url.contains("zapret-info.dsi.ru") && connection.getContentLength() > 0) {
                    res = ResponseResult.WORKED;
                } else {
                    res = ResponseResult.BLOCKED;
                }
            }
            return res;
        }//
        catch (UnknownHostException ex) {
            res = ResponseResult.UNKNOWN_HOST;
        } catch (SSLHandshakeException ex) {
            res = ResponseResult.SSLHANDSHAKE_EXCEPTION;
        } catch (SocketTimeoutException | ConnectException ex) {
            res = ResponseResult.TIMEOUT;
        } catch (SocketException ex) {
            res = ResponseResult.BLOCKED;
        } catch (ProtocolException ex) {
            res = ResponseResult.PROTOCOL_EXCEPTION;
        } catch (KeyManagementException ex) {
            logger.error(ex);
        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex);
        } catch (Exception ex) {
            res = ResponseResult.UNKNOWN;
            logger.error(ex);
        }
        return res;
    }

    private URLConnection openConnection(String url_string) throws KeyManagementException, NoSuchAlgorithmException, MalformedURLException, IOException {
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }

            }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        /*
         * end of the fix
         */

        URL url = new URL(url_string);
        URLConnection con = url.openConnection();
        return con;
    }

}
