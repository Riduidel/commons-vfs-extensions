package fr.perigee.commonsvfs.truezip.zip;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.AllFileSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileType;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class CanWriteZipTest extends AbstractCommonsVFSZipTest {

	@Test
	public void canCreateAndWriteWriteAFileInArchive() throws Exception {
		FileObject loaded = getFileObject(manager);
		FileObject cFile = loaded.resolveFile(TestConstants.FILE_C_NAME);
		if(!cFile.exists())
			cFile.createFile();
		assertThat(cFile.exists(), Is.is(true));
		assertThat(cFile.isReadable(), Is.is(true));
		assertThat(cFile.isWriteable(), Is.is(true));
		assertThat(cFile.getType(), Is.is(FileType.FILE));
		// file was modified less than 10 ms ago
		assertThat(System.currentTimeMillis()-cFile.getContent().getLastModifiedTime()<10, Is.is(true)); 
		OutputStream outputStream = cFile.getContent().getOutputStream();
		IOUtils.write(TestConstants.FILE_C_TEXT, outputStream);
		outputStream.close();
		// now read that content (which is a known working operation, thanks to CanReadZipTest)
		String cText = IOUtils.toString(cFile.getContent().getInputStream());
		assertThat(cText, Is.is(TestConstants.FILE_C_TEXT));
		assertThat(cFile.getContent().getSize(), IsNot.not(0l));
	}

	@Test
	public void canDeleteAFileInArchive() throws Exception {
		FileObject loaded = getFileObject(manager);
		FileObject cFile = loaded.resolveFile(TestConstants.FILE_C_NAME);
		if(!cFile.exists())
			cFile.createFile();
		assertThat(cFile.exists(), Is.is(true));
		cFile.delete();
		assertThat(cFile.exists(), Is.is(false));
	}

	@Test
	public void canCreateAFolderInArchive() throws Exception {
		FileObject loaded = getFileObject(manager);
		FileObject dFile = loaded.resolveFile(TestConstants.FOLDER_D_NAME);
		if(!dFile.exists())
			dFile.createFolder();
		assertThat(dFile.exists(), Is.is(true));
		assertThat(dFile.isReadable(), Is.is(true));
		assertThat(dFile.isWriteable(), Is.is(true));
		assertThat(dFile.isHidden(), Is.is(false));
		assertThat(dFile.getType(), Is.is(FileType.FOLDER));
		dFile.delete(new AllFileSelector());
		assertThat(dFile.exists(), Is.is(false));
	}

	@Test
	public void canCreateAFolderThenAFileInArchive() throws Exception {
		FileObject loaded = getFileObject(manager);
		FileObject dFile = loaded.resolveFile(TestConstants.FOLDER_D_NAME);
		if(!dFile.exists())
			dFile.createFolder();
		FileObject eFile = dFile.resolveFile(TestConstants.FILE_E_NAME);
		OutputStream outputStream = eFile.getContent().getOutputStream();
		IOUtils.write(TestConstants.FILE_E_TEXT, outputStream);
		outputStream.close();
		// now read that content (which is a known working operation, thanks to CanReadZipTest)
		String cText = IOUtils.toString(eFile.getContent().getInputStream());
		assertThat(cText, Is.is(TestConstants.FILE_E_TEXT));
	}
}
