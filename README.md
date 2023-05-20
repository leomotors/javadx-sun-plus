# JavaDX SUN PLUS!

Java<ruby>DX<rt>でらっくす</rt></ruby> SUN PLUS! is a rythm game _not inspired by_ <ruby>CHUNITHM<rt>チュウニズム</rt></ruby>

## Setup

- Copy `.classname.template` to `.classpath` and edit to your match JDK Config

## Chart Schema

- Each line contains hit note
- `TYPE TIME LANE_START LANE_END ARG0`
- Lane from 1 to N (number of lanes)
- `TAP` ARG0 = Is EX Note
- `HOLD` ARG0 = End Time
- `FLICK` ARG0 = EX Note and Direction (0 = left, 1 = right, 10 = EX & left, 11 = EX & right)
- Example: `TAP 69 1 5 0`, `HOLD 69 1 5 420`

## Chart Folder Structure

```
~/Library/Application Support/javadx # Prefix + /javadx
 |
 |- charts
    |- [chartId] # Folder Name = id
        |- metadata.txt
        |- cover.jpg
        |- music.mp3
        |- (basic.txt) # If exists
        |- (advanced.txt)
        |- (expert.txt)
        |- (master.txt)
        |- (ultima.txt)
```

### metadata.txt

```
title ぴえヨンブートダンス
author ぴえヨン（CV：村田太志）「【推しの子】」
expert 7
master 9
```
