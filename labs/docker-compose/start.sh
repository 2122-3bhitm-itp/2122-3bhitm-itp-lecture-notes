#!/usr/bin/env bash
docker run --rm --name my-database -e POSTGRES_PASSWORD=password -p 5432:5432 my-database

