#!/bin/bash

# Define source and destination paths
source_dir="target/"
destination_dir="build/"
file_prefix="text-replacer"

# Copy the file to the destination directory with the desired name
cp "${source_dir}text-replacer-1.0-SNAPSHOT-exec.jar" "${destination_dir}${file_prefix}.jar"


chmod a+x build/run.sh
chmod a+x "${destination_dir}${file_prefix}.jar"
