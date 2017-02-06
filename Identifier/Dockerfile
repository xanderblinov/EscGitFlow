FROM python:3.6
MAINTAINER Sergey Demurin <kakty3.mail@gmail.com>

COPY requirements.txt /opt/esc-identifier/
RUN pip3 install -r /opt/esc-identifier/requirements.txt

COPY ./src /opt/esc-identifier

WORKDIR /opt/esc-identifier
CMD python3 main.py