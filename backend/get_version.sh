#!/bin/bash
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
mv target/clickbarber-$VERSION.jar target/clickbarber.jar