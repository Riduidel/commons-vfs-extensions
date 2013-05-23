package fr.perigee.commonsvfs.truezip.zip;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.vfs2.Capability;
import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystem;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.UserAuthenticationData;
import org.apache.commons.vfs2.provider.AbstractFileName;
import org.apache.commons.vfs2.provider.AbstractLayeredFileProvider;
import org.apache.commons.vfs2.provider.FileProvider;
import org.apache.commons.vfs2.provider.LayeredFileName;

import de.schlichtherle.truezip.file.TArchiveDetector;
import de.schlichtherle.truezip.file.TConfig;
import de.schlichtherle.truezip.fs.FsScheme;
import de.schlichtherle.truezip.fs.archive.zip.ZipDriver;
import de.schlichtherle.truezip.socket.sl.IOPoolLocator;

/**
 * Truezip backed file system provider
 * @author ndx
 *
 */
public class ZipFileProvider  extends AbstractLayeredFileProvider implements FileProvider {
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

	private TArchiveDetector archiveDetector;

    public ZipFileProvider()
    {
        super();
    }

	@Override
	public Collection<Capability> getCapabilities() {
		return capabilities;
	}
	
	@Override
	protected FileSystem doCreateFileSystem(String scheme, FileObject file, FileSystemOptions fileSystemOptions) throws FileSystemException {
		TConfig.get().setArchiveDetector(
				        new TArchiveDetector(
				            TArchiveDetector.NULL,
				            new Object[][] {
				                { "zip", new ZipDriver(IOPoolLocator.SINGLETON)},
				            }));
        final AbstractFileName rootName =
        	            new LayeredFileName(scheme, file.getName(), FileName.ROOT_PATH, FileType.FOLDER);
		return new ZipFileSystem(rootName, file, fileSystemOptions);
	}
}
