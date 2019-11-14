
## Infrastructure

![alt text](https://github.com/jukkhop/kahvilat/blob/master/docs/resources/architecture-old.png 'Architecture')


Both the backend and the frontend have fully automated CI/CD pipelines. The pipelines are triggered by any new commits to the `master` branch.

### Backend

The backend is Dockerized and deployed to AWS ECS using CircleCI.

The pipeline works roughly as follows:

- Run tests (currently there are none, see [#2](https://github.com/jukkhop/kahvilat/issues/2))
- Compile the code into a jar file
- Build the Docker image and push it to AWS ECR
- Update the ECS service with the latest image and start a new task

There are currently no Terraform templates or such (see [#5](https://github.com/jukkhop/kahvilat/issues/5)).

### Frontend

The frontend is separately built and hosted by Netlify.

Netlify is currently used because it's ridiculously easy to use for hosting static websites. However, I am looking into moving the frontend to AWS S3, just to get everything built and deployed under a single service (CircleCI).

The frontend is currently also missing automated tests (see [#2](https://github.com/jukkhop/kahvilat/issues/2)).
