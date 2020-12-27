#!/bin/bash
#PBS -N l1
#PBS -o l1.out
#PBS -e l1.err
#PBS -l nodes=2
#PBS -q arcedu
date
python l1.py
date
