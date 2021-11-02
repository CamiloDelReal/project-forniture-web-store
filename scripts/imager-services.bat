@echo off

echo [!] Checking docker
set "dockerErr=0"
call docker -v || set dockerErr=1
if "%dockerErr%" == "0" echo [+] Docker found && goto checkdockerid

echo [-] Maven executable (mvn) could not be found
echo [!] Please add mvn executable to your PATH variable
echo [-] Script cannot continue
goto end

:checkdockerid

if not "%1" == "" echo [!] Creating images for %1 && goto execute

echo [-] DockerID could not be found
echo [!] Please start the script passing your DockerID as arguments
echo [-] Script cannot continue
goto end

:execute

@rem Configuration Service
echo [!] Imaging Configuration Service
cd ../configuration-service/
set "dockerErr=0"
call docker build . --tag %1/configuration-service --force-rm=true || set dockerErr=1
if "%dockerErr%" == "0" echo [+] Configuration Service successfully imaged
if "%dockerErr%" == "1" echo [-] Error imaging Configuration Service. Script cannot continue && goto end

@rem Discovery Service
echo [!] Imaging Discovery Service
cd ../discovery-service/
set "dockerErr=0"
call docker build . --tag %1/discovery-service --force-rm=true || set dockerErr=1
if "%dockerErr%" == "0" echo [+] Discovery Service successfully imaged
if "%dockerErr%" == "1" echo [-] Error imaging Discovery Service. Script cannot continue && goto end

@rem Gateway Service
echo [!] Imaging Gateway Service
cd ../gateway-service/
set "dockerErr=0"
call docker build . --tag %1/gateway-service --force-rm=true || set dockerErr=1
if "%dockerErr%" == "0" echo [+] Gateway Service successfully imaged
if "%dockerErr%" == "1" echo [-] Error imaging Gateway Service. Script cannot continue && goto end

@rem User Service
echo [!] Imaging User Service
cd ../user-service/
set "dockerErr=0"
call docker build . --tag %1/user-service --force-rm=true || set dockerErr=1
if "%dockerErr%" == "0" echo [+] User Service successfully imaged
if "%dockerErr%" == "1" echo [-] Error imaging User Service. Script cannot continue && goto end

@rem Product Service
echo [!] Imaging Product Service
cd ../product-service/
set "dockerErr=0"
call docker build . --tag %1/product-service --force-rm=true || set dockerErr=1
if "%dockerErr%" == "0" echo [+] Product Service successfully imaged
if "%dockerErr%" == "1" echo [-] Error imaging Product Service. Script cannot continue && goto end

:end