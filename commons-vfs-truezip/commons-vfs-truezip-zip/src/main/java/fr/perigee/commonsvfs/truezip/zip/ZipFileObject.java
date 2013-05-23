package fr.perigee.commonsvfs.truezip.zip;

import java.io.InputStream;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.provider.AbstractFileName;
import org.apache.commons.vfs2.provider.AbstractFileObject;
import org.apache.commons.vfs2.provider.AbstractFileSystem;

import de.schlichtherle.truezip.file.TFile;

public class ZipFileObject extends AbstractFileObject implements FileObject {
	private static final String MIME_DIRECTORY = "httpd/unix-directory";
	private ZipFileSystemConfigBuilder builder;
	private String urlCharset;
	/**
	 * Container zip, from which the file will be built
	 */
	private FileObject containerZip;
	/**
	 * Lazy loaded file
	 */
	private TFile file;

	public ZipFileObject(AbstractFileName name, FileObject fileObject, ZipFileSystem fileSystem) {
		super(name, fileSystem);
		this.containerZip = fileObject;
        builder = (ZipFileSystemConfigBuilder) ZipFileSystemConfigBuilder.getInstance();
        this.urlCharset = builder.getUrlCharset(getFileSystem().getFileSystemOptions());
	}
	
	private TFile getFile() {
		if(file==null) {
			String fullPath = containerZip.getName().getFriendlyURI()+getName().getPath();
			file = new TFile(fullPath, ((ZipFileSystem) getFileSystem()).getArchiveDector());
		}
		return file;
	}

	@Override
	protected FileType doGetType() throws Exception {
		if(getFile().isDirectory()) {
			return FileType.FOLDER;
		} else {
			return FileType.FILE;
		}
	}

	@Override
	protected String[] doListChildren() throws Exception {
		return getFile().list();
	}

	@Override
	protected long doGetContentSize() throws Exception {
		return getFile().length();
	}

	@Override
	protected InputStream doGetInputStream() throws Exception {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("method "+AbstractFileObject.class.getName()+"#doGetInputStream has not yet been implemented AT ALL");
	}
	
	
}
