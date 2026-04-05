# API Automation (POM + POJO)

Refactored TestNG + Rest Assured suite using a service-object (POM) layer and POJO models so new APIs can be added quickly.

## Layout
- `core/RequestSpecFactory` – shared spec with base URL, JSON content type, Allure + logging filters.
- `clients/` – API-facing clients (`AuthClient`, `UserClient`) encapsulate endpoints.
- `models/` – request/response POJOs; add new ones per endpoint.
- `data/UserDataFactory` – reusable, randomized payload builders.
- `tests/` – TestNG test classes consume clients, cover CRUD + search flows.
- `resources/config.properties` – base URL and auth credentials.

## Run Tests
```bash
mvn clean test
```

## Allure Report
```bash
# after mvn test
mvn allure:serve
# or
mvn allure:report && open target/site/allure-maven/index.html
```

## GitLab CI/CD
1. The pipeline config lives in `.gitlab-ci.yml` (already in this repo).
2. On every branch push or merge request, GitLab runs a `test` job in the `maven:3.9.6-eclipse-temurin-11` image.
3. Commands executed:  
   - `mvn -B -Dmaven.repo.local=.m2/repository clean test`  
   - `mvn -B -Dmaven.repo.local=.m2/repository allure:report`
4. The `.m2/repository` folder is cached per branch/MR to speed up dependency downloads.
5. Artifacts kept for 1 week: `target/surefire-reports` (JUnit XML shown in GitLab UI), `target/allure-results` (raw Allure data), and `target/site/allure-maven` (HTML report you can download from the job page).

## Adding A New API
1. Create a POJO request/response in `models/`.
2. Add a client method in the relevant `clients/*Client` or a new client class.
3. Build payloads in a factory (e.g., `data/`).
4. Write tests that call the client; assertions live in tests for clarity.
