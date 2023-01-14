# Tending-Repos

[![platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)

## Preview

### Light Mode

<img src="https://github.com/MohNage7/Tending-Repos/blob/develop/screenshots/Screenshot_20221201_184258.png"  width="197" height="414" /> <img src="https://github.com/MohNage7/Tending-Repos/blob/develop/screenshots/Screenshot_20221201_183817.png"   width="197" height="414"/><img src="https://github.com/MohNage7/Tending-Repos/blob/develop/screenshots/Screenshot_20221201_184237.png"  width="197" height="414" />

### Dark Mode

<img src="https://github.com/MohNage7/Tending-Repos/blob/develop/screenshots/Screenshot_20221201_184044.png"   width="197" height="414" /><img src="https://github.com/MohNage7/Tending-Repos/blob/develop/screenshots/Screenshot_20221201_184052.png"  width="197" height="414" /><img src="https://github.com/MohNage7/Tending-Repos/blob/develop/screenshots/Screenshot_20221201_184201.png"  width="197" height="414" />

## Project Overview

- Project is using Kotlin as the development language and supporting min API level 24 (Nougat)
- The app should define and run 3 requests SIMULTANEOUSLY, each request is defined below:
    - Truecaller10thCharacterRequest:
        - I. Grab https://www.truecaller.com/blog/life-at-truecaller/life-as-an-android-engineer
        - II. Find the 10th character and display it on the screen
    - TruecallerEvery10thCharacterRequest:
        - I. Grab https://www.truecaller.com/blog/life-at-truecaller/life-as-an-android-engineer
        - II. Find every 10th character (i.e. 10th, 20th, 30th, etc.) and display the array on the
          screen
    - TruecallerWordCounterRequest:
        - I. Grab https://www.truecaller.com/blog/life-at-truecaller/life-as-an-android-engineer
        - II. Split the text into words using whitespace characters (i.e. space, tab, line break,
          etc.), count the occurrence of every unique word (case insensitive) and display the count
          for each word on the screen

## Project Architecture

The project is
following [clean architucte](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
, It’s a group of practices and decisions that makes the code testable with independent components.

The following diagram shows how all the modules will interact with one another.

<img src="https://github.com/MohNage7/Tending-Repos/blob/develop/screenshots/clean_architecture_mvvm.png"  width=772 height=772  />

### Dependency Flow

As illustrated in the image above each component depends only on the component below it. the higher
layers will request the needed data from the layers below it and the data is being provided by the
lower layers by a reactive paradigm.

#### Dependency Injection

Allows classes to define their dependencies without constructing them. At runtime, another class is
responsible for providing these dependencies

* For DI we are using Hilt

### Presentation layer (app module)

The layer that interacts with the UI. for this layer we are applying MVVM architecture pattern

### Domain Layer

Contains business logic (usecases/repository interfaces) and entities.

### Data Layer

Abstract definition of all the data sources. (Network / Local )

### Network layer

Contain abstract and concrete implementation for any logic that is related to network calls.
