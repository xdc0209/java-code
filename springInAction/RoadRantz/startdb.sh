#!/bin/sh
hsqlPath=/Users/wallsc/db/roadrantz/roadrantz
hsqlName=roadrantz
java -cp $SPRING_HOME/lib/hsqldb/hsqldb.jar org.hsqldb.Server -database.0 $hsqlPath -dbname.0 $hsqlName &
