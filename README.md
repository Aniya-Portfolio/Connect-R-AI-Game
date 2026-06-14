# Connect-R AI Game

An intelligent implementation of the classic Connect-R board game featuring AI opponents, game state evaluation, and strategic decision-making algorithms.

## Overview

Connect-R is a generalized version of Connect Four where players attempt to connect **R consecutive pieces** horizontally, vertically, or diagonally before their opponent. This project implements both human and AI players, allowing users to compete against an intelligent computer opponent.

The AI uses search algorithms and heuristic evaluation functions to analyze board positions and select optimal moves.

## Features

- Human vs Human gameplay
- Human vs AI gameplay
- Configurable board dimensions
- Configurable connect length (R)
- Intelligent AI opponent
- Win detection system
- Board evaluation heuristics
- Turn-based gameplay
- Console-based user interface

## Technologies Used

- Java
- Object-Oriented Programming
- Minimax Algorithm
- Alpha-Beta Pruning
- Data Structures
- Game AI Concepts

## Game Rules

1. Players take turns dropping pieces into columns.
2. Pieces fall to the lowest available row.
3. The objective is to connect **R** pieces in a row:
   - Horizontally
   - Vertically
   - Diagonally
4. The first player to connect R pieces wins.
5. If the board fills without a winner, the game ends in a draw.

## AI Strategy

The AI utilizes:

### Minimax Search
The AI explores possible future game states to determine the best move.

### Alpha-Beta Pruning
Optimization technique that reduces the number of nodes evaluated during search.

### Heuristic Evaluation
Board positions are scored based on:
- Potential winning lines
- Blocking opponent threats
- Creating multiple winning opportunities
- Center column control



## How to Run

### Prerequisites

- Java JDK 8 or later
- Any Java IDE (IntelliJ, Eclipse, VS Code)

### Compile

```bash
javac *.java
java Main

Example Gameplay
| | | | | | | |
| | | | | | | |
| | | | | | | |
| | |O|X| | | |
| |O|X|X| | | |
|O|X|O|X| | | |
---------------
 1 2 3 4 5 6 7
Player X's Turn

Learning Objectives

This project demonstrates:

Artificial Intelligence fundamentals
Search algorithms
Adversarial game playing
Object-Oriented Design
Algorithm optimization
Problem-solving techniques
Future Improvements
Graphical User Interface (GUI)
Multiple AI difficulty levels
Online multiplayer support
Monte Carlo Tree Search (MCTS)
Save/Load game functionality
Game statistics tracking
Author

Aniya Hicks

Bachelor's Student in Game Design & Development

License

This project is for educational purposes and coursework demonstrations.
