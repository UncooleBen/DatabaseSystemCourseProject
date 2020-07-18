# Database System Final Project

<h5>This is a database system final project of East China Normal University. This project is on the basis of my another web app. If you want to, please refer to https://github.com/uncooleben/MeetHere</h5>

## Author

彭钧涛 (AKA. UncooleBen at github) 10164601140

## Instruction
HOW TO RUN THE WEBAPP?
-- TO RUN IN SAMPLE MODE:
0. Change the directory to the project root. Run `docker-compose down -v` command.
1. Run the `config.py` script with `python config.py -config -db sample`
2. Run `docker-compose up` command and wait for the services to start.
3. Use `http://localhost:12580/MeetHere` to visit the website
4. You may use username of `admin` and password of `admin` for administrative usage.
5. You may use username of `user` and password of `user` for normal usage.
6. Press Ctrl-C to shutdown the services.
7. Run `python config.py -clean` to clean the deployment files.
-- TO RUN IN PRODUCTION MODE:
0. Change the directory to the project root. Run `docker-compose down -v` command.
1. Run the `config.py` script with `python config.py -config -db production`
2. Run `docker-compose up` command and wait for the services to start (THIS MAY TAKE A LOOOOOOG TIME TO STARTUP DUE TO THE DATABASE SCALE).
3. Use `http://localhost:12580/MeetHere` to visit the website
4. You may use username of `admin` and password of `admin` for administrative usage.
5. You may use any integer between 3 and 100 as username and password for normal usage (e.g. username=5 and password=5).
6. Press Ctrl-C to shutdown the services.
7. Run `python config.py -clean` to clean the deployment files.

## Important file locations
1. SQL table declaration file is at `./sql/declaration.sql`.
2. Test example for task 5-1 is at `./sql/{testsample.sql, testsample.out}`.
3. Population script for production database is also in `./config.py`.
4. Test example for task 5-2 is at `./sql/{testproduction.sql, testproduction.out}`.
5. All the source files are at `./src`.

## Contact
pengjuntao2016@foxmail.com
