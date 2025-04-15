#!/bin/bash
sbt clean scalafmt test:scalafmt coverage test it/Test/scalafmt it/test coverageReport