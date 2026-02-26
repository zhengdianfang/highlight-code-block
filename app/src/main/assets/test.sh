#!/bin/bash

# Simple shell script demo

name="Highlightr"
echo "Hello, $name!"

if [ -d "/tmp" ]; then
    echo "Temp directory exists"
fi

function greet() {
    echo "Greetings from function!"
}

greet

for i in {1..5}; do
    echo "Count: $i"
done
