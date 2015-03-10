/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ivc_ShherbakovIV
 */
public class LoadUrl {

    public LoadUrl() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
        try {
            URL url = new URL("http://alisa.esrr.oao.rzd:8082");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            System.out.println(code);
        } catch (UnknownHostException ex) {
            //@TODO тут не нашли хост
        } catch (MalformedURLException ex) {
            Logger.getLogger(LoadUrl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoadUrl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void hello2() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("http://alisa.esrr.oao.rzd:8082").openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.out.println(responseCode);
            }
        } catch (UnknownHostException ex) {
            System.out.println("Unknown Host");
        } catch (IOException ex) {
            Logger.getLogger(LoadUrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
