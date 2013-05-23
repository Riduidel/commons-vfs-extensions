package fr.perigee.commonsvfs.truezip.zip;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.hamcrest.core.Is;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class TestMavenProcess {
	@Test
	public void testZipExist() throws URISyntaxException {
		assertThat(TestUtils.getZipFile().exists(), Is.is(true));
	}

}
