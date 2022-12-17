# Pendenzenliste

**About arc42**

arc42, the template for documentation of software and system
architecture.

Template Version 8.1 EN. (based upon AsciiDoc version), May 2022

Created, maintained and © by Dr. Peter Hruschka, Dr. Gernot Starke and
contributors. See <https://arc42.org>.

# Introduction and Goals {#section-introduction-and-goals}

## Requirements Overview {#_requirements_overview}

## Quality Goals {#_quality_goals}

## Stakeholders {#_stakeholders}

+-------------+---------------------------+---------------------------+
| Role/Name | Contact | Expectations |
+=============+===========================+===========================+
| * | *\<Contact-1\>*           | *\<Expectation-1\>*       |
| \<Role-1\>* | | |
+-------------+---------------------------+---------------------------+
| * | *\<Contact-2\>*           | *\<Expectation-2\>*       |
| \<Role-2\>* | | |
+-------------+---------------------------+---------------------------+

# Architecture Constraints {#section-architecture-constraints}

# System Scope and Context {#section-system-scope-and-context}

## Business Context {#_business_context}

**\<Diagram or Table\>**

**\<optionally: Explanation of external domain interfaces\>**

## Technical Context {#_technical_context}

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