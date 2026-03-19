# Recitation 7 - Horse Race

## Learning Outcomes

By the end of this activity, a student should be able to:

1. Implement a simple Java class from requirements
2. Use classes to accomplish more complex tasks
3. Use if-else statements to make decisions

---

## Introduction

In this recitation you will implement two classes for a horse racing simulation: `Horse` and `HorseRace`. Both classes have already been stubbed out for you — your job is to implement each method according to the instructions below. It is recommended that you implement and test `Horse` first, then move on to `HorseRace`.

---

## Background: The Amusement Park Horse Race Game

The horse race game is a beloved classic found on carnival midways, at state fairs, and in amusement parks across the country. The most common version is the **Roll-a-Ball Horse Race**. Players roll balls down an alley toward a set of numbered holes at the opposite end. Each hole is colored differently and moves the player's horse forward at a different speed — landing in the highest-value hole sends the horse galloping, while lower-value holes result in a trot or a walk. A race track display is mounted above the lanes, and the first horse to reach the finish line is the winner.

The Roll-a-Ball Derby's origins can be traced back to mid-20th century fairgrounds, where players rolled balls up inclined tracks and into scoring holes. Initially built around the excitement of horse racing with themes inspired by the Kentucky Derby, the concept soon evolved into variations such as Donkey Derby, Camel Derby, and Reindeer Races. By the 1960s and 70s, these games had become fixtures of seaside arcades and travelling funfairs, drawing crowds with their flashing lights, moving race boards, and lively competitive atmosphere.

A second popular version is the **Water Gun Horse Race**, where players beat their opponents by shooting water at a target to advance their horse. Players aim their water gun at alternating targets, and propel their moving game piece toward the finish line.

In both versions the core idea is the same: each round, a player's action determines how far their horse advances, and the first to cross the finish line wins. Our simulation captures this spirit — instead of rolling a ball or shooting water, each horse advances by a random dice roll each round, and the first to reach position 20 wins the race.

<div style="text-align:center">
<img src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%2Fid%2FOIP.AICB1-rUlv7lugWXbBzcUAHaFj%3Fpid%3DApi&f=1&ipt=906cc7754d855f891b0f8de80b7387b480358f7956896c5c5c35d1f11df26b70&ipo=images"> <br/>
<em>Figur 1: A classic Roll-a-Ball Horse Race game — a staple of carnival midways.</em>
</div>


## The `Horse` Class

### Fields

The following fields have already been declared for you:

- `String name` — the horse's name
- `int position` — the horse's current position on the track (starts at 0)
- `int wins` — number of races won
- `int losses` — number of races lost
- `int draws` — number of races drawn
- `Random random` — used to generate random rolls
- `static final int FINISH_LINE = 20` — the position a horse must reach to finish

### Methods Implemented

#### 1. Constructor — `Horse(String name)`

Initializes all fields. The horse's name should be set to the given parameter. Position, wins, losses, and draws should all start at 0. Create a new `Random` object and assign it to the `random` field.


#### 2. Getters

Implement the following getter methods, each returning the corresponding field:

- `public String getName()` — returns `name`
- `public int getPosition()` — returns `position`
- `public int getWins()` — returns `wins`
- `public int getLosses()` — returns `losses`
- `public int getDraws()` — returns `draws`


### Methods to Implement


#### 1. `public int advance()`

This method simulates the horse moving forward one step in the race.

- Generate a random integer between 1 and 6 (inclusive) using `random.nextInt(6) + 1`
- Add that value to `position`
- Return the value rolled


#### 2. `public boolean hasFinished()`

Return `true` if the horse's current position is greater than or equal to `FINISH_LINE`, and `false` otherwise.


#### 3. `public void resetPosition()`

Set `position` back to 0. This is called at the start of each new race. It should **not** affect any statistics.


#### 4. `public void clearStats()`

Set `wins`, `losses`, and `draws` all back to 0. This is called when starting a completely new game.


#### 5. `public void recordWin()`

Increment `wins` by 1.


#### 6. `public void recordLoss()`

Increment `losses` by 1.


#### 7. `public void recordDraw()`

Increment `draws` by 1.



## The `HorseRace` Class

### Fields

The following fields have already been declared for you:

- `Horse horse1` — the first horse in the race
- `Horse horse2` — the second horse in the race


### Methods Implemented

#### 1. Constructor — `HorseRace(Horse horse1, Horse horse2)`

Assign the two given `Horse` parameters to their corresponding fields.

#### 2. Getters

Implement the following getter methods, each returning the corresponding field:

- `public Horse getHorse1()` — returns `horse1`
- `public Horse getHorse2()` — returns `horse2`



### Methods to Implement

#### 1. `public Horse race()`

This is the main method that runs the race from start to finish. Implement it in the following order:

1. **Reset both horses** by calling `resetPosition()` on each so they start at position 0.
2. **Run the race loop** — use a `while` loop that continues as long as neither horse has finished. Inside the loop:
   - Call `advance()` on each horse and save the roll value returned.
   - Print each horse's name, the roll, and their new position (see the example output below for the expected format).
3. **Determine the outcome** using if-else once the loop ends:
   - If **both** horses have finished, call `recordDraw()` on each and print `"Result: Draw!"`. Return `null`.
   - If only **horse1** has finished, call `recordWin()` on horse1 and `recordLoss()` on horse2. Print the winner's name and return `horse1`.
   - Otherwise, call `recordWin()` on horse2 and `recordLoss()` on horse1. Print the winner's name and return `horse2`.


## Example Interaction

Once fully implemented, a race between two horses should behave like this:

```
--- Race Start! Finish line at position 20 ---

Round 1: Thunderbolt advances 4 -> position: 4
         Lightning advances 2 -> position: 2

Round 2: Thunderbolt advances 5 -> position: 9
         Lightning advances 6 -> position: 8

Round 3: Thunderbolt advances 3 -> position: 12
         Lightning advances 5 -> position: 13

Round 4: Thunderbolt advances 6 -> position: 18
         Lightning advances 4 -> position: 17

Round 5: Thunderbolt advances 5 -> position: 23 
         Lightning advances 6 -> position: 23

Result: Draw!

Thunderbolt stats -> Wins: 0 | Losses: 0 | Draws: 1
Lightning stats   -> Wins: 0 | Losses: 0 | Draws: 1
```

<div style="background-color:pink;padding:3px; border-radius:3px;">
<strong>NOTE:</strong> The name of the horses above are examples. Your program should output names based on the names provided for each horse!
</div>


## Submitting Your Work

Submit your work on CodeGrade as you did with all previous recitations. You will be provided with more details once the recitation session is completed.
