#!/bin/bash
# generate a sql script
# Author: Jomkie



for ((i = 0; i < 30; i ++));do
	echo "($i, 'Jomkie$i', $i, 'test$i@baomidou.com'), " >> ./temp.txt
done
