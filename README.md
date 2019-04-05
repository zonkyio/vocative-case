# <img src="zonky.jpg" height="100"> Vocative case

## Purpose

A utility for generating vocative cases out of nominative ones.
It's useful when you want to greet someone in an email, sms, or any notification in general.

## Usage

### Maven dependency

```xml
<dependency>
    <groupId>io.zonky.util</groupId>
    <artifactId>vocative-case</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

### Java code

```java
    CzechVocativeCase cvc = CzechVocativeCase.getInstance();
    assertEquals("Pavle", cvc.getVocativeCase("pavel"));
```

## Linguistic details

Vocative cases are used in Slavic languages for example, such as Czech. 
For more information, see https://en.wikipedia.org/wiki/Vocative_case.

### Examples

#### A single given name

For the Czech name `Pavel`, an English greeting might look like `Hello Pavel, welcome onboard!`

The same greeting in Czech, however, would be `Ahoj Pavle, vítej na palubě!`

E.g. the nominative case `Pavel` has changed to `Pavle` vocative case.

#### A double given name

If a person was given a double name, for example `Pavel Petr`, the Czech vocative case will be `Pavle Petře`.

#### A family name

Family names are also supported. For the nominative double given name and a family name `Pavel Petr Kačmář`,
the Czech vocative case will be `Pavle Petře Kačmáři`.

#### General

The names are also trimmed and normalised to a camel case. Suppose you provide a `pAvEL PEtr  ` name, the returned result will be `Pavle Petře`.

## Known limitations

- Only Czech names are supported at the moment
- Mixing male and female given/family names will produce mixed results.
    I.e. for the `Pavla Kovář` nominative, the algorithm will return `Pavlo Kováři` vocative.

## Building from sources

`./gradlew clean build`
