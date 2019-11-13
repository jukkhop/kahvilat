FROM clojure:lein

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app

COPY project.clj /usr/src/app/

RUN lein deps

COPY . /usr/src/app

ENV CACHE_TTL 30000

ENV ORIGIN_URL http://localhost:3449

ENV PORT 8080

ENV SCRAPER_API_KEY = my_scraper_api_key

RUN mv "$(lein uberjar | sed -n 's/^Created \(.*standalone\.jar\)/\1/p')" kahvilat-backend-standalone.jar

CMD ["java", "-jar", "kahvilat-backend-standalone.jar"]
