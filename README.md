# Productivity Insight

**Android application that analyzes and keeps track of the user's and monthly productivity.**

**Note:** The app is not published on the Google Play Store yet, so this is not a complete version, there are 
still a few features to be added + a pretty, graphical user interface design. This is a **MVP**
(Minimum Viable Product).

The app helps the user figure out on which period of time he or she is most productive at.
Currently those periods of time include months(January, February, ...)
and week days(Monday, Tuesday, ...), but more specific periods will be added like parts of days
(Morning, Afternoon, Evening, ...), maybe even hours.

The app takes an absolutely minimal amount of user's time to use. The only "work" the user need 
to do is to click on a notification that pops up every day at 20:17 and choose one of the productivity 
scores to tell the app how productive his or her day was. This is necessary in order to keep the data updated 
and for the app to be of any use.

The app currently has 5 different scores for productivity:
* Very productive
* Pretty productive
* Average
* Not very productive
* Not at all productive

and all those have numerical values assigned to them, which the program uses to calculate 
productivity for all periods of time.

The app's backend ([Productivity-Insight-Backend](https://github.com/delicmakaveli/Productivity-Insight-Backend)) built with Python and Django is a separate project, also on my GitHub, 
click [here](https://github.com/delicmakaveli/Productivity-Insight-Backend#productivity-insight-backend) to read more about it.

## Prerequisites

The app's minimal API is 15 (Ice Cream Sandwich), so it will work on anything above that.

## Installing

The app is not published on the Google Play Store yet. Will add installation and usage instruction when the app is published.

## Built With

* [Java 1.7](http://docs.oracle.com/javase/7/docs/api/) - Language of the project
* [Volley](https://github.com/google/volley) - HTTP Library that communicates with [the backend](https://github.com/delicmakaveli/Productivity-Insight-Backend)

## Author

* **Stefan Delic** _Creation and Initial Work_- [delicmakaveli](https://github.com/delicmakaveli)

See also the list of [contributors](https://github.com/delicmakaveli/Productivity-Insight/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/delicmakaveli/Productivity-Insight/blob/master/LICENCE.md) file for details

## Acknowledgments

* Hat tip to anyone who's code was used
