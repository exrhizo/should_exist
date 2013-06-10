#should_exist
============
###This readme is underconstruction


#Git
We will be using git for this project. [Pro Git book](http://git-scm.com/documentation) is a good resource for using it.
You will need to get an [ssh key](https://help.github.com/articles/generating-ssh-keys) to commit changes.
However the main things to know are:

###Getting the repository
To download the repository first do this:

	git clone git@github.com:ftalex/should_exist.git

And that will copy the directory to your computer as well as a .git file that keeps track of the versioning.
Now you can make changes in to the working directory but they aren't updated on the web server until you 'push' them.

###Committing Changes
Now that you have edited or added files to the directory, you want git track them and then commit them.
Tracking means that git is watching files and they will be committed when you run that command next. 

	git add filename.ext

adds the file to be tracked, or when you want to commit changes into a version use

	git commit

or to add all the modified files (won't add new files) and commit at the same time

	git commit -a

when you commit it will ask you to put a message explaining the commit, it can be easier to just do it as one command (this examle shows how to add one file also):

	git commit -m "added the paper" -a paper.pdf

If at anytime you want to see what is happening use

	git status

Or to see how something has changed use

	git diff file.ext

To understand the different stages skim [this page](http://git-scm.com/book/en/Git-Basics-Recording-Changes-to-the-Repository).

###Publishing your changes
After you have made changes to the files and want to update them on the web server (do this often) use:

	git push

But you must commit your changes before you push otherwise it will only update up to the last commit

###Getting the latest version
If changes have been made to the repository, you may want to download the latest version. Use:

	git pull

while in the directory to get the latest version.