#!/bin/sh

echo "Job running on WN: "`hostname`
echo "------------"
chmod 777 main.py && python main.py
