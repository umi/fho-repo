FROM mysql/mysql-server:8.0

ENV MYSQL_DATABASE=spring \
  MYSQL_USER=spring \
  MYSQL_PASSWORD=secret \
  MYSQL_ROOT_PASSWORD=secret \
  TZ=Asia/Tokyo

RUN microdnf update -y \
    && microdnf install -y glibc-locale-source \
    && localedef -i ja_JP -c -f UTF-8 -A /usr/share/locale/locale.alias ja_JP.UTF-8

ENV LANG ja_JP.UTF-8
ENV LC_ALL ja_JP.UTF-8

COPY ./my.cnf /etc/my.cnf
RUN chmod 644 /etc/my.cnf
