# Easy Enroll - Class Registration System

Url: https://3.135.246.118/login-page
Admin User

Username: test101
Password: test123

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

## Challenges we ran into

1. **Hosting on AWS:** Setting up and configuring the hosting environment on AWS presented some challenges, but it allowed us to ensure high availability.
2. **Optimizing the Algorithm:** Tuning the Gale-Shapley algorithm to handle edge cases efficiently was a complex task, but it now provides accurate and fast results.
3. **Integration with OpenAI:** Incorporating the OpenAI LLM API into the application was challenging, but it significantly improved the course relevance assessment.

## Accomplishments that we're proud of

- Successfully implementing the enhanced Gale-Shapley algorithm for matching students with courses.
- Integrating OpenAI's Language Model to provide students with insightful project relevancy scores.
- Hosting the application on AWS for stability and scalability.

## What we learned

Through the development of Easy Enroll, we learned how to:

- Implement complex matching algorithms to solve real-world problems.
- Integrate external APIs, such as the OpenAI LLM, to enhance the functionality of our application.
- Set up and manage a web application hosting environment on AWS.

## What's next for Easy Enroll

In the future, we plan to:

- Enhance the user experience by adding more features, such as real-time course availability updates.
- Improve the matching algorithm's performance by optimizing it further.
- Continue refining the project relevance assessment process with more data and user feedback.

Easy Enroll aims to make college course enrollment a more efficient and user-friendly experience for students while ensuring they get the courses that best fit their academic goals.
