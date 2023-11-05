# Easy Enroll - Class Registration System

The web application can be accessed using the Url: https://3.135.246.118/login-page. 

The Admin User details are Admin User - Username: test101 Password: test123

## Inspiration

The inspiration for Easy Enroll comes from the need to simplify the process of enrolling in courses for college students. College course enrollment can often be a complex and time-consuming task, and we wanted to create a solution that streamlines this process and helps students get the courses they want.

## What it does

Easy Enroll is a web application that allows college students to set their course preferences. They can prioritize the courses they want to enroll in, taking into account prerequisites, professor recommendations, and even input from OpenAI's Language Model (LLM) to assess the relevance of a project to a particular course.

The system then uses an enhanced Gale-Shapley algorithm to match students with their preferred courses, taking into consideration various factors and resolving any ties between students. Once the matching is complete, the results are displayed to the students.

## How we built it

Easy Enroll is built using a combination of technologies:

- Frontend: React.js for the web interface, allowing students to input their course preferences.
- Backend: Spring Boot, a Java-based framework, hosts the matching algorithm API.
- Enhanced Gale-Shapley Algorithm: This algorithm is implemented to perform the course-student matching.
- OpenAI's Language Model: We use the OpenAI LLM API to assess project relevancy for students, adding an additional layer to course selection.
- Hosting: The application is hosted on AWS to ensure reliability and scalability.

## What's next for Easy Enroll

In the future, we plan to:

- Enhance the user experience by adding more features, such as real-time course availability updates.
- Improve the matching algorithm's performance by optimizing it further.
- Continue refining the project relevance assessment process with more data and user feedback.

Easy Enroll aims to make college course enrollment a more efficient and user-friendly experience for students while ensuring they get the courses that best fit their academic goals.
