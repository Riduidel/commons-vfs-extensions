package fr.perigee.commonsvfs.truezip.zip;

import java.util.Collection;

import org.apache.commons.vfs2.Capability;
import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystem;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.provider.AbstractFileName;
import org.apache.commons.vfs2.provider.AbstractFileSystem;

import de.schlichtherle.truezip.file.TArchiveDetector;

public class ZipFileSystem extends AbstractFileSystem implements FileSystem {


	private TArchiveDetector archiveDector;

	protected ZipFileSystem(FileName rootName, FileObject parent, FileSystemOptions fileSystemOptions, TArchiveDetector tArchiveDetector) {
		super(rootName, parent, fileSystemOptions);
		this.archiveDector = tArchiveDetector;
	}

	@Override
	protected FileObject createFile(AbstractFileName name) throws Exception {
		return new ZipFileObject(name, getParentLayer(), this);
	}

    /**
     * Adds the capabilities of this file system.
     */
    @Override
    protected void addCapabilities(final Collection<Capability> caps)
    {
        caps.addAll(ZipFileProvider.capabilities);
    }

	/**
	 * @return the archiveDector
	 * @category getter
	 * @category archiveDector
	 */
	public TArchiveDetector getArchiveDector() {
		return archiveDector;
	}
}
