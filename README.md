# Simple Weather Api

* **Name**      : weather-api
* **Team**      : Example
* **Framework** : Spring Boot 3
* **Java**    : 21

## Overview
A simple weather api to return weather details by city name

## API Definition
doc/weather_v1.yaml


## How To
* start from local
  * Run WeatherApplication
  * curl "http://localhost:9595/api/v1/weather?city=Auckland" 
* deploy to cloud


## Configuration
| name | type   | description |
| ---- |--------| ----------- |
| PORT | number | servier start port, default is 9595 |
| PROVIDER_BASE | string | the weather service provider base url | 

