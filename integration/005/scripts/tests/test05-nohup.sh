

cd /home/pyrothorn/

python testing/test_firethorn_logged_json.py > logs/logfile.txt

# Run test as background task (Closing terminal will not cancel run)
# nohup python testing/test_firethorn_logged_json.py > logfile.txt 2>&1 </dev/null &
