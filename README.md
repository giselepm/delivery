# Allpago Package Delivery Challenge

## BUILDING AND COMPILING PROJECT

This solution uses [Gradle](https://gradle.org/) as build mechanism. It's shipped with an embedded Gradle installation, 
hence, to run its tests, simply open a command shell and issue the line below at the root directory: 
  
    ./gradlew clean test

Analogously, to build a package, use: 

    ./gradlew jar


## MY APPROACH TO THIS CHALLENGE


My approach to this challenge was to solve the routing problem first as it is a well known problem (Dijkstra). Then I 
continued solving the pricing problem and in the end, after having most of the code running and validated for small 
scenarios, I've created the acceptance tests consuming the csv files. And during all the process, I've refactored every 
time I felt something could be clearer or simpler.

I've tried to keep the code as simpler as possible using small classes, significant classes, method and attribute names 
and methods with only one responsibility.  

Also I've decided to use groovy because I've been coding mainly on it for the last 2 years and hence I fill more 
productive with it. Besides, using gradle you don't need anything but Java installed to run it.

## REFERENCES

As Dijkstra is a well known algorithm and there are a lot of solutions in the internet, instead of starting from 
scratch, I've used the code in this[page](http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html)as my 
starting point and adapted it for the delivery problem.

## ASSUMPTIONS

* It's not necessary to provide a way for the user to interact with it via a command-line interface of any kind of user 
interface. The challenge is about implementing methods to solve the problem and test cases to run the csv scenarios.
* Only .csv files in the root csvs folder will be executed. No other file extension in this folder or any file in 
subfolders will be executed.
* If a negative number is sent in the package's measures, its absolute value will be considered.
* Same names with different letter cases are considered equals, i.e. Me, ME and me are considered the same person.

## DESIGN

* **BestRouteCalculator**class is responsible for generating all the best routes from a defined origin and for 
retrieving the hard to a specific Person.
* **ShippingCalculator**class is responsible for calculating the shipping cost of a package based in a provided hard 
and the package's measures.
* **Graph**class keeps the all people and routes information.
* **Person**class keeps a person info, in the case, just its name.
* **Route**class keeps the the source and destination of a route plus its hard to transport the package
* **Package**class has all info about a package, including its weight, height, width and length.

## THANK YOU

Regardless of succeeding or failing this recruiting process, I just wanted to thank you for the opportunity. I had a lot 
of fun working on this challenge. I hope I succeed, though :)


## Problem Description
-----------
Suppose you have a network of friends and want to ship a package to any one of 
your friends at the optimal (lowest) cost. As common in life, some of your friends 
live closer to you than others. Instead of representing your friends in terms of a distance 
of how far they live from you (or their friends), we will represent them in terms of 
abstract units called `HARD` which define the difficulty of shipping a package. For example, 
if one of your friends lives on the Moon and another across the street from you, it is 
magnitude harder to ship a package to the Moon than across the street. So a relationship 
between you and your friend on the Moon may be defined as `1000000 HARD`, while the 
relationship between you and your friend across the street as `5 HARD`. Each relationship 
is uni-directional, that is, the difficulty for shipping a package from you to your 
friend on the moon may not be the same as the difficulty of shipping a package from your 
friend on the moon to you. As an example, let's use [escape velocity](https://en.wikipedia.org/wiki/Escape_velocity). 
Since Earth's escape velocity is higher than that of the Moon, it is harder to shoot 
a package from Earth to space than it is from the Moon to space. As such, the relationship 
between your friend on the Moon and You could be smaller, eg: `900000 HARD`. Also, remember 
that in your network, your friends may have other friends so an optimal shipping cost 
may not always be direct shipping from You to your friend, but perhaps through a number 
of your friends. 

To each of your friends you can ship a package of certain weight and dimensions at a 
fixed cost. The formula for computing the shipment is the same for all of your 
friends in the network and is as follows:
```
{shipping cost}(EUR) = sqrt(sum(HARD)) * {normalized weight}(kg)
```
The cost is represented to the nearest two decimals (eg: 24,33 EUR). A normalized package 
weight is the greater value of an actual weight or a volumetric weight.

A `volumetric weight` (sometimes called dimentional weight) is a formula often applied by 
carriers to take into account volume as a function of weight. A light package that 
takes up a lot of space is just as difficult to ship as a small package that weighs a lot. 
The volumetric formula is defined as:
```
{Width x Length x Height}(cm) / 5000 = {Volumetric Weight}(kg) rounded up to the nearest 0,5kg
```
A practical example of this is [DHL](http://wap.dhl.com/serv/volweight.html). For example, 
a package of `width=26cm, length=10cm and height=11cm` that weighs `400 grams` would 
have a `normalized weight = 1kg` because volumetric weight defined as `2860/5000 = 0,572kg` 
rounded up to the `nearest 0,5kg` is `1kg`.

Suppose you have five friends in your network and this is how hard it is to ship a package 
to them:
```
You => Stefan:  100 HARD 
You => Amir:   1042 HARD 
You => Martin:  595 HARD 
You => Adam:     10 HARD 
You => Philipp: 128 HARD
```
You can assume `HARD` units are positive `integers`. 

You also know how hard it is for some of your friends to ship a package between themselves, but 
since not all of your friends may be friends with each other you wouldn't know all shipping 
combinations. For example, you only know that:
```
Stefan => Amir: 850 HARD 
Stefan => Adam:  85 HARD 
Adam => Philipp:  7 HARD 
Adam => Martin: 400 HARD 
Diana => Amir:   57 HARD 
Diana => Martin:  3 HARD
```
_(notice that Diana is not your friend)_

Taking into account all of this information you know that you could ship a `1kg` (normalized weight) 
package to Philipp directly at a cost of `11,31 EUR` or you could ship through Adam for a 
total cost of `4,12 EUR`. Shipping through Adam is the optimal cost.

### TASK

Given a package, a network of friends with the shipping `HARD` units, and a friend to which 
you want to ship a package, write a Java program which always finds the lowest shipping cost 
possible from you to that friend.

Your program should have a comprehensive documentation so that it's obvious and intuitive for 
Allpago to run it. Provide unit tests. At minimum, you must provide a unit test capable of consuming a 
`CSV` file with definition of a your friend network and expected results. The format of a file is:
```
SOURCE,TARGET:HARD,TARGET:HARD ... 
@,TARGET,PACKAGE,COST
```
Both lines can appear multiple times. `SOURCE` is You or a friend for whom other friends and 
their respective hardness relationship is defined. You are always represented by the string 
`ME`. Your friends are always represented by first name. Friends with same first name are appended 
a differentiator such as a sequence (Jessica1, Jessica2, Jessica3, etc). For example, you could 
define your friend network in a `CSV` like this:
```
ME,Lisa:33,Peter:123,John:55 
Lisa,John:3 
Diana,Peter:11
```
Given example above, you have three friends: Lisa, Peter and John. You can ship to Lisa @ `33 HARD`, 
to Peter @ `123 HARD` and to John @ `55 HARD`. In addition, Lisa shipment to John is `3 HARD` 
and Diana can ship to Peter @ `11 HARD`. You cannot ship to Diana.

If the line begins with the `@` symbol, then it is an assertion line which means you are given 
a scenario which your test must pass. Each scenario will always be the correct shipping cost from You 
to someone in your network given package information. For example:
```
@,Lisa,10x9x5x1200,6.89 
@,Diana,6x10x8x1233,~
```
The first assertion states that a shipping cost for a `1.2kg (width=10cm, length=9cm, height=5cm)` 
package to Lisa should be `6.89 EUR`. The second assertion states that it's impossible to ship a 
package to Diana so the cost is a positive infinity.

You are not allowed to modify provided CSV files in any way.

### DELIVERABLE

Provide complete source code with all relevant documentation. Your program should pass the tests 
criteria given to you in `CSV`. Furthermore, Allpago will test your program with several additional 
inputs that you are not given. Ideally, we would like to have a unit test which reads some 
directory (eg: `/tmp/allpago`) and validates your program against any number of `CSV` files inside 
that directory. In few sentences describe your design; in particular, tell us about your core 
algorithm. You are free to use any library that may help your task. You can even re-use Java 
code from the Internet if you find it useful, as long as you cite the source and explain why 
you used it.
