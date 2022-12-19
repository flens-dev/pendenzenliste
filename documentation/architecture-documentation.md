# Pendenzenliste

**About arc42**

arc42, the template for documentation of software and system
architecture.

Template Version 8.1 EN. (based upon AsciiDoc version), May 2022

Created, maintained and © by Dr. Peter Hruschka, Dr. Gernot Starke and
contributors. See <https://arc42.org>.

# Introduction and Goals

Pendenzenliste is a highly over-engineered application that can be used to manage todos.
The main goal of the project is to exercise designing a non-trivial application in a CLEAN-Architecture.

The users of the application define for themselves how they want to run the application.
This may mean that the app is run in a web application, a CLI or an android app.
If the user desires it the user should be able to synchronize the data between multiple of his personal devices.

## Requirements Overview

| ID                                                           | Requirement               | Description                                                                        | Implemented |
|--------------------------------------------------------------|---------------------------|------------------------------------------------------------------------------------|-------------|
| M-1                                                          | Create todo               | Users should be able to create their todos                                         | Yes         |
| M-2                                                          | Delete todo               | Users should be able to delete their todos                                         | Yes         |
| M-3                                                          | Complete todo             | Users should be able to complete their todos                                       | Yes         |
| M-4                                                          | Reset todo                | Users should be able to reset a previously completed todo                          | Yes         |
| M-5                                                          | List todos                | Users should be able to list their todos                                           | Yes         |
| M-6                                                          | Fetch todo                | Users should be able to fetch a specific todo                                      | Yes         |
| M-7                                                          | Update todo               | Users should be able to update their open todos                                    | Yes         |
| M-8                                                          | Storage gateway           | The application should have some kind of storage gateway to store the todos        | Yes         |
| O-1                                                          | In-memory storage gateway | The application should support an in-memory storage gateway                        | Yes         |
| [O-2](https://github.com/flens-dev/pendenzenliste/issues/1)  | File storage gateway      | The application should support a file storage gateway to persistently store todos  | Yes         |
| [O-3](https://github.com/flens-dev/pendenzenliste/issues/2)  | Redis storage gateway     | The application should support a redis storage gateway to persistently store todos | No          |
| [O-4](https://github.com/flens-dev/pendenzenliste/issues/10) | REST storage gateway      | The application should support a REST storage gateway to persistently store todos  | No          |
| M-9                                                          | App                       | The pendenzenliste should be bundled into an application                           | Yes         |
| O-5                                                          | javafx app                | The pendenzenliste should be bundled into a javafx application                     | Yes         |
| [O-6](https://github.com/flens-dev/pendenzenliste/issues/7)  | vaadin app                | The pendenzenliste should be bundled into a vaadin application                     | Yes         |
| [O-7](https://github.com/flens-dev/pendenzenliste/issues/12) | cli app                   | The pendenzenliste should be bundled into a command line interface application     | Yes         |
| [O-8](https://github.com/flens-dev/pendenzenliste/issues/13) | i18n                      | The pendenzenliste should be translated into german and english                    | No          |
| [O-9](https://github.com/flens-dev/pendenzenliste/issues/9)  | achievements              | The user should be able to earn achievements by interacting with the application   | No          |

Legend:

```
M = Must have requirement
O = Optional requirement
```

## Quality Goals

| ID  | Priority | Quality goal  | Description                                                                                                                 |
|-----|----------|---------------|-----------------------------------------------------------------------------------------------------------------------------|
| Q-1 | 1        | Extendability | The application should be easily extended by adding new frontend applications or gateways to support different technologies |
| Q-2 | 2        | Learnability  | The application should be self explanatory to the end users                                                                 | 

## Stakeholders

| Role/Name | Goal                                                               | Expectations |
|-----------|--------------------------------------------------------------------|--------------|
| User      | Wants to configure the app to his needs and it to manage his todos |              |

# Architecture Constraints

| ID   | Description                                                     | 
|------|-----------------------------------------------------------------|
| AC-1 | The application should follow the CLEAN architecture principles |

# System Scope and Context

## Business Context

| Neighbour              | Description                                                              |
|------------------------|--------------------------------------------------------------------------|
| user                   | Manages his todos via the applications                                   |
| gateway implementation | Is accessed by the application to store and retrieve the users todo data |

**\<Diagram or Table\>**

**\<optionally: Explanation of external domain interfaces\>**

## Technical Context

| Component          | Description                                                                                           |
|--------------------|-------------------------------------------------------------------------------------------------------|
| app-cli            | Provides the end user access to the pendenzenliste through a cli                                      |
| app-javafx         | Provides the end user access to the pendenzenliste through a desktop javafx application               |
| app-vaadin         | Provides the end user access to the pendenzenliste through a web based vaadin application             | 
| boundary           | Defines the input and output boundaries that are used to provide access to the applications use cases | 
| domain             | Defines the core domain logic of the pendenzenliste                                                   |
| gateway            | Defines the public API of the gateways that are used to store the todos                               |
| gateway-filesystem | An implementation of the gateway API that stores the todos in a filesystem                            |
| gateway-inmemory   | An implementation of the gateway API that stores the todos in an in-memory storage                    |
| gateway-redis      | An implementation of the gateway API that stores the todos in a redis instance                        |
| usecases           | An implementation of the apps input boundaries that represent the actual use cases of the application |

**\<Diagram or Table\>**

**\<optionally: Explanation of technical interfaces\>**

**\<Mapping Input/Output to Channels\>**

# Solution Strategy {#section-solution-strategy}

# Building Block View {#section-building-block-view}

## Whitebox Overall System {#_whitebox_overall_system}

***\<Overview Diagram\>***

Motivation

:   *\<text explanation\>*

Contained Building Blocks

:   *\<Description of contained building block (black boxes)\>*

Important Interfaces

:   *\<Description of important interfaces\>*

### \<Name black box 1\> {#__name_black_box_1}

*\<Purpose/Responsibility\>*

*\<Interface(s)\>*

*\<(Optional) Quality/Performance Characteristics\>*

*\<(Optional) Directory/File Location\>*

*\<(Optional) Fulfilled Requirements\>*

*\<(optional) Open Issues/Problems/Risks\>*

### \<Name black box 2\> {#__name_black_box_2}

*\<black box template\>*

### \<Name black box n\> {#__name_black_box_n}

*\<black box template\>*

### \<Name interface 1\> {#__name_interface_1}

...

### \<Name interface m\> {#__name_interface_m}

## Level 2 {#_level_2}

### White Box *\<building block 1\>* {#_white_box_emphasis_building_block_1_emphasis}

*\<white box template\>*

### White Box *\<building block 2\>* {#_white_box_emphasis_building_block_2_emphasis}

*\<white box template\>*

...

### White Box *\<building block m\>* {#_white_box_emphasis_building_block_m_emphasis}

*\<white box template\>*

## Level 3 {#_level_3}

### White Box \<\_building block x.1\_\> {#_white_box_building_block_x_1}

*\<white box template\>*

### White Box \<\_building block x.2\_\> {#_white_box_building_block_x_2}

*\<white box template\>*

### White Box \<\_building block y.1\_\> {#_white_box_building_block_y_1}

*\<white box template\>*

# Runtime View {#section-runtime-view}

## \<Runtime Scenario 1\> {#__runtime_scenario_1}

- *\<insert runtime diagram or textual description of the scenario\>*

- *\<insert description of the notable aspects of the interactions
  between the building block instances depicted in this diagram.\>*

## \<Runtime Scenario 2\> {#__runtime_scenario_2}

## ... {#_}

## \<Runtime Scenario n\> {#__runtime_scenario_n}

# Deployment View {#section-deployment-view}

## Infrastructure Level 1 {#_infrastructure_level_1}

***\<Overview Diagram\>***

Motivation

:   *\<explanation in text form\>*

Quality and/or Performance Features

:   *\<explanation in text form\>*

Mapping of Building Blocks to Infrastructure

:   *\<description of the mapping\>*

## Infrastructure Level 2 {#_infrastructure_level_2}

### *\<Infrastructure Element 1\>* {#__emphasis_infrastructure_element_1_emphasis}

*\<diagram + explanation\>*

### *\<Infrastructure Element 2\>* {#__emphasis_infrastructure_element_2_emphasis}

*\<diagram + explanation\>*

...

### *\<Infrastructure Element n\>* {#__emphasis_infrastructure_element_n_emphasis}

*\<diagram + explanation\>*

# Cross-cutting Concepts

## Controller

A `Controller` creates a `Request` and invokes the appropriate `InputBoundary` with it.

The `Controller` is not accessed directly, but rather listens to events from the application and acts as it seems fit to
those events.

## Entity

An `Entity` is some kind of persistent object that belongs to the domain.

It may contain other `ValueObject`s.

Each `Entity` is accessed through an appropriate `Gateway`.

## Event

An `Event` describes some kind of meaningful state change within the application or domain that happened in the past.

## Gateway

A gateway provides access to some kind of external system such as a database or a REST API.

## InputBoundary

An `InputBoundary` defines a public interface for objects that handle the `UseCase` specific inputs of the application.
It receives a `Request` and an `OutputBoundary` and produces a `Response`.
The `Response` will then be used to update the `OutputBoundary`.

## OutputBoundary

An `OutputBoundary` defines a public interface for objects that handle the responses of the applications `UseCase`s.

## Presenter

A `Presenter` is an application specific implementation of an `OutputBoundary`.
The `Presenter` modifies the `ViewModel`, which is bound to a `View`, so the `Presenter` indirectly modifies the data
displayed by the `View`.

## UseCase

A `UseCase` implements an `InputBoundary` and encapsulates the interactions required to fulfill some kind of goal within
the application.

## Request

A `Request` encapsulates the data used to invoke an `InputBoundary` in a technology-agnostic format.
The `Request` may only contain native datatypes, such as strings, integers, etc., or `RequestModel`s.

## RequestModel

A `RequestModel` encapsulates complex data structures that may be a member of a `Request`, but cannot be represented in
a primitive datatype.

An example for this would be a list of key value pairs that need to be passed to the `InputBoundary`.

## Response

A `Response` encapsulates the data used to invoke an `OutputBoundary` in a technology-agnostic format.

The `Response` may only contain native datatypes, such as strings, integers, etc., or `ResponseModel`s.

## ResponseModel

A `ResponseModel` is a technology-agnostic model that may be contained in a `Response`.

The `ResponseModel` may represent some kind of `Entity` without exposing the actual data types of the domain module.

## ValueObject

A `ValueObject` represents some kind of domain-specific data type.

## View

A `View` provides a user the means to look at and enter new data into the application.

It binds to a `ViewModel`, which stores all the data used to display the data for the end user.

## ViewModel

A `ViewModel` is an application-specific model of the data displayed by a `View`.
The `ViewModel` provides means for a `View` to bind to its properties.
Both the `Presenter` and `View` may update the properties to store the displayed data.

# Architecture Decisions {#section-design-decisions}

# Quality Requirements {#section-quality-scenarios}

## Quality Tree {#_quality_tree}

## Quality Scenarios {#_quality_scenarios}

# Risks and Technical Debts {#section-technical-risks}

# Glossary {#section-glossary}

+-----------------------+-----------------------------------------------+
| Term | Definition |
+=======================+===============================================+
| *\<Term-1\>*          | *\<definition-1\>*                            |
+-----------------------+-----------------------------------------------+
| *\<Term-2\>*          | *\<definition-2\>*                            |
+-----------------------+-----------------------------------------------+