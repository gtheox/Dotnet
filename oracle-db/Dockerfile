FROM gvenzl/oracle-xe:latest

USER root

RUN useradd -ms /bin/bash oracleuser && \
    mkdir -p /opt/oracle/app && \
    chown -R oracleuser:oracleuser /opt/oracle/app

USER oracleuser
WORKDIR /opt/oracle/app

EXPOSE 1521

ENV ORACLE_SID=ORCL
