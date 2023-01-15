
# Jewelry eCommerce store

The project is an eCommerce Jewelry website developed to attract more customers and make them want to buy a jewel
Developed a REST API of HTTP Servlet-based application using JDBC, PostgreSQL, and Java 16 using the JSP flexibility to make the UI/UX easy to interact with and intuitive

## Run Locally

Use Git or checkout with SVN using the web URL.

https://github.com/kyrychenkoartem/online-shop.git


## Install dependencies

commons-lang3-3.12.0

jakarta.servlet.jsp.jstl-2.0.0

jakarta.servlet.jsp.jstl-api-2.0.0

lombok

postgresql-42.5.0

servlet-api

slf4j-api-2.0.6

## Before start

Add Web module and create Web exploded artifact 

File -> Project Structure(Ctrl+Alt+Shift+S) -> Modules -> Add Web module -> Appply Changes

File -> Project Structure(Ctrl+Alt+Shift+S) -> Artifacts -> Add Web Application: Exploded From Modules(choose the module from previous step) -> Appply Changes

## Start
Before running the application you need to change run configuration

Run -> Edit Configurations -> Add New Configuration(Choose Tomcat Server - Local) -> Before Launch(Add -> Build Artifacts -> Web exploded artifact) -> Change Tomcat Server Settings(if need(default url: http://localhost:8080)) -> Appply Changes

Run the application (Shift + F10)

