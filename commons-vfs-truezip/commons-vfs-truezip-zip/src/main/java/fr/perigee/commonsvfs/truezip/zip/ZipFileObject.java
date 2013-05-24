package fr.perigee.commonsvfs.truezip.zip;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSelector;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.provider.AbstractFileName;
import org.apache.commons.vfs2.provider.AbstractFileObject;
import org.apache.commons.vfs2.provider.AbstractFileSystem;

import de.schlichtherle.truezip.file.TFile;
import de.schlichtherle.truezip.file.TFileInputStream;
import de.schlichtherle.truezip.file.TFileOutputStream;
import de.schlichtherle.truezip.file.TVFS;

public class ZipFileObject extends AbstractFileObject implements FileObject {
	/**
	 * Container zip, from which the file will be built
	 */
	private FileObject containerZip;
	/**
	 * Lazy loaded file
	 */
	private TFile file;

	public ZipFileObject(AbstractFileName name, FileObject parent, ZipFileSystem fileSystem) {
		super(name, fileSystem);
		this.containerZip = parent;
	}
	
	private TFile getFile() {
		if(file==null) {
			String fullPath = containerZip.getName().getPath()+getName().getPath();
			file = new TFile(fullPath);
		}
		return file;
	}

	@Override
	protected FileType doGetType() throws Exception {
		if(getFile().exists()) {
			if(getFile().isDirectory()) {
				return FileType.FOLDER;
			} else if(getFile().isFile()){
				return FileType.FILE;
			} else {
				return FileType.IMAGINARY;
			}
		} else {
			// ahve to return imaginary when file doesn't exist ... strange
			return FileType.IMAGINARY;
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
		return new TFileInputStream(getFile());
	}
	
	/**
	 * Lazy load file by calling the {@link #getFile()} method
	 * @throws Exception
	 * @see org.apache.commons.vfs2.provider.AbstractFileObject#doAttach()
	 */
	@Override
	protected void doAttach() throws Exception {
		getFile();
	}
	
	@Override
	protected void doDelete() throws Exception {
		getFile().rm();
	}
	
	@Override
	protected long doGetLastModifiedTime() throws Exception {
		return getFile().lastModified();
	}
	
	@Override
	protected OutputStream doGetOutputStream(boolean bAppend) throws Exception {
		return new TFileOutputStream(getFile(), bAppend);
	}
	
	@Override
	protected void doCreateFolder() throws Exception {
		getFile().mkdir(true);
	}
	
	@Override
	protected boolean doIsHidden() throws Exception {
		return getFile().isHidden();
	}
	
	@Override
	protected boolean doIsReadable() throws Exception {
		return getFile().canRead();
	}
	
	@Override
	protected boolean doIsWriteable() throws Exception {
		return getFile().canWrite();
	}
	
	@Override
	protected void doDetach() throws Exception {
		TVFS.umount(getFile());
	}
}
