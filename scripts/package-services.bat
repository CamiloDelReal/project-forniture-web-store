@echo off

echo [!] Checking maven
set "mvnErr=0"
call mvn -v || set mvnErr=1
if "%mvnErr%" == "0" echo [+] Maven found && goto execute

echo [-] Maven executable (mvn) could not be found
echo [!] Please add mvn executable to your PATH variable
echo [-] Script cannot continue
goto end

:execute

@rem Configuration Service
echo [!] Packaging Configuration Service
cd ../configuration-service/
set "mvnErr=0"
call mvn clean package install "-Dmaven.test.skip=true" || set mvnErr=1
if "%mvnErr%" == "0" echo [+] Configuration Service successfully packaged
if "%mvnErr%" == "1" echo [-] Error packaging Configuration Service. Script cannot continue && goto end

@rem Discovery Service
echo [!] Packaging Discovery Service
cd ../discovery-service/
set "mvnErr=0"
call mvn clean package install "-Dmaven.test.skip=true" || set mvnErr=1
if "%mvnErr%" == "0" echo [+] Discovery Service successfully packaged
if "%mvnErr%" == "1" echo [-] Error packaging Discovery Service. Script cannot continue && goto end

@rem Gateway Service
echo [!] Packaging Gateway Service
cd ../gateway-service/
set "mvnErr=0"
call mvn clean package install "-Dmaven.test.skip=true" || set mvnErr=1
if "%mvnErr%" == "0" echo [+] Gateway Service successfully packaged
if "%mvnErr%" == "1" echo [-] Error packaging Gateway Service. Script cannot continue && goto end

@rem User Service
echo [!] Packaging User Service
cd ../user-service/
set "mvnErr=0"
call mvn clean package install "-Dmaven.test.skip=true" || set mvnErr=1
if "%mvnErr%" == "0" echo [+] User Service successfully packaged
if "%mvnErr%" == "1" echo [-] Error packaging User Service. Script cannot continue && goto end

@rem Product Service
echo [!] Packaging Product Service
cd ../product-service/
set "mvnErr=0"
call mvn clean package install "-Dmaven.test.skip=true" || set mvnErr=1
if "%mvnErr%" == "0" echo [+] Product Service successfully packaged
if "%mvnErr%" == "1" echo [-] Error packaging Product Service. Script cannot continue && goto end

:end
