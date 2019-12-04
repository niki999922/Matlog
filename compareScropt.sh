#!/bin/zsh

echo "lala"
if [ "$(wc -l < file_me.txt)" -ne "$(wc -l < file_other.txt)" ]; then echo 'Warning: No Match!' >>'/Users/nikita/Matlog/file_res.txt'; fi
#if [ "$(wc -l < file_me.txt)" -ne "$(wc -l < file_other.txt)" ]; then echo 'Warning: No Match!' >>'/Users/nikita/Matlog/file_res.txt'; else echo 'Match!' >>'/Users/nikita/Matlog/file_res.txt'; fi
#if [ "$(wc -l < file_me.txt)" -eq "$(wc -l < file_other.txt)" ]; then echo 'Match!'; else echo 'Warning: No Match!'; fi