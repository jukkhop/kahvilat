
## Instructions for local development

### Prerequisites

Make sure you have [leiningen](https://leiningen.org/) installed and ready to go. It's a build tool for Clojure used in this project.

### Run backend

- Change directory `cd backend`
- Create a profiles file `cp profiles.clj.example profiles.clj` (edit as needed)
- Run the server `lein run`
- Test the server `curl http://localhost:8080/api/v1/` (you should get a JSON response)

### Run frontend

- Change directory `cd frontend`
- Edit `project.clj` as needed
- Run the development server `lein figwheel`
- Browser should open at `http://localhost:3449`
