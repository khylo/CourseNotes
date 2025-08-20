rem =======================================================
rem create the HR schema user
rem =======================================================
ALTER SESSION set CONTAINER=FREEPDB1;

CREATE USER hr IDENTIFIED BY pass;

GRANT CONNECT, RESOURCE to hr;
GRANT ALL privileges to hr;
GRANT CREATE SESSION TO hr;

ALTER SESSION SET CURRENT_SCHEMA=HR;
ALTER SESSION SET NLS_LANGUAGE=American;
ALTER SESSION SET NLS_TERRITORY=America;