setlocal enabledelayedexpansion
for /f "tokens=1" %%a in ('jps ^| findstr yasso.jar') do taskkill /f /pid %%a
echo Stop Successful !!