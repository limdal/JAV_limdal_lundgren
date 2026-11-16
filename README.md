# JAV_limdal_lundgren
Version: 2.0.1

Welcome to Farming Game, a text-mode game about farming written in Java!

Buy seeds, plant them in your fields, harvest them at the right moment and
sell your harvest at the market. Earn $300 before the season ends to win.

## Requirements

A Java Development Kit (JDK), version 8 or later. Check with:

```
javac -version
```

No other library is needed.

## Compiling and running

Run these commands from the project folder (the one containing `src`).
The compiled classes are written to a new `out` folder.

**Linux / macOS (bash or zsh)**

```
javac -d out $(find src -name "*.java")
java -cp out Main
```

**Windows (PowerShell)**

```
javac -d out (Get-ChildItem -Path src -Recurse -Filter *.java).FullName
java -cp out Main
```

**Windows (Command Prompt)**

```
dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
java -cp out Main
```

(The Command Prompt version does not work if the project path contains
spaces: use PowerShell in that case.)

To generate the HTML documentation in a `doc` folder:

```
javadoc -d doc -sourcepath src -subpackages crops:farm:game:market:player
```

## How to play

The game is played in turns. Each turn the farm is shown, then you choose
an action by typing its number and pressing Enter:

1. **Plant crop**: buy seeds and plant them in an empty field (1 to 9).
2. **Harvest crop**: take a ready crop out of a field and store it in your
   barn. A rotten crop is turned into compost, worth $2.
3. **Sell everything in the barn**: get the money for all stored items.
4. **Wait**: let your crops grow for one turn.
5. **Quit**: leave the game.

Every action that succeeds takes one turn, and crops grow by one turn
after each of them. Going back (0) or an action that fails (not enough
money, crop not ready...) does not use a turn.

| Crop       | Seeds | Ready after | Rots after | Selling price                         |
|------------|-------|-------------|------------|---------------------------------------|
| Carrot     | $5    | 2 turns     | 5 turns    | always $12                            |
| Potato     | $10   | 4 turns     | 7 turns    | $25, +$4 per turn left in the field   |
| Strawberry | $30   | 5 turns     | 7 turns    | $40, -$10 per turn left in the field, grows back after harvest |

Crops stored in the barn keep the price they had when harvested.

You start with $50.

- **You win** as soon as you have $300.
- **You lose** if the 30 turns are over, or if you cannot afford any seeds
  and have nothing left to harvest or sell.

## Project structure

```
src/
  Main.java                  entry point
  game/Game.java             menus, keyboard input, display, game rules
  player/Player.java         money, fields and barn of the farmer
  farm/Field.java            a plot of land holding one crop
  farm/Barn.java             stores harvested items until they are sold
  farm/Compost.java          what is left of a rotten crop
  crops/Crop.java            abstract base class of all crops
  crops/Carrot.java, Potato.java, Strawberry.java
  crops/CropState.java       GROWING, READY or ROTTEN
  market/Sellable.java       interface for everything that can be sold
  farm/FieldOccupiedException.java, farm/CannotHarvestException.java,
  player/NotEnoughMoneyException.java
```

## Object-oriented concepts used

| Concept | Where |
|---------|-------|
| Abstract class and abstract methods | `Crop`: each crop implements `getSellPrice()` and `getDescription()` |
| Inheritance, `super(...)`, `@Override` | `Carrot`, `Potato`, `Strawberry` extend `Crop`; `Strawberry` overrides `regrow()` |
| Interface implemented by unrelated classes | `Sellable`, implemented by `Crop` and `Compost`; `Barn` only knows the interface |
| Overriding `toString()` | `Crop`, `Barn` |
| Encapsulation (`private`, `protected`, getters) | all classes; `Crop.setAge()` is only for subclasses |
| `final` and `static final` constants | `Crop` fields, game settings in `Game`, `Player.FIELD_COUNT` |
| Enumeration | `CropState` |
| Own exception classes, `throws`, `try`/`catch` | `FieldOccupiedException`, `CannotHarvestException`, `NotEnoughMoneyException`; caught in `Game` |
| Containers | `Field[]` in `Player`, `ArrayList<Sellable>` in `Barn` |
| Packages | `crops`, `farm`, `game`, `market`, `player` |
| Separation of model and display (MVC idea) | only `Game` reads input and prints; the other classes hold the data and rules |
