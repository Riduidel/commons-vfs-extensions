package fr.perigee.commonsvfs.truezip.zip;
import java.io.File;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.AllFileSelector;
import org.apache.commons.vfs2.FileObject;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class CanCreateAZipFileTest extends AbstractCommonsVFSZipTest {
	private static File createdZiPFile;

	@BeforeClass
	public static void canCreateAZipFile() throws Exception {
		File normalZipFile = TestUtils.getZipFile();
		File folder = normalZipFile.getParentFile();
		createdZiPFile = new File(folder, CanCreateAZipFileTest.class.getSimpleName()+".zip");
	}
	
	@After
	public void deleteZipFile() {
		createdZiPFile.delete();
	}
	
	@Test
	public void canCreateZipFileAndPutSomeContentIn() throws Exception {
		FileObject newZip = getFileObjectFrom(manager, createdZiPFile);
		assertThat(newZip.exists(), Is.is(false));
		// we call createFolder to create the zip as a folder
		newZip.createFolder();
		FileObject fileA = newZip.resolveFile(TestConstants.FILE_A_NAME);
		OutputStream outputStream = fileA.getContent().getOutputStream();
		IOUtils.write(TestConstants.FILE_A_TEXT, outputStream);
		outputStream.close();
		assertThat(IOUtils.toString(fileA.getContent().getInputStream()), Is.is(TestConstants.FILE_A_TEXT));
	}
	
	@Test
	public void canCopyBetweenZipFiles() throws Exception {
		FileObject newZip = getFileObjectFrom(manager, createdZiPFile);
		FileObject oldZip = getFileObject(manager);
		FileObject sourceFileB = oldZip.resolveFile(TestConstants.FILE_B_NAME);
		FileObject targetFileB = newZip.resolveFile(TestConstants.FILE_B_NAME);
		targetFileB.copyFrom(sourceFileB, new AllFileSelector());
	}
}
