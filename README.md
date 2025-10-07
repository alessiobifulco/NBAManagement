# NBA GM Management System: A Database Design Project

**Project for the Basi di Dati (Database) course**, designed to model and manage the core business logic of an NBA General Manager, utilizing **MySQL** and a supporting **Java application**.

---

## Core Concept and Business Logic

This project focuses on the **conceptual**, **logical**, and **physical design** of a relational database to serve as a comprehensive support system for an NBA General Manager (GM).  
The primary goal is to facilitate strategic decision-making by providing structured, integrated, and historically accurate data access.

The platform manages the dynamic elements of an NBA franchise, including:

- **Personnel**: Players (categorized by role: Superstar, Role Player, etc.) and Staff (coaches and scouts).  
- **Contracts**: The central entity linking players to teams and driving all trade and signing operations.  
- **Operations**: Detailed history of games, training sessions, and market transactions (trades and signings).  

---

## Database Design Architecture

The project strictly adheres to established database design methodologies, moving from conceptual modeling to physical implementation.

### Conceptual Design (E-R Model)
Focused on defining the primary entities and relationships.  
Key features include:
- Use of hierarchies for **Staff** and categorization for **Players**.  
- Centrality of the **Contratto** entity.

### Logical Design & Refinement
Involved quantitative analysis to justify design choices.

### Redundancy Analysis
Two major redundancies were identified and resolved to ensure data integrity over minimal performance gains.  
The decision was made to **centralize player-team membership through the `Contratto` entity**.

### Access Table Calculation
Estimation of the **read (R)** and **write (W)** costs for critical operations to validate the final schema design.

### Physical Implementation
The final relational schema will be implemented using **MySQL**.

---

## Key Design Decisions & Challenges

The project was structured around resolving common design challenges to ensure a coherent and normalized database.

- **Trade-off Resolution**: Prioritization of data integrity over marginal performance gains, especially regarding player-team membership (removing the redundant direct `RIENTRA` relationship).  
- **Simplified Stadium Logic**: Elimination of complex stadium-team relationships in favor of a simpler, more logical `atHome` boolean attribute in the `Partita` entity.  
- **Hierarchical Modeling**: Strategic use of hierarchies for the `Staff` entity to separate distinct functional roles (**Coaches** vs. **Scouts**) while maintaining shared attributes.

---

## Application Architecture (Java Backend)

The data layer is supported by a backend application developed in **Java**.

- **Technology**: Java (high-level, object-oriented, class-based language)  
- **Database Driver**: JDBC (Java Database Connectivity) for communication with the MySQL server  
- **Functionality**:  
  - User authentication  
  - Execution of complex SQL queries corresponding to GM operations (e.g., executing a multi-step trade transaction)  
  - Management of business logic constraints  

---

## Technologies and Database Concepts

- **Database Management System**: MySQL  
- **Language**: Java  
- **Concepts**:  
  - Entity-Relationship (E-R) Modeling  
  - Generalization/Specialization  
  - Data Normalization  
  - Access Cost Estimation  
  - Transactional Integrity  

---

## Contatti
* Alessio Bifulco: `alessio.bifulco@studio.unibo.it`
