package fr.perigee.commonsvfs.webdav;

import java.net.ProxySelector;

import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;

import com.github.sardine.Sardine;
import com.github.sardine.impl.SardineImpl;

public class TimedSardineImpl extends SardineImpl implements Sardine {

	private static final String SARDINE_SOCKET_TIMEOUT = "SARDINE_SOCKET_TIMEOUT";
	private static final String SARDINE_CONNECTION_TIMEOUT = "SARDINE_CONNECTION_TIMEOUT";

	public TimedSardineImpl(HttpClientBuilder http) {
		super(http);
	}

	public TimedSardineImpl(String username, String password) {
		super(username, password);
	}

	public TimedSardineImpl(String username, String password, ProxySelector selector) {
		super(username, password, selector);
	}

	public TimedSardineImpl(HttpClientBuilder http, String username, String password) {
		super(http, username, password);
	}

	@Override
	protected HttpClientBuilder configure(ProxySelector selector, CredentialsProvider credentials) {
        // forced to copy-paste, as the builder does not provide getters...
        RequestConfig requestConfig = RequestConfig.custom()
                .setExpectContinueEnabled(false)
                .setConnectTimeout(getConnectionTimeout())
                .setSocketTimeout(getSocketTimeout())
                .build();

        HttpClientBuilder builder = super.configure(selector, credentials);
		builder.setDefaultRequestConfig(requestConfig);
        return builder;
	}

	public static int getSocketTimeout() {
		return getFromSystemProperties(SARDINE_SOCKET_TIMEOUT);
	}

	protected static int getFromSystemProperties(String timeout) {
		try {
			return Integer.parseInt(System.getProperty(timeout));
		} catch(Exception e) {
			return 60*1000;
		}
	}

	public static int getConnectionTimeout() {
		return getFromSystemProperties(SARDINE_CONNECTION_TIMEOUT);
	}
}
