package fr.perigee.commonsvfs.webdav;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.vfs2.Capability;
import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystem;
import org.apache.commons.vfs2.FileSystemConfigBuilder;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.UserAuthenticationData;
import org.apache.commons.vfs2.provider.AbstractOriginatingFileProvider;
import org.apache.commons.vfs2.util.UserAuthenticatorUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import com.github.sardine.impl.SardineImpl;

/**
 * Sardine-backed Webdav file provider
 * @author ndx
 *
 */
public class WebdavFileProvider extends AbstractOriginatingFileProvider {
    /** The authenticator types used by the WebDAV provider. */
    public static final UserAuthenticationData.Type[] AUTHENTICATOR_TYPES = new UserAuthenticationData.Type[]
        {
            UserAuthenticationData.USERNAME, UserAuthenticationData.PASSWORD
        };

    /** The capabilities of the WebDAV provider */
    protected static final Collection<Capability> capabilities =
            Collections.unmodifiableCollection(Arrays.asList(new Capability[]
    {
        Capability.CREATE,
        Capability.DELETE,
        Capability.RENAME,
        Capability.GET_TYPE,
        Capability.LIST_CHILDREN,
        Capability.READ_CONTENT,
        Capability.URI,
        Capability.WRITE_CONTENT,
        Capability.GET_LAST_MODIFIED,
        Capability.ATTRIBUTES,
//        Capability.RANDOM_ACCESS_READ,
        Capability.DIRECTORY_READ_CONTENT,
    }));

    public WebdavFileProvider()
    {
        super();

        setFileNameParser(WebdavFileNameParser.getInstance());
    }
    /**
     * Creates a {@link FileSystem}. Creates in fact a Sardine (with various required elements for auth and proxy handling) configured to connect to described host 
     * @see org.apache.commons.vfs2.impl.DefaultFileSystemManager#resolveFile(FileObject, String, FileSystemOptions)
     */
    @Override
    protected FileSystem doCreateFileSystem(final FileName name, final FileSystemOptions fileSystemOptions)
        throws FileSystemException
    {
        final URLFileName rootName = (URLFileName) name;
        // Create the file system
        FileSystemOptions fsOpts = (fileSystemOptions == null) ? new FileSystemOptions() : fileSystemOptions;

        UserAuthenticationData authData = null;
        DefaultHttpClient httpClient;
        Sardine sardine;

        try
        {
            authData = UserAuthenticatorUtils.authenticate(fsOpts, AUTHENTICATOR_TYPES);
            String username = UserAuthenticatorUtils.toString(UserAuthenticatorUtils.getData(authData,
			        UserAuthenticationData.USERNAME, UserAuthenticatorUtils.toChar(rootName.getUserName())));
			String password = UserAuthenticatorUtils.toString(UserAuthenticatorUtils.getData(authData,
			        UserAuthenticationData.PASSWORD, UserAuthenticatorUtils.toChar(rootName.getPassword())));
            WebdavFileSystem returned = new WebdavFileSystem(rootName, createSardine(username, password), fsOpts);
            // TODO find a way to make sure server filesystem is OK
            return returned;
        }
        finally
        {
            UserAuthenticatorUtils.cleanup(authData);
        }
    }

    /**
     * Create a sardine instance with the given auth and the right set of http params (including the crazy timeouts)
     * @param username
     * @param password
     * @return
     */
    private Sardine createSardine(String username, String password) {
    	return new TimedSardineImpl(username, password);
	}
	@Override
    public FileSystemConfigBuilder getConfigBuilder()
    {
        return WebdavFileSystemConfigBuilder.getInstance();
    }


    public Collection<Capability> getCapabilities()
    {
        return capabilities;
    }

    public FileName parseUri(FileName base, String uri) throws FileSystemException
    {
        if (getFileNameParser() != null)
        {
            return getFileNameParser().parseUri(getContext(), base, uri);
        }

        throw new FileSystemException("vfs.provider/filename-parser-missing.error");
        // return GenericFileName.parseUri(getFileNameParser(), uri, 0);
    }
}
