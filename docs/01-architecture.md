## Architecture

### Backend

The backend is a vanilla Clojure application running on the JVM.

The backend's main purpose is to scrape data off Facebook, specifically the business hours for a given cafeteria. The backend exposes an endpoint, which fetches the given Facebook page and parses the desired data fields, and returns back a JSON that can be easily consumed by the frontend.

In order to deal with Facebook's scrape-prevention mechanisms, a third-party data-scraping service [Scraper API](https://www.scraperapi.com/) is used. This service automatically proxies the requests and bypasses any CATPCHAs.

The backend uses a time-to-live cache of 30 seconds. However, there are still performance and scalability issues with this approach, which I've outlined in [#3](https://github.com/jukkhop/kahvilat/issues/3).

Following libraries have been used:

- [http-kit](https://www.http-kit.org/) as a HTTP server
- [compojure](https://github.com/weavejester/compojure) for routing
- [core.cache](https://github.com/clojure/core.cache) for caching
- [clj-http](https://github.com/dakrone/clj-http) as a HTTP client
- [hickory](https://github.com/davidsantiago/hickory) for HTML parsing
- [environ](https://github.com/weavejester/environ) for managing environment variables

### Frontend

The frontend is a ClojureScript application built on top of [Reagent](https://github.com/reagent-project/reagent).

The frontend's main purpose is to take a predefined list of cafeterias, use the backend to fetch their current status and business hours, and update that information on the page.

After the initial render, the application continues to refresh the list every 60 seconds. This is done because the scraped data is simply rendered as-is and not parsed in any meaningful way, meaning the information becomes outdated very quickly.

The fetches are done via [cljs-http](https://github.com/r0man/cljs-http) and are carried out asynchronously with the facilities provided by [core.async](https://github.com/clojure/core.async).

### Issues

The current architecture has issues regarding latency and scalability. I've explained these in more detail in [#3](https://github.com/jukkhop/kahvilat/issues/3).
