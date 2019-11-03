![alt text](https://github.com/jukkhop/kahvilat/blob/master/screenshot.png 'Screenshot')

Quick way to see which specialty cafeterias in Helsinki are currently open. Frontend is written in ClojureScript and backend in Clojure. Frontend utilizes Reagent, which is a React-wrapper framework for ClojureScript.

#### https://kahvilat.caffeinerush.dev

#### [Read the blog post][post]

[post]: https://caffeinerush.dev/blog/some-dynamic-functional-code-with-your-coffee

<hr>

### Instructions for local development

#### Run backend

- Change directory `cd backend`
- Create a profiles file `cp profiles.clj.example profiles.clj` (edit as needed)
- Run the server `lein run`
- Test the server `curl http://localhost:8080/api/v1/` (you should get a JSON response)

#### Run frontend

- Change directory `cd frontend`
- Run the development server `lein figwheel`
- Browser should open at `http://localhost:3449`
