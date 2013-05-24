Commons-VFS archive implementation using TrueZip
=====

Title says quite all, no ? This project was started since default Commons-VFS zip implementation doesn't support write operations.

And, since TrueZip is quite versatile, I tried to use that to provide a flexible set of extensions

Want to integrate that in your commons-vfs installation ?

Simple !

Insert (and create, if it is not yet the case) a vfs-providers.xml file containing at least

 	<provider class-name="fr.perigee.commonsvfs.truezip.zip.ZipFileProvider">
		<scheme name="zip" />
	</provider>

And make sure CommonsVFS is loaded with this configuration file, and it will work.

**ATTENTION** : do not forget to comment default zip file system provider !

Notice that, if you want to be able to create zip files, you have to keep the LocalFileProvider as is :

 	<provider
		class-name="org.apache.commons.vfs2.provider.local.DefaultLocalFileProvider">
		<scheme name="file" />
	</provider>
