#!/bin/bash
# generate a sql script
# Author: Jomkie



for ((i = 0; i < 30; i ++));do
	echo "($i, 'Jomkie$i', $i, 'test$i@baomidou.com', '未知' NOW(), NOW()), " >> ./temp.txt
done
