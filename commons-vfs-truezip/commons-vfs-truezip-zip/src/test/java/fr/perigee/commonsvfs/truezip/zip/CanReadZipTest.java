package fr.perigee.commonsvfs.truezip.zip;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.operations.FileOperation;
import org.apache.commons.vfs2.operations.FileOperations;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.assertThat;

public class CanReadZipTest extends AbstractCommonsVFSZipTest {
	@Test
	public void canReadAFile() throws URISyntaxException, FileSystemException {
		FileObject loaded = getFileObject(manager);
		assertThat(loaded.exists(), Is.is(true));
		assertThat(loaded, Is.is(ZipFileObject.class));
	}
	
	@Test
	public void zipFileIsConsideredAsAFolder() throws URISyntaxException, FileSystemException {
		FileObject loaded = getFileObject(manager);
		assertThat(loaded.getType(), Is.is(FileType.FOLDER));
	}

	@Test
	public void canListFileContent() throws URISyntaxException, FileSystemException {
		FileObject loaded = getFileObject(manager);
		FileObject[] children = loaded.getChildren();
		assertThat(children.length, IsNot.not(0));
	}

	@Test
	public void canGetFileSize() throws URISyntaxException, FileSystemException {
		FileObject loaded = getFileObject(manager);
		FileObject[] children = loaded.getChildren();
		assertThat(children.length, IsNot.not(0));
		for(FileObject child : children) {
			if(child.getType()==FileType.FILE)
				assertThat(child.getContent().getSize(), IsNot.not(0l));
		}
	}

	@Test
	public void canReadFileNamedA() throws URISyntaxException, IOException {
		FileObject loaded = getFileObject(manager);
		FileObject[] children = loaded.getChildren();
		assertThat(children.length, IsNot.not(0));
		FileObject aFile = loaded.getChild(TestConstants.FILE_A_NAME);
		assertThat(aFile.getContent().getSize(), IsNot.not(0l));
		String type = aFile.getContent().getContentInfo().getContentType();
		assertThat(type, IsNull.notNullValue());
		String text = IOUtils.toString(aFile.getContent().getInputStream());
		assertThat(text, Is.is(TestConstants.FILE_A_TEXT));
	}
	
	@Test
	public void getAttributesIsNotSupportedInZipFile() throws Exception {
		FileObject loaded = getFileObject(manager);
		assertThat(loaded.exists(), Is.is(true));
		String[] attributes = loaded.getContent().getAttributeNames();
		assertThat(attributes.length, Is.is(0));
		
	}
}
