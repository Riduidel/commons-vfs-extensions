package fr.perigee.commonsvfs.truezip.zip;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;

public class TestUtils {

	private static final String VFS_PROVIDERS = "META-INF/test-vfs-providers.xml";

	/**
	 * Obtain a File reference to loaded zip file
	 * 
	 * @return
	 * @throws URISyntaxException
	 */
	public static File getZipFile() throws URISyntaxException {
		URL testZipUrl = CanReadZipTest.class.getClassLoader().getResource("testzip.zip");
		File testZipFile = new File(testZipUrl.toURI());
		return testZipFile;
	}

	/**
	 * Change protocol of URI to given one
	 * 
	 * @param scheme
	 * @param uri
	 * @return
	 * @throws URISyntaxException
	 */
	public static URI usingProtocol(String scheme, URI uri) throws URISyntaxException {
		URI returned = new URI(scheme, uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), uri.getQuery(), uri.getFragment());
		return returned;
	}

	public static FileSystemManager createFileSystemManager() throws FileSystemException {
		StandardFileSystemManager manager = new StandardFileSystemManager();
		manager.setConfiguration(TestUtils.class.getClassLoader().getResource(VFS_PROVIDERS));
		manager.init();
		return manager;
	}

	static final String FILE_A_TEXT = "A text";
}
