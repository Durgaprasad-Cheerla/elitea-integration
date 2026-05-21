@echo off
REM ###############################################################################
REM ParaBank Test Automation - Execution Script (Windows)
REM Test Case: SCRUM-167 - TC001 User Registration
REM Description: Automated test execution script with multiple options
REM ###############################################################################

title ParaBank Test Automation - SCRUM-167

color 0A

:BANNER
echo.
echo ================================================================
echo      ParaBank Test Automation - SCRUM-167
echo      TC001: User Registration Test Execution
echo ================================================================
echo.

:CHECK_PREREQUISITES
echo [INFO] Checking prerequisites...
echo.

REM Check Java
java -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Java not found. Please install Java 11 or higher.
    pause
    exit /b 1
) else (
    echo [OK] Java found
    java -version 2>&1 | findstr /C:"version"
)

echo.

REM Check Maven
mvn -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Maven not found. Please install Maven 3.6 or higher.
    pause
    exit /b 1
) else (
    echo [OK] Maven found
    mvn -version 2>&1 | findstr /C:"Maven"
)

echo.
echo [OK] All prerequisites are met!
echo.

:MENU
echo.
echo ================================================================
echo                      TEST EXECUTION MENU
echo ================================================================
echo.
echo 1. Run SCRUM-167 test only (Chrome)
echo 2. Run all User Registration tests (Chrome)
echo 3. Run tests in Firefox
echo 4. Run tests in Edge
echo 5. Run tests in Headless mode
echo 6. Run High Priority tests
echo 7. Install dependencies only
echo 8. Open test reports
echo 9. Clean and rebuild
echo 0. Exit
echo.
set /p choice="Enter your choice [0-9]: "

if "%choice%"=="1" goto RUN_SCRUM167
if "%choice%"=="2" goto RUN_USER_REG
if "%choice%"=="3" goto RUN_FIREFOX
if "%choice%"=="4" goto RUN_EDGE
if "%choice%"=="5" goto RUN_HEADLESS
if "%choice%"=="6" goto RUN_HIGH_PRIORITY
if "%choice%"=="7" goto INSTALL_DEPS
if "%choice%"=="8" goto OPEN_REPORTS
if "%choice%"=="9" goto CLEAN_REBUILD
if "%choice%"=="0" goto EXIT
goto INVALID_CHOICE

:RUN_SCRUM167
echo.
echo [INFO] Running SCRUM-167 test in Chrome...
echo.
call mvn clean test -Dcucumber.filter.tags="@SCRUM-167" -Dbrowser=chrome
if errorlevel 1 (
    echo [ERROR] Tests failed!
) else (
    echo [OK] Tests passed successfully!
)
call :OPEN_REPORTS
goto CONTINUE

:RUN_USER_REG
echo.
echo [INFO] Running all User Registration tests in Chrome...
echo.
call mvn clean test -Dcucumber.filter.tags="@UserRegistration" -Dbrowser=chrome
if errorlevel 1 (
    echo [ERROR] Tests failed!
) else (
    echo [OK] Tests passed successfully!
)
call :OPEN_REPORTS
goto CONTINUE

:RUN_FIREFOX
echo.
echo [INFO] Running SCRUM-167 test in Firefox...
echo.
call mvn clean test -Dcucumber.filter.tags="@SCRUM-167" -Dbrowser=firefox
if errorlevel 1 (
    echo [ERROR] Tests failed!
) else (
    echo [OK] Tests passed successfully!
)
call :OPEN_REPORTS
goto CONTINUE

:RUN_EDGE
echo.
echo [INFO] Running SCRUM-167 test in Edge...
echo.
call mvn clean test -Dcucumber.filter.tags="@SCRUM-167" -Dbrowser=edge
if errorlevel 1 (
    echo [ERROR] Tests failed!
) else (
    echo [OK] Tests passed successfully!
)
call :OPEN_REPORTS
goto CONTINUE

:RUN_HEADLESS
echo.
echo [INFO] Running tests in Headless mode...
echo.
call mvn clean test -Dcucumber.filter.tags="@SCRUM-167" -Dbrowser=chrome -Dbrowser.headless=true
if errorlevel 1 (
    echo [ERROR] Tests failed!
) else (
    echo [OK] Tests passed successfully!
)
call :OPEN_REPORTS
goto CONTINUE

:RUN_HIGH_PRIORITY
echo.
echo [INFO] Running High Priority tests...
echo.
call mvn clean test -Dcucumber.filter.tags="@HighPriority" -Dbrowser=chrome
if errorlevel 1 (
    echo [ERROR] Tests failed!
) else (
    echo [OK] Tests passed successfully!
)
call :OPEN_REPORTS
goto CONTINUE

:INSTALL_DEPS
echo.
echo [INFO] Installing dependencies...
echo.
call mvn clean install -DskipTests
if errorlevel 1 (
    echo [ERROR] Failed to install dependencies!
    pause
    exit /b 1
) else (
    echo [OK] Dependencies installed successfully!
)
goto CONTINUE

:OPEN_REPORTS
set REPORT_PATH=test-output\cucumber-reports\cucumber-report.html
if exist "%REPORT_PATH%" (
    echo.
    echo [INFO] Opening test report...
    start "" "%REPORT_PATH%"
    echo [OK] Report opened: %REPORT_PATH%
) else (
    echo [WARNING] Report not found: %REPORT_PATH%
    echo [INFO] Reports will be generated after test execution.
)
goto :EOF

:CLEAN_REBUILD
echo.
echo [INFO] Cleaning and rebuilding project...
echo.
call mvn clean install -DskipTests
if errorlevel 1 (
    echo [ERROR] Failed to rebuild project!
) else (
    echo [OK] Project rebuilt successfully!
)
goto CONTINUE

:INVALID_CHOICE
echo.
echo [ERROR] Invalid choice. Please try again.
goto CONTINUE

:CONTINUE
echo.
pause
goto MENU

:EXIT
echo.
echo [INFO] Thank you for using ParaBank Test Automation!
echo.
timeout /t 2 >nul
exit /b 0

REM ###############################################################################
REM Additional Commands (can be run directly from command line)
REM
REM Examples:
REM   run-tests.bat 1              - Run SCRUM-167 test
REM   run-tests.bat 2              - Run all User Registration tests
REM   run-tests.bat 7              - Install dependencies
REM   run-tests.bat 8              - Open reports
REM
REM Advanced Maven Commands:
REM   mvn clean test                                        - Run all tests
REM   mvn clean test -Dcucumber.filter.tags="@SCRUM-167"   - Run specific tag
REM   mvn clean test -Dbrowser=firefox                      - Run with Firefox
REM   mvn clean test -Dbrowser.headless=true                - Run headless
REM
REM ###############################################################################
