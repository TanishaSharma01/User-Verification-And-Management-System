
# User Verification And Management System
A system to create and verify users based on external API calls for nationality and gender, validating data and managing user records with custom validation and efficient sorting.

## Overview
This project is a user management system that verifies users based on their nationality and gender using external APIs. Users are marked as VERIFIED or TO_BE_VERIFIED based on the validation results. The system also provides APIs for user creation and retrieval, with support for sorting, pagination, and input validation.

## Prerequisites

• Java Development Kit (JDK) installed (version 17 or higher)

• Maven build tool

• MySQL Workbench (version 8.0 or higher)

## Features
• Fetch random user data from external API (randomuser.me).

• Validate user nationality and gender using external APIs (Nationalize.io, Genderize.io).

• Mark users as VERIFIED or TO_BE_VERIFIED based on nationality and gender validation.

• Support for user creation and retrieval with sorting by name or age, and pagination.

• Custom validation using Singleton and Factory design patterns.

• Extensive error handling and custom error messages.

## Technology Stack
Backend: Java, Spring Boot

Database: MySQL

API Calls: WebClient (for external API integration)

Concurrency: Executor Framework (for parallel API calls)

Design Patterns: Singleton, Factory, Strategy
Testing: JUnit, Mockito
