# Feelings

This app can detect a sentiment by analyzing a text.
Thanks to the [Open Service Broker for Azure](https://github.com/Azure/open-service-broker-azure),
this app is able to connect to the
[Text Analytics API](https://azure.microsoft.com/en-us/services/cognitive-services/text-analytics/)
seemlessly.
The endpoint is managed by the Azure service broker.

## Prerequisites

You need to run Cloud Foundry on Azure.
An Open Service Broker instance should be running.

If you're using Pivotal Cloud Foundry, you can use
[this guide](https://gist.github.com/alexandreroman/1ae63509067317fc07a0263d5fbbd903)
to setup a service broker for Azure on your platform.

## How to use it?

Create a `Text Analytics` service:
```shell
$ cf create-service azure-text-analytics free textAnalytics -c '{ "location": "northeurope", "resourceGroup": "cfdemo-services" }'
```

Wait for the service to be provisioned by the service broker.
```shell
$ cf service textAnalytics
Showing info of service textAnalytics in org demos / space dev as foo@bar.io...

name:            textAnalytics
service:         azure-text-analytics
tags:
plan:            free
description:     Azure Text Analytics (Experimental)
documentation:
dashboard:

Showing status of last operation from service textAnalytics...

status:    create succeeded
message:
started:   2018-10-26T21:44:57Z
updated:   2018-10-26T21:47:00Z

There are no bound apps for this service.
```

Compile this project using Maven, and deploy it to Cloud Foundry:
```shell
$ ./mvnw clean package
$ cf push
```

You're good to go!

<img src="https://imgur.com/download/kPcNjUI"/>

## How it works?

This app is built on the following technologies:
 - [Spring Boot](https://spring.io/projects/spring-boot) is providing a very effective framework for developping modern apps
 - [Spring Cloud Connectors](https://cloud.spring.io/spring-cloud-connectors/) makes integration with Cloud Foundry easy:
 this app extends SCC in order to connect to Azure Text Analytics API from Cloud Foundry
 - [Open Service Broker for Azure](https://github.com/Azure/open-service-broker-azure) brings Azure services to
 Cloud Foundry apps: these services are fully managed by Azure
 - Last but not least, this app is written using [Kotlin](https://kotlinlang.org/), a modern language running on the JVM

## Contribute

Contributions are always welcome!

Feel free to open issues & send PR.

## License

Copyright &copy; 2018 Pivotal Software, Inc.

This project is licensed under the [Apache Software License version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
