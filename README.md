The implemented platform simulates actions that users can take on a movie viewing platform: ratings, movie viewing, searches, recommendations.

Modeled entities:
- Video
    Of two types: movies and shows. The difference between them is that the series has seasons.
    All videos share a title, release year, one or more genres (e.g. comedy, thriller)
    The seasons of a series associated a number, the duration of the whole season and a rating.
    The films have a duration and rating
- User
    Two categories: standard and premium
    He has favorite and viewed videos
    
A user's ability to perform direct actions is of 3 different types:
  - Favorites - Adds a video to the user's favorites video list if it has already been viewed by that user.
  - View - views a video by marking it as seen. If he has seen it before, the user increases the number of views of that video. When viewing a series, all seasons are viewed.
  - Rating - gives a rating of a video that is already seen (in series it applies to each season (as opposed to watching, where it is done throughout the series)).
The rating differs depending on the type - for series it is for each season. The rating can be given only once by a user. For series it can be only once a season.

A query contains the type of information you are looking for: actor / video / user, sort criteria, and various parameters :

For actors:
  - Average - the first N actors (N given in the query) sorted by the average ratings of the movies and series in which they played. The average is arithmetic and is calculated for all videos (with a total rating other than 0) in which he played.
  
  - Awards - all actors with the prizes mentioned in the query. Those actors must have received all the required awards, not just some of them. The sorting will be done according to the number of prizes of each actor, according to the order specified in the input.
  
  - Filter Description - all the actors whose description contains all the keywords (insensitive cases) mentioned in the query. Sorting will be done in the alphabetical order of the actors' names, in the order specified in the input.
  
For videos:
  - Rating - the first N videos sorted by rating. For series, the rating is calculated as the average of all seasons, provided that at least one season has a rating, those without a rating having 0. Videos that have not been rated will not be considered.
  
  - Favorites - the first N videos sorted by the number of appearances in users' favorite video lists
  
  - Longest - the first N videos sorted by length. For series, the sum of the lengths of the seasons is considered.
  
  - Most Viewed - the first N videos sorted by number of views. Each user also has a data structure that takes into account how many times they have seen a video. In the case of seasons, the whole series is taken as the number of views, not independently by seasons.
  
For users:
  - Number of ratings - the first N users sorted by the number of ratings they gave (practically the most active users)
  

Recommendations are user searches for videos. They are customized based on their profile and are based on 5 strategies:
For all users:
    - Standard - returns the first video unseen by the user in the database, with no second criterion.
    - Best unseen - returns the best video unseen by that user. All videos are sorted in descending order by rating and the first of them is chosen, the second criterion being the order of appearance in the database.
    
For premium users only:
    - Popular - returns the first unseen video of the most popular genre (videos are browsed according to the order in the database). Genre popularity is calculated from the total number of views of such videos. If all the videos of the most popular genre are viewed by the user, then it moves to the next most popular genre. The process resumes until the first video that was not viewed in the database is found.
    
    - Favorites - Returns the most common video in the favorites list (not seen by the recommended user) of all users, the second criterion being the order in which it appears in the database. The video must be in at least one list of users' favorite videos.
    
    - Search - all videos unseen by the user of a certain genre, given as an input filter. Sorted is in ascending order by rating, the second criterion being the name.
