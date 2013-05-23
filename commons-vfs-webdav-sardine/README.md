Commons-VFS Webdav implementation using Webdav
=====

Title says quite all, no ? This project was started as an answer to both a question on [StackOverflow](http://stackoverflow.com/q/9920816/15619), and to [one on my blog](http://riduidel.wordpress.com/2013/02/12/commons-vfs/#comment-255) asking for some code.

Want to integrate that in your commons-vfs installation ?

Simple !

Insert (and create, if it is not yet the case) a vfs-providers.xml file containing at least

	<provider
		class-name="fr.perigee.commonsvfs.webdav.WebdavFileProvider">
		<scheme name="webdav" />
	</provider>

And make sure CommonsVFS is loaded with this configuration file, and it will work.