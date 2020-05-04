# Changes Replication Between GridGain Clusters

The demo shows how to configure Data Center Replication between GridGain clusters.

## Prerequisites

JDK 1.8 or later: https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html.
Docker (demo was verified on Docker version 19.03.5, build 633a0ea): https://www.docker.com/get-started.

## Build the Demo

Clone or download this repository and build it by running `./gradlew clean assembly` from the root of the project.

## Start GridGain Clusters in Docker

Change directory to `$projectRoot/build/assembly`. After that execute `docker-compose -f config/dc1/dr-compose.yaml up -d`
and `docker-compose -f config/dc2/dr-compose.yaml up -d` to start both clusters.

## Start Demo Application

Once both clusters are started, you will need to start two demo application: one for Data Center 1 and another for 
Data Center 2. You could start demo applications by executing following commands from `assembly` directory:
* `java -jar -Dserver.port=8081  -Dignite.address=127.0.0.1:10801 dr-demo-app.jar &` - to start application for DC 1.
* `java -jar -Dserver.port=8082  -Dignite.address=127.0.0.1:10802 dr-demo-app.jar &` - to start application for DC 2.

## Verify Replication is Started

Before starting to work with application you need to verify status of the replication via `control.sh`. To get access 
to the `control.sh` utility you need to go inside the docker container. It could be achieved by executing
`docker-compose -f config/dc1/dr-compose.yaml exec -u 0 data-node /bin/sh` from the `assembly` directory. After that
 execute `bin/control.sh --dr state --verbose` command and verify an output. If the output doesn't contain
`Cache cart is stopped with reason "NO_SND_HUBS"` then replication is start working automatically. Otherwise you have
to start replication manually by executing command `bin/control.sh --dr cache cart --action start --yes`.

## Check That Changes Were Replicated

Open URLs `http://localhost:8081` and `http://localhost:8082` in the browser in different tabs. Verify both carts are 
empty. After that add some items to the cart from application 1 (it listen the port 8081). Then switch to application 2
(it listen the port 8082) and refresh the page. Now both cart should be the same.

## Links

More documentation is available here: https://www.gridgain.com/docs/latest/administrators-guide/data-center-replication/configuring-replication
