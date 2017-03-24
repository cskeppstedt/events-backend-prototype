# events-backend-prototype

Prototype setup of an event processing system. Using a Kafka topic as the
source, streaming events through Riemann to Graphite. Includes a sample
producer that generates events.

## Usage

### First run

The Kafka topic needs to be created:

    make topic

### Consecutive runs

The Kafka topic (and data) is preserved between runs, so you only need to run:

    make up

This will start the containers in interactive mode which exposes the console
output of the containers.

### Recompile after changing source code

`riemann` and `producer` are built from source, so any changes to their source
files requires a new build. This is done automatically as part of `make up`.
