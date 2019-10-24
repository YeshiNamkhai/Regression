# Regression

This is an implementation of *Simple Linear Regression* reading an input file in CSV format such as
<small>
| Age | Height |
| --- | --- |
| 4 | 100.1 |
| 5 | 107.2 |
| 6 | 114.1 |
| 7 | 121.7 |
| 8 | 126.8 |
| 9 | 130.9 |
| 10 | 137.5 |
| 11 | 143.2 |
| 12 | 149.4 |
| 13 | 151.6 |
| 14 | 154.0 |
| 15 | 154.6 |
| 16 | 155.0 |
| 17 | 155.1 |
| 18 | 155.3 |
| 19 | 155.7 |
| Graph it | ![chart](img/dispersion.png) |
</small>
..and showing a dispersion plot to check whether is meaningful to look for an equation in the form **y=ax+b**. 

<small>Where the variable **y** is indipendent and the variable  **x** is dipendent. In this equation the coefficent **a** is the regression and as usual gives the slope of the line.</small>

Then computing for each row [TSS](https://en.wikipedia.org/wiki/Total_sum_of_squares) and [RSS](https://en.wikipedia.org/wiki/Residual_sum_of_squares).

| | Age | <sup>1</sup>/<sub>age</sub> | Height |
| --- | --- | --- | --- |
| sum | 184.0 | 1.7144 | 2212.2 |
| avg | 11.5 | 0.1072 | 138.3 |
| tss | | 0.0489 | 5464.4575 |

| rss |
| --- |
| -15.9563| |

| | coefficent |
| --- | --- |
| **a** <small>&sum;<sub>xy</sub> &sum;<sub>xx</sub></small> | -326.6 |
| **b** <small><sub>avg</sub>Y-<sub>avg<sup>1</sup></sub>/<sub>age</sub>*a</small> |  173.3 |  

| equation |
| --- |
| **y** = -326.4**x** + 173.3 |

## Package
The code that performs simple linear regression is organized into a package containg three classes: Compute, DataCSV and Draw.

### Compute
- round()   **round with precision**
- Column calculations
    - sumList() column **sum**
    - avgList() column **average**
    - tssList()  **T**otal **S**um of **S**quares
    - rssList()  **R**esidual **S**um of **S**quares

### DataCSV
- getIndexValue()
- getIndexValueInverted()

### Draw
- scatterPlot()

## Credits
Inspiration for this Java coding challenge comes from [The Manga Guide to Regression Analysis](https://nostarch.com/regression) published in Italian by Le Scienze/La Repubblica (NO STARCH PRESS and Ohmsha Ltd.) in 2016.<small>
>Like a lot of people, Miu has had trouble learning regression analysis. But with new motivation—in the form of a handsome but shy customer—and the help of her brilliant café coworker Risa, she’s determined to master it.</small>

Charts built with [XChart](https://knowm.org/open-source/xchart/) a light-weight and convenient library for plotting data.<small> 
>Its focus is on simplicity and ease-of-use, requiring only two lines of code to save or display a basic default chart.
</small>
