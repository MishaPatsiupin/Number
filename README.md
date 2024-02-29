## NUMBERSAPI
### Let your statistics tell tales and dates come to life
#### A web service for finding out the fact about numbers
### URL STRUCTURE
Just hit '''http://localhost:8080/info?number=NUMBER&type=TYPE''' to get a plain text response, where
'''TYPE''' is one of '''trivia''', '''math''', '''date''', or '''year'''. Defaults to '''trivia''' if omitted.
'''NUMBER'''  is
*an integer, or
*the keyword '''random''', for which we will try to return a random available fact, or
*a day of year in the form '''month/day''' (eg. '''2/29''', '''1/09''', '''04/1'''), if '''TYPE''' is '''date'''


