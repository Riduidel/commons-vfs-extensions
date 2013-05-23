package fr.perigee.commonsvfs.truezip.zip;
import java.io.File;
import java.net.URISyntaxException;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class CanReadZip {
	private static FileSystemManager manager;
	
	@BeforeClass
	public static void loadManager() throws FileSystemException {
		manager = TestUtils.createFileSystemManager();
	}

	@Test
	public void canReadAFile() throws URISyntaxException, FileSystemException {
		FileObject loaded = getFileObject();
		assertThat(loaded.exists(), Is.is(true));
		assertThat(loaded, Is.is(ZipFileObject.class));
	}
	@Test
	public void zipFileIsConsideredAsAFolder() throws URISyntaxException, FileSystemException {
		FileObject loaded = getFileObject();
		assertThat(loaded.getType(), Is.is(FileType.FOLDER));
	}

	@Test
	public void canListFileContent() throws URISyntaxException, FileSystemException {
		FileObject loaded = getFileObject();
		FileObject[] children = loaded.getChildren();
		assertThat(children.length, IsNot.not(0));
	}

	@Test
	public void canGetFileSize() throws URISyntaxException, FileSystemException {
		FileObject loaded = getFileObject();
		FileObject[] children = loaded.getChildren();
		assertThat(children.length, IsNot.not(0));
		for(FileObject child : children) {
			if(child.getType()==FileType.FILE)
				assertThat(child.getContent().getSize(), IsNot.not(0l));
		}
	}

	@Test
	public void canReadFileNamedA() throws URISyntaxException, FileSystemException {
		FileObject loaded = getFileObject();
		FileObject[] children = loaded.getChildren();
		assertThat(children.length, IsNot.not(0));
		for(FileObject child : children) {
			if(child.getName().getBaseName().startsWith("A")) {
				assertThat(child.getContent().getSize(), IsNot.not(0l));
				String type = child.getContent().getContentInfo().getContentType();
			}
		}
	}

	private FileObject getFileObject() throws URISyntaxException, FileSystemException {
		File testZipFile = TestUtils.getZipFile();
		// Notice the weird pattern : to address a publicly visible file, one has to write
		// zip:file://
		// that's what commons-vfs calls layered file provider
		// And the "/" at end denotes we want the directory inside the zip
		String path = "zip:"+TestUtils.getZipFile().toURI();
		FileObject loaded = manager.resolveFile(path);
		return loaded;
	}

	
}
