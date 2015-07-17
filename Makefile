

all : tereg


ifeq ($(OS),Windows_NT)
ANT = .\\apache-ant\\bin\\ant.bat
else
ANT = ./apache-ant/bin/ant
endif

tereg :
	$(ANT)
	
clean :
	rm bin/*