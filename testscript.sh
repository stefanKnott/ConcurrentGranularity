#/!/bin/bash

#author: Stefan Knott

LO_THREADS=4
MED_THREADS=8
HI_THREADS=12
COARSE=0
FINE=1
LAZY=2
#need to implement laziest algorithm...still stuck in deadlock
HI_ADD_LO_REM=0
HI_ADD_LO_CONT=1
HI_ADD_HI_CONT=2
LO_ADD_HI_CONT=3
TIMEFORMAT="wall=%e user=%U system=%S CPU=%P i-switched=%c v-switched=%w \n"


clear
echo Building code...
javac ListDriver.java

echo LOW THREADS....................................................................................

echo HIGH ADD OPERATIONS LOW REMOVAL OPERATIONS
echo Inserting numbers into a list using $LOW_THREADS THREADS, COARSE-GRAINED algorithm and HI_ADD_LO_REM operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $COARSE $HI_ADD_LO_REM $LO_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS, FINE-GRAINED algorithm and HI_ADD_LO_REM operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $FINE $HI_ADD_LO_REM $LO_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS LAZY algorithm and HI_ADD_LO_REM operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $LAZY  $HI_ADD_LO_REM $LO_THREADS 


echo ...........................................................................

echo HIGH ADD OPERATION LOW CONTAINS OPERATIONS
echo Inserting numbers into a list using $LOW_THREADS THREADS, COARSE-GRAINED algorithm and HI_ADD_LO_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $COARSE $HI_ADD_LO_CONT $LO_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS, FINE-GRAINED algorithm and HI_ADD_LO_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $FINE $HI_ADD_LO_CONT $LO_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS LAZY algorithm and HI_ADD_LO_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $LAZY  $HI_ADD_LO_CONT $LO_THREADS 

echo ............................................................................

echo HIGH ADD OPERATION HIGH CONTAINS OPERATIONS
echo Inserting numbers into a list using $LOW_THREADS THREADS, COARSE-GRAINED algorithm and HI_ADD_HI_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $COARSE $HI_ADD_HI_CONT $LO_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS, FINE-GRAINED algorithm and HI_ADD_HI_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $FINE $HI_ADD_HI_CONT $LO_THREADS  

echo Inserting numbers into a list using $LOW_THREADS THREADS LAZY algorithm and HI_ADD_HI_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $LAZY  $HI_ADD_HI_CONT $LO_THREADS 


echo LO ADD OPERATION HIGH CONTAINS OPERATIONS
echo Inserting numbers into a list using $LOW_THREADS THREADS, COARSE-GRAINED algorithm and LO_ADD_HI_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $COARSE $LO_ADD_HI_CONT $LO_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS, FINE-GRAINED algorithm and LO_ADD_HI_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $FINE $LO_ADD_HI_CONT $LO_THREADS  

echo Inserting numbers into a list using $LOW_THREADS THREADS LAZY algorithm and LO_ADD_HOI_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $LAZY  $LO_ADD_HI_CONT $LO_THREADS 

echo ................................................................................................
echo ................................................................................................
echo ................................................................................................

echo MED THREADS.....................................................................................
echo HIGH ADD OPERATIONS LOW REMOVAL OPERATIONS
echo Inserting numbers into a list using $LOW_THREADS THREADS, COARSE-GRAINED algorithm and HI_ADD_LO_REM operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $COARSE $HI_ADD_LO_REM $MED_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS, FINE-GRAINED algorithm and HI_ADD_LO_REM operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $FINE $HI_ADD_LO_REM $MED_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS LAZY algorithm and HI_ADD_LO_REM operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $LAZY  $HI_ADD_LO_REM $MED_THREADS 
echo .............................................................................

echo HIGH ADD OPERATION LOW CONTAINS OPERATIONS
echo Inserting numbers into a list using $LOW_THREADS THREADS, COARSE-GRAINED algorithm and HI_ADD_LO_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $COARSE $HI_ADD_LO_CONT $MED_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS, FINE-GRAINED algorithm and HI_ADD_LO_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $FINE $HI_ADD_LO_CONT $MED_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS LAZY algorithm and HI_ADD_LO_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $LAZY  $HI_ADD_LO_CONT $MED_THREADS 

echo ...........................................................................

echo HIGH ADD OPERATION HIGH CONTAINS OPERATIONS
echo Inserting numbers into a list using $LOW_THREADS THREADS, COARSE-GRAINED algorithm and HI_ADD_LO_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $COARSE $HI_ADD_HI_CONT $MED_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS, FINE-GRAINED algorithm and HI_ADD_LO_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $FINE $HI_ADD_HI_CONT $MED_THREADS  

echo Inserting numbers into a list using $LOW_THREADS THREADS LAZY algorithm and HI_ADD_LO_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $LAZY  $HI_ADD_HI_CONT $MED_THREADS 


echo LO ADD OPERATION HIGH CONTAINS OPERATIONS
echo Inserting numbers into a list using $LOW_THREADS THREADS, COARSE-GRAINED algorithm and LO_ADD_HI_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $COARSE $LO_ADD_HI_CONT $MED_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS, FINE-GRAINED algorithm and LO_ADD_HI_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $FINE $LO_ADD_HI_CONT $MED_THREADS  

echo Inserting numbers into a list using $LOW_THREADS THREADS LAZY algorithm and LO_ADD_HOI_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $LAZY  $LO_ADD_HI_CONT $MED_THREADS 

echo ...........................................................................
echo MED THREADS TESTING DONE...................................................

echo ................................................................................................
echo ................................................................................................
echo ................................................................................................

echo HIGH THREADS.....................................................................................
echo HIGH ADD OPERATIONS LOW REMOVAL OPERATIONS
echo Inserting numbers into a list using $LOW_THREADS THREADS, COARSE-GRAINED algorithm and HI_ADD_LO_REM operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $COARSE $HI_ADD_LO_REM $HI_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS, FINE-GRAINED algorithm and HI_ADD_LO_REM operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $FINE $HI_ADD_LO_REM $HI_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS LAZY algorithm and HI_ADD_LO_REM operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $LAZY  $HI_ADD_LO_REM $HI_THREADS 
echo .............................................................................

echo HIGH ADD OPERATION LOW CONTAINS OPERATIONS
echo Inserting numbers into a list using $LOW_THREADS THREADS, COARSE-GRAINED algorithm and HI_ADD_LO_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $COARSE $HI_ADD_LO_CONT $HI_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS, FINE-GRAINED algorithm and HI_ADD_LO_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $FINE $HI_ADD_LO_CONT $HI_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS LAZY algorithm and HI_ADD_LO_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $LAZY  $HI_ADD_LO_CONT $HI_THREADS 

echo ...........................................................................

echo HIGH ADD OPERATION HIGH CONTAINS OPERATIONS
echo Inserting numbers into a list using $LOW_THREADS THREADS, COARSE-GRAINED algorithm and HI_ADD_LO_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $COARSE $HI_ADD_HI_CONT $HI_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS, FINE-GRAINED algorithm and HI_ADD_LO_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $FINE $HI_ADD_HI_CONT $HI_THREADS  

echo Inserting numbers into a list using $LOW_THREADS THREADS LAZY algorithm and HI_ADD_LO_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $LAZY  $HI_ADD_HI_CONT $HI_THREADS 


echo LO ADD OPERATION HIGH CONTAINS OPERATIONS
echo Inserting numbers into a list using $LOW_THREADS THREADS, COARSE-GRAINED algorithm and LO_ADD_HI_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $COARSE $LO_ADD_HI_CONT $HI_THREADS 

echo Inserting numbers into a list using $LOW_THREADS THREADS, FINE-GRAINED algorithm and LO_ADD_HI_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $FINE $LO_ADD_HI_CONT $HI_THREADS  

echo Inserting numbers into a list using $LOW_THREADS THREADS LAZY algorithm and LO_ADD_HOI_CONT operations
/usr/bin/time -f "$TIMEFORMAT" java ListDriver $LAZY  $LO_ADD_HI_CONT $HI_THREADS 
echo ...........................................................................
echo HIGH THREADS TESTING DONE...................................................