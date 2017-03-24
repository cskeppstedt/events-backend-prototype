FROM java:openjdk-8-jre

ENV LEIN_VERSION=2.7.1 \
    RIEMANN_TAG=8bf6b64895b104a5bc0c46e28621c5afb5e2ab3a

# Install git
RUN apt-get update && \
    apt-get install -y git && \
    apt-get clean

# Install Leiningen
RUN wget -q https://raw.githubusercontent.com/technomancy/leiningen/$LEIN_VERSION/bin/lein -O /usr/bin/lein && \
    chmod a+x /usr/bin/lein

RUN useradd -ms /bin/bash riemann
USER riemann
WORKDIR /home/riemann
RUN lein

# Download and build riemann from source
RUN git clone https://github.com/riemann/riemann.git ./riemann-source && \
    cd ./riemann-source && \
    git reset --hard $RIEMANN_TAG && \
    lein install

# Include and run riemann.conf
ADD riemann.config /home/riemann/riemann.config
RUN cat /home/riemann/riemann.config

CMD cd /home/riemann/riemann-source && lein run -- /home/riemann/riemann.config
