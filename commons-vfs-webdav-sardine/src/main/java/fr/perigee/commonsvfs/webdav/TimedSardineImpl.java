package fr.perigee.commonsvfs.webdav;

import java.net.ProxySelector;

import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.googlecode.sardine.Sardine;
import com.googlecode.sardine.impl.SardineImpl;

public class TimedSardineImpl extends SardineImpl implements Sardine {

	private static final String SARDINE_SOCKET_TIMEOUT = "SARDINE_SOCKET_TIMEOUT";
	private static final String SARDINE_CONNECTION_TIMEOUT = "SARDINE_CONNECTION_TIMEOUT";

	public TimedSardineImpl(AbstractHttpClient http) {
		super(http);
	}

	public TimedSardineImpl(String username, String password) {
		super(username, password);
	}

	public TimedSardineImpl(String username, String password, ProxySelector selector) {
		super(username, password, selector);
	}

	public TimedSardineImpl(AbstractHttpClient http, String username, String password) {
		super(http, username, password);
	}

	@Override
	protected HttpParams createDefaultHttpParams() {
		HttpParams returned = super.createDefaultHttpParams();
		HttpConnectionParams.setConnectionTimeout(returned, getConnectionTimeout());
		HttpConnectionParams.setSoTimeout(returned, getSocketTimeout());
		return returned;
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
