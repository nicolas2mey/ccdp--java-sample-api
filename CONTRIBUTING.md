_NB: Here is a sample of a minimalist contributing guide_

# Contributing.

When contributing to this repository, please first discuss the change you wish to make with team members (<mail_team>@adeo.com). 

## Code formatting and quality.

This project contains 4 files to ensure code quality:

1. `ruleset.xml` custom PMD rules file, compatible with IDEA PMD Plugin.
2. `checkstyle.xml` custom Checkstyle file, compatible with IDEA Checkstyle plugin.
3. `formatter.xml` compatible with IDEA JAVA code formatter
   (`Settings > Editor > Code Style > Java`, import this file as new Scheme).
4. `.editorconfig` standard editor configuration file.
5. You can additionally use SonarLint plugin to perform some local analysis.

## Pull Request Process.

1. Update the README.md with details of changes to the interface, this includes new environment 
   variables, exposed ports, useful file locations and container parameters.
2. You may merge the Pull Request in once you have the sign-off of **at least one other developers**, or if you 
   do not have permission to do that, you may request the second reviewer to merge it for you.
   
## Git Flow

1. Possible prefix are `feat/` for new developments and `fix/` for new hotfix.
2. Do not finish feature (merge to develop) if untested.

### New development.

A new development will start on a new branch derived from `develop`:

```bash
git checkout -b feat/my_new_feature
```

Once your developments are finished, you can create a P.R on github, to merge it on `develop`.

### Hot Fix.

A new hot fix will start on a new branch derived from `master`:

```bash
git checkout -b fix/my_new_hotfix
```

Once your developments are finished, you can create a P.R on github, to merge it on `master`.


## Development Rules.

### Dependency Injection.

- Use **constructor injection** for the sake of the team and tests.
- **@Autowired** is prohibited except in pure integration tests.

### Tests.

- Junit 5 as Test Framework in completion of AssertJ.
- Use **new** operator in Unit Tests and not DI.
- Please follow existing tests' pattern to create new ones.

### Utility classes.

- Use *Utils or *Helper naming convention.

## Code Quality.

Projet is registered into ADEO Sonar product: https://sonar.factory.adeo.cloud/dashboard?id=ccdp--java-sample-api. 