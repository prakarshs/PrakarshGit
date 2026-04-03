@echo off
setlocal ENABLEDELAYEDEXPANSION

echo ===============================================
echo Installing prakarshGit (vit)
echo ===============================================

SET VIT_INSTALL_DIR=%USERPROFILE%\VitGit

echo.
echo Installing to: %VIT_INSTALL_DIR%

REM Check Java
where java >nul 2>nul
IF %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java is not installed or not in PATH.
    pause
    exit /b 1
)

REM Create directory
if not exist "%VIT_INSTALL_DIR%" (
    mkdir "%VIT_INSTALL_DIR%"
)

REM Copy files
copy /Y target\vit.jar "%VIT_INSTALL_DIR%\vit.jar" >nul
copy /Y scripts\vit.cmd "%VIT_INSTALL_DIR%\vit.cmd"

REM Read existing PATH safely
for /f "tokens=2*" %%A in ('reg query HKCU\Environment /v PATH 2^>nul') do (
    set CURRENT_PATH=%%B
)

REM Check if already exists
echo !CURRENT_PATH! | find /I "%VIT_INSTALL_DIR%" >nul
IF !ERRORLEVEL! EQU 0 (
    echo Already in PATH.
) ELSE (
    echo Adding to PATH...

    set NEW_PATH=!CURRENT_PATH!;%VIT_INSTALL_DIR%
    reg add HKCU\Environment /v PATH /t REG_EXPAND_SZ /d "!NEW_PATH!" /f >nul

    REM Update current session
    set PATH=!NEW_PATH!
)

echo.
echo ✅ Installation complete!
echo Restart terminal and run:
echo     vit --help
echo.
pause