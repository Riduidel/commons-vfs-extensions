package fr.perigee.commonsvfs.truezip.zip;

import java.io.File;
import java.net.URISyntaxException;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.junit.BeforeClass;

public abstract class AbstractCommonsVFSZipTest {

	protected static FileSystemManager manager;

	@BeforeClass
	public static void loadManager() throws FileSystemException {
		manager = TestUtils.createFileSystemManager();
	}

	protected static FileObject getFileObject(FileSystemManager manager) throws URISyntaxException, FileSystemException {
		return getFileObjectFrom(manager, TestUtils.getZipFile());
	}

	protected static FileObject getFileObjectFrom(FileSystemManager manager, File file) throws URISyntaxException, FileSystemException {
		// Notice the weird pattern : to address a publicly visible file, one has to write
		// zip:file://
		// that's what commons-vfs calls layered file provider
		// And the "/" at end denotes we want the directory inside the zip
		String path = "zip:"+file.toURI();
		FileObject loaded = manager.resolveFile(path);
		return loaded;
	}

}
