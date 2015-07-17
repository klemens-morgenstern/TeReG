

all : tereg


include source.mk

ifeq ($(OS),Windows_NT)
ANT = .\\apache-ant\\bin\\ant.bat
else
ANT = ./apache-ant/bin/ant
endif

bin/TeReG.jar : $(SRCS)
	$(ANT)
	
tereg : bin/TeReG.jar

	
clean :
	rm bin/*